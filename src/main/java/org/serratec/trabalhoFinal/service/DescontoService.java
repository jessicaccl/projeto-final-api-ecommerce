package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DescontoService {

    private final PedidoRepository pedidoRepository;
    private static final BigDecimal descontoporcentagem = new BigDecimal("0.10");

    public DescontoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    public BigDecimal aplicarDesconto(BigDecimal valorTotalInicial, Long clienteId) {
        boolean clienteJaFezPedido = pedidoRepository.existsByClienteId(clienteId);

        if (!clienteJaFezPedido) {
            BigDecimal valorDesconto = valorTotalInicial.multiply(descontoporcentagem).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorFinal = valorTotalInicial.subtract(valorDesconto);
            return valorFinal;
        } else {
            return valorTotalInicial;
        }
    }
}

