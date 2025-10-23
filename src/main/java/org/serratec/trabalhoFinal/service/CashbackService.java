package org.serratec.trabalhoFinal.service;

import java.math.BigDecimal;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.serratec.trabalhoFinal.dto.CashbackDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.exception.SaldoInsuficienteException;
import org.serratec.trabalhoFinal.repository.CashbackRepository;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashbackService {

    private final CashbackRepository cashbackRepo;
    private final ClienteRepository clienteRepo;
    // Defina a porcentagem de cashback. Ex: 5% = 0.05
    private static final BigDecimal PORCENTAGEM_CASHBACK = new BigDecimal("0.05");

    public CashbackService(CashbackRepository cashbackRepo, ClienteRepository clienteRepo) {
        this.cashbackRepo = cashbackRepo;
        this.clienteRepo = clienteRepo;
    }

    // --- R (GET) - Buscar Saldo ---
    public CashbackDTO buscarPorClienteId(Long clienteId) {
        Cashback c = getCashbackByClienteId(clienteId);
        return toDto(c);
    }
    
    // --- POST - Creditar Saldo (Adicionar) ---
    @Transactional
    public CashbackDTO creditar(Long clienteId, BigDecimal valorTotalPedido) {
        Cashback cashback = getCashbackByClienteId(clienteId);
        
        // Calcula o valor a ser creditado (ex: 5% do valor total do pedido)
        BigDecimal valorCredito = valorTotalPedido.multiply(PORCENTAGEM_CASHBACK);
        
        BigDecimal novoSaldo = cashback.getSaldo().add(valorCredito);
        cashback.setSaldo(novoSaldo);
        
        cashbackRepo.save(cashback);
        return toDto(cashback);
    }
    
    // --- PUT - Debitar Saldo (Usar) ---
    @Transactional
    public CashbackDTO debitar(Long clienteId, BigDecimal valorDebito) {
        Cashback cashback = getCashbackByClienteId(clienteId);
        
        if (cashback.getSaldo().compareTo(valorDebito) < 0) {
            throw new SaldoInsuficienteException("Saldo de cashback insuficiente para o valor solicitado.");
        }
        
        BigDecimal novoSaldo = cashback.getSaldo().subtract(valorDebito);
        cashback.setSaldo(novoSaldo);
        
        cashbackRepo.save(cashback);
        return toDto(cashback);
    }
    
    // --- DELETE - Zerar/Resetar Saldo (Lógica Administrativa) ---
    @Transactional
    public void zerarSaldo(Long clienteId) {
        Cashback cashback = getCashbackByClienteId(clienteId);
        cashback.setSaldo(BigDecimal.ZERO);
        cashbackRepo.save(cashback);
    }
    
    // --- Método Auxiliar para obter a Entity ---
    public Cashback getCashbackByClienteId(Long clienteId) {
         return cashbackRepo.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cashback não encontrado para o Cliente ID: " + clienteId));
    }

    // --- Mapeamento DTO ---
    private CashbackDTO toDto(Cashback c) {
        CashbackDTO dto = new CashbackDTO();
        dto.setId(c.getId());
        dto.setClienteId(c.getCliente().getId());
        dto.setNomeCliente(c.getCliente().getNome());
        dto.setSaldo(c.getSaldo());
        return dto;
    }
}