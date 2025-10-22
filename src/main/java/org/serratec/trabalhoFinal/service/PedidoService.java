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

    public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }

    @Transactional
    public PedidoDTO criar(PedidoCriacaoDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId()).orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.CRIADO);

        for (ItemPedidoCriacaoDTO itemDTO : dto.getItens()) {
            Produto p = produtoRepo.findById(itemDTO.getProdutoId()).orElseThrow(() -> new NotFoundException("Produto não encontrado: " + itemDTO.getProdutoId()));
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
    public PedidoDTO alterarStatus(Long id, String statusStr) {
        Pedido p = pedidoRepo.findById(id).orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        try {
            p.setStatus(StatusPedido.valueOf(statusStr));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido");
        }
        return toDto(p);
    }
    
    public List<PedidoDTO> listarTodos() {
        return pedidoRepo.findAll()
            .stream()
            .map(pedido -> new PedidoDTO())
            .toList();
    }

    private PedidoDTO toDto(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());
        dto.setClienteId(p.getCliente().getId());
        dto.setStatus(p.getStatus());
        dto.setDataCriacao(p.getDataCriacao());
        dto.setItens(p.getItens().stream().map(item -> {
            ItemPedidoDTO idto = new ItemPedidoDTO();
            idto.setId(item.getId());
            idto.setProdutoId(item.getProduto().getId());
            idto.setProdutoNome(item.getProduto().getNome());
            idto.setQuantidade(item.getQuantidade());
            idto.setValorVenda(item.getValorVenda());
            idto.setDesconto(item.getDesconto());
            return idto;
        }).collect(Collectors.toList()));
        dto.setTotal(p.getTotal());
        return dto;
    }
}
