package org.serratec.trabalhoFinal.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.ItemPedido;
import org.serratec.trabalhoFinal.domain.Pedido;
import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.domain.StatusPedido;
import org.serratec.trabalhoFinal.dto.CarrinhoResponseDTO;
import org.serratec.trabalhoFinal.dto.ItemPedidoCriacaoDTO;
import org.serratec.trabalhoFinal.dto.ItemPedidoDTO;
import org.serratec.trabalhoFinal.dto.PedidoCriacaoDTO;
import org.serratec.trabalhoFinal.dto.PedidoDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.serratec.trabalhoFinal.repository.PedidoRepository;
import org.serratec.trabalhoFinal.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepo;
	private final ClienteRepository clienteRepo;
	private final ProdutoRepository produtoRepo;
	private final CashbackService cashbackService;
	private final EstoqueService estoqueService;
  private final EmailService emailService;

	public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo,
			CashbackService cashbackService, EmailService emailService, EstoqueService estoqueService) {

		this.pedidoRepo = pedidoRepo;
		this.clienteRepo = clienteRepo;
		this.produtoRepo = produtoRepo;
		this.cashbackService = cashbackService;
    this.emailService = emailService;
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
			item.setDesconto(BigDecimal.ZERO); // não existe desconto na DTO
			pedido.getItens().add(item);
		}

		Pedido saved = pedidoRepo.save(pedido);
		return toDto(saved);
	}

	public PedidoDTO buscarPorId(Long id) {
		Pedido p = pedidoRepo.findById(id)
				.orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
		return toDto(p);
	}

	public List<PedidoDTO> listarTodos() {
		return pedidoRepo.findAll().stream()
				.map(this::toDto)
				.collect(Collectors.toList());
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

	public void deletar(Long id) {
		if (!pedidoRepo.existsById(id)) {
			throw new NotFoundException("Pedido não encontrado");
		}
		pedidoRepo.deleteById(id);
	}
    
	public CarrinhoResponseDTO adicionarProduto(Long clienteId, @Valid ItemPedidoCriacaoDTO dto) {
		clienteRepo.findById(clienteId)
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado com o ID: " + clienteId));

		Pedido pedido;
		if (!pedidoRepo.existsByClienteIdAndStatus(clienteId, StatusPedido.PENDENTE)) {
			pedido = new Pedido();
			pedido.setCliente(clienteRepo.findById(clienteId).get());
		} else {
			pedido = pedidoRepo.findByClienteIdAndStatus(clienteId, StatusPedido.PENDENTE);
		}

		pedido.adicionarItem(
				new ItemPedido(produtoRepo.findById(dto.getProdutoId()).get(), pedido, dto.getQuantidade()));
		
		PedidoDTO pedidoDto = toDto(pedidoRepo.save(pedido));

		return new CarrinhoResponseDTO(pedidoDto.getItens(), pedido.getTotal()); //

	}

	public PedidoDTO concluirPedido(Long pedidoId, boolean usarCashback) {
		// info pedido
		Pedido pedido = pedidoRepo.findById(pedidoId).get();
		//valor do pedido
		BigDecimal totalDoPedido = pedido.getTotal();
		
		// aplicar cashback = calcular valor da compra
		if (usarCashback) {
			BigDecimal saldoCashback = pedido.getCliente().getCarteira();
			if (saldoCashback.compareTo(totalDoPedido) > 0) { // caso o valor da compra seja inferior ao valor contido
																// na carteira (cashback)
				saldoCashback = saldoCashback.subtract(totalDoPedido);
				totalDoPedido = totalDoPedido.subtract(totalDoPedido);
				pedido.getCliente().setCarteira(saldoCashback);
			} else {
				totalDoPedido = totalDoPedido.subtract(saldoCashback);
				saldoCashback = saldoCashback.subtract(saldoCashback); // se o pedido for maior qu o cashback eu subtraio tudo
				pedido.getCliente().setCarteira(saldoCashback);
			}
		}
		
		// receber cashback
		Cashback cashback = cashbackService.ganharCashback(pedido.getCliente(), totalDoPedido);
		pedido.getCliente().aumentarCarteira(cashback);    // recebe o pedido, cria o cashback, soma na carteira e salva o cb no banco
		// alterar status
		pedido.setStatus(StatusPedido.PAGO);
		
		
		// enviar email
		emailService.enviarNotificacaoCashback(pedido, cashback.getSaldo(), pedido.getCliente().getCarteira(), totalDoPedido, pedido.getTotal());
		
		PedidoDTO dto = toDto(pedidoRepo.save(pedido));
		dto.setTotal(totalDoPedido);
		cashbackService.desativarCashbackUsado(pedido.getCliente());
		
		return dto;  // salva o pedido

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
