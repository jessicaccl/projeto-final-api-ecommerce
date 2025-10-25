package org.serratec.trabalhoFinal.service;

import java.math.BigDecimal;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.serratec.trabalhoFinal.domain.Pedido;
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
    private static final BigDecimal PORCENTAGEM_CASHBACK = new BigDecimal("0.05");
    private final EmailService emailService;

    public CashbackService(CashbackRepository cashbackRepo, ClienteRepository clienteRepo, EmailService emailService) {
        this.cashbackRepo = cashbackRepo;
        this.emailService = emailService;
    }

    public CashbackDTO buscarPorClienteId(Long clienteId) {    // procura saldo
        Cashback c = getCashbackByClienteId(clienteId);
        return toDto(c);
    }
    

@Transactional   
public CashbackDTO adicionar(Long clienteId, BigDecimal valorTotalPedido, Pedido pedido) { 
     Cashback cashback = getCashbackByClienteId(clienteId);
        
     BigDecimal valorCredito = valorTotalPedido.multiply(PORCENTAGEM_CASHBACK);
     BigDecimal novoSaldo = cashback.getSaldo().add(valorCredito);
     cashback.setSaldo(novoSaldo);
        
     Cashback salvo = cashbackRepo.save(cashback); 
     emailService.enviarNotificacaoCashback(pedido, valorCredito, novoSaldo); 
     return toDto(salvo);
    }
    
 
@Transactional
public CashbackDTO debitar(Long clienteId, BigDecimal valorDebito) {    // usa o cashb
	Cashback cashback = getCashbackByClienteId(clienteId);
    
    if (cashback.getSaldo().compareTo(valorDebito) < 0) {
        throw new SaldoInsuficienteException("Saldo de cashback insuficiente para o valor solicitado.");
    }
    
    BigDecimal novoSaldo = cashback.getSaldo().subtract(valorDebito);
    cashback.setSaldo(novoSaldo);
    cashbackRepo.save(cashback);
    return toDto(cashback);
}
    

@Transactional
public void zerarSaldo(Long clienteId) {                             // aqui ele limpa o saldo dos cashbks
    Cashback cashback = getCashbackByClienteId(clienteId);
    cashback.setSaldo(BigDecimal.ZERO);
    cashbackRepo.save(cashback);
}    

public Cashback getCashbackByClienteId(Long clienteId) {        //  Método Auxiliar para obter a Entity
    return cashbackRepo.findByClienteId(clienteId)
           .orElseThrow(() -> new NotFoundException("Cashback não encontrado para o Cliente ID: " + clienteId));
}


private CashbackDTO toDto(Cashback c) {     // mapeam. dtos
   CashbackDTO dto = new CashbackDTO();
   dto.setId(c.getId());
   dto.setClienteId(c.getCliente().getId());
   dto.setNomeCliente(c.getCliente().getNome());
   dto.setSaldo(c.getSaldo());
   return dto;
}

}



    