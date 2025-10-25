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
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final ProdutoRepository produtoRepo;
    private final CashbackService cashbackService;
    private final DescontoService descontoService;

    public PedidoService(PedidoRepository pedidoRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo,
                         CashbackService cashbackService, DescontoService descontoService) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
        this.cashbackService = cashbackService;
        this.descontoService = descontoService;
    }

    @Transactional
    public PedidoDTO criar(PedidoCriacaoDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.CRIADO);

        BigDecimal totalPedidoBruto = BigDecimal.ZERO;

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

            BigDecimal subtotalItem = item.getValorVenda()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()))
                    .subtract(item.getDesconto());
            totalPedidoBruto = totalPedidoBruto.add(subtotalItem);
        }

        BigDecimal totalComDesconto = descontoService.aplicarDesconto(totalPedidoBruto, cliente.getId());

        if (totalComDesconto.compareTo(totalPedidoBruto) < 0) {
            BigDecimal descontoTotal = totalPedidoBruto.subtract(totalComDesconto);

            for (ItemPedido item : pedido.getItens()) {
                BigDecimal subtotalItem = item.getValorVenda()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
                        .subtract(item.getDesconto());

                BigDecimal descontoProporcional = subtotalItem
                        .multiply(descontoTotal)
                        .divide(totalPedidoBruto, 4, RoundingMode.HALF_UP);

                item.setDesconto(item.getDesconto().add(descontoProporcional));
            }
            pedido.setValorTotal(totalComDesconto);
        } else {
            pedido.setValorTotal(totalPedidoBruto);
        }

        Pedido saved = pedidoRepo.save(pedido);
        return toDto(saved);
    }

    public PedidoDTO buscarPorId(Long id) {
        Pedido p = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));
        return toDto(p);
    }

    @Transactional
    public PedidoDTO atualizarPedido(Long id, PedidoCriacaoDTO dto) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        Cliente cliente = clienteRepo.findById(dto.getClienteId())
                .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        pedido.getItens().clear();
        BigDecimal totalPedidoBruto = BigDecimal.ZERO;

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

            BigDecimal subtotalItem = item.getValorVenda()
                    .multiply(BigDecimal.valueOf(item.getQuantidade()))
                    .subtract(item.getDesconto());
            totalPedidoBruto = totalPedidoBruto.add(subtotalItem);
        }

        BigDecimal totalComDesconto = descontoService.aplicarDesconto(totalPedidoBruto, cliente.getId());

        if (totalComDesconto.compareTo(totalPedidoBruto) < 0) {
            BigDecimal descontoTotal = totalPedidoBruto.subtract(totalComDesconto);
            for (ItemPedido item : pedido.getItens()) {
                BigDecimal subtotalItem = item.getValorVenda()
                        .multiply(BigDecimal.valueOf(item.getQuantidade()))
                        .subtract(item.getDesconto());

                BigDecimal descontoProporcional = subtotalItem
                        .multiply(descontoTotal)
                        .divide(totalPedidoBruto, 4, RoundingMode.HALF_UP);

                item.setDesconto(item.getDesconto().add(descontoProporcional));
            }
            pedido.setValorTotal(totalComDesconto);
        } else {
            pedido.setValorTotal(totalPedidoBruto);
        }

        Pedido saved = pedidoRepo.save(pedido);
        return toDto(saved);
    }

    @Transactional
    public PedidoDTO atualizarStatus(Long id, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido não encontrado"));

        if (novoStatus == StatusPedido.PAGO && pedido.getStatus() != StatusPedido.PAGO) {
            BigDecimal valorTotalParaCashback = pedido.getValorTotal() != null ? pedido.getValorTotal() : BigDecimal.ZERO;
            cashbackService.creditar(pedido.getCliente().getId(), valorTotalParaCashback);
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
        dto.setDataCriacao(p.getDataCriacao()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Evita nulos
        BigDecimal totalBruto = p.getItens().stream()
                .map(item -> item.getValorVenda() != null ? item.getValorVenda().multiply(BigDecimal.valueOf(item.getQuantidade())) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalFinal = p.getValorTotal() != null ? p.getValorTotal() : BigDecimal.ZERO;
        BigDecimal valorDescontoTotal = totalBruto.subtract(totalFinal);

        List<ItemPedidoDTO> itensDto = p.getItens().stream().map(item -> {
            ItemPedidoDTO idto = new ItemPedidoDTO();
            idto.setProdutoNome(item.getProduto().getNome());
            idto.setQuantidade(item.getQuantidade());
            idto.setValorUnitario(item.getValorVenda() != null ? item.getValorVenda() : BigDecimal.ZERO);

            BigDecimal descontoOriginal = item.getDesconto() != null ? item.getDesconto() : BigDecimal.ZERO;
            idto.setDesconto(descontoOriginal);

            BigDecimal subtotalSemDesconto = (item.getValorVenda() != null ? item.getValorVenda() : BigDecimal.ZERO)
                    .multiply(BigDecimal.valueOf(item.getQuantidade()));

            BigDecimal descontoProporcional = BigDecimal.ZERO;
            if (totalBruto.compareTo(BigDecimal.ZERO) > 0) {
                descontoProporcional = descontoOriginal.subtract(
                        subtotalSemDesconto.multiply(valorDescontoTotal)
                                .divide(totalBruto, 4, RoundingMode.HALF_UP)
                );
            }

            idto.setSubtotal(subtotalSemDesconto.subtract(descontoOriginal));
            return idto;
        }).collect(Collectors.toList());

        dto.setItens(itensDto);
        dto.setTotal(totalFinal);
        dto.setValorDescontoTotal(valorDescontoTotal);
        return dto;
    }
}
