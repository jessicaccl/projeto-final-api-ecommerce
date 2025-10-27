package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.*;
import org.serratec.trabalhoFinal.dto.*;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.serratec.trabalhoFinal.repository.PedidoRepository;
import org.serratec.trabalhoFinal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepo;
	private final ClienteRepository clienteRepo;
	private final ProdutoRepository produtoRepo;
	private final CashbackService cashbackService;
	private final EstoqueService estoqueService;

	public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo,
			CashbackService cashbackService, EstoqueService estoqueService) {
		this.pedidoRepo = pedidoRepo;
		this.clienteRepo = clienteRepo;
		this.produtoRepo = produtoRepo;
		this.cashbackService = cashbackService;
		this.estoqueService = estoqueService;
	}

	@Transactional
	public PedidoDTO criar(PedidoCriacaoDTO dto) {
		Cliente cliente = clienteRepo.findById(dto.getClienteId())
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

		Pedido pedido = new Pedido();
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.CRIADO);

		for (ItemPedidoCriacaoDTO itemDTO : dto.getItens()) {
			Produto p = produtoRepo.findById(itemDTO.getProdutoId())
					.orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDTO.getProdutoId()));

			if(!estoqueService.verificarEstoque(p.getId(), itemDTO.getQuantidade())) {
				throw new RuntimeException("Estoque insuficiente para o produto: " + p.getNome());
			}
			
			estoqueService.darBaixaEstoque(p.getId(), itemDTO.getQuantidade());
			
			ItemPedido item = new ItemPedido();
			item.setPedido(pedido);
			item.setProduto(p);
			item.setQuantidade(itemDTO.getQuantidade());
			item.setValorVenda(p.getPreco());
			item.setDesconto(itemDTO.getDesconto() == null ? BigDecimal.ZERO : itemDTO.getDesconto());
			pedido.getItens().add(item);
		}

		Pedido saved = pedidoRepo.save(pedido);
		return toDto(saved);
	}

	public PedidoDTO buscarPorId(Long id) {
		Pedido p = pedidoRepo.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
		return toDto(p);
	}

	@Transactional
	public PedidoDTO atualizarPedido(Long id, PedidoCriacaoDTO dto) {
		Pedido pedido = pedidoRepo.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

		Cliente cliente = clienteRepo.findById(dto.getClienteId())
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
		pedido.setCliente(cliente);

		pedido.getItens().clear();
		for (ItemPedidoCriacaoDTO itemDTO : dto.getItens()) {
			Produto p = produtoRepo.findById(itemDTO.getProdutoId())
					.orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDTO.getProdutoId()));

			ItemPedido item = new ItemPedido();
			item.setPedido(pedido);
			item.setProduto(p);
			item.setQuantidade(itemDTO.getQuantidade());
			item.setValorVenda(p.getPreco());
			item.setDesconto(itemDTO.getDesconto() == null ? BigDecimal.ZERO : itemDTO.getDesconto());
			pedido.getItens().add(item);
		}

		Pedido saved = pedidoRepo.save(pedido);
		return toDto(saved);
	}
	
	@Transactional 
    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        
           if (novoStatus == StatusPedido.PAGO && pedido.getStatus() != StatusPedido.PAGO) {
            BigDecimal valorTotalParaCashback = pedido.getTotal(); 
            cashbackService.creditar(pedido.getCliente().getId(), valorTotalParaCashback);
        } else if (novoStatus == StatusPedido.CANCELADO && pedido.getStatus() == StatusPedido.PAGO) {
       }
        
        pedido.setStatus(novoStatus);
        Pedido saved = pedidoRepo.save(pedido);
        return toDto(saved);
    }
	

	public List<PedidoDTO> listarTodos() {
		return pedidoRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
	}

	public void deletar(Long id) {
		if (!pedidoRepo.existsById(id)) {
			throw new NotFoundException("Pedido não encontrado");
		}
		pedidoRepo.deleteById(id);
	}

	private PedidoDTO toDto(Pedido p) {
		PedidoDTO dto = new PedidoDTO();
		dto.setId(p.getId());
		dto.setClienteNome(p.getCliente().getNome());
		dto.setStatus(p.getStatus().name());
		dto.setDataCriacao(p.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

		List<ItemPedidoDTO> itensDto = p.getItens().stream().map(item -> {
			ItemPedidoDTO idto = new ItemPedidoDTO();
			idto.setProdutoNome(item.getProduto().getNome());
			idto.setQuantidade(item.getQuantidade());
			idto.setValorUnitario(item.getValorVenda());
			idto.setDesconto(item.getDesconto() == null ? BigDecimal.ZERO : item.getDesconto());
			idto.setSubtotal(item.getValorVenda().multiply(new java.math.BigDecimal(item.getQuantidade()))
					.subtract(item.getDesconto() == null ? BigDecimal.ZERO : item.getDesconto()));
			return idto;
		}).collect(java.util.stream.Collectors.toList());

		dto.setItens(itensDto);
		dto.setTotal(p.getTotal());
		return dto;
	}
}