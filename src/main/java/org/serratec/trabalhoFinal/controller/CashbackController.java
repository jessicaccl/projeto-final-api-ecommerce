package org.serratec.trabalhoFinal.controller;

import org.serratec.trabalhoFinal.dto.CashbackDTO;
import org.serratec.trabalhoFinal.service.CashbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cashbacks")
public class CashbackController {

    @Autowired
    private CashbackService cashbackService;
    
    @GetMapping("/cliente/{clienteId}") // Busca saldo de cashback de um cliente
    public ResponseEntity<CashbackDTO> buscarPorCliente(@PathVariable Long clienteId) {
        CashbackDTO dto = cashbackService.buscarPorClienteId(clienteId);
        return ResponseEntity.ok(dto);
    }
    

    @PostMapping("/creditar/{clienteId}/{valor}") // Adiciona saldo (crédito após um pedido)
    public ResponseEntity<CashbackDTO> creditarCashback(@PathVariable Long clienteId, @PathVariable String valor) {
        CashbackDTO dto = cashbackService.creditar(clienteId, new java.math.BigDecimal(valor));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/debitar/{clienteId}/{valor}") // Simulando uso do saldo. Pode ser integrado ao Pedido Service)
    public ResponseEntity<CashbackDTO> debitarCashback(@PathVariable Long clienteId, @PathVariable String valor) {
        CashbackDTO dto = cashbackService.debitar(clienteId, new java.math.BigDecimal(valor));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{clienteId}") // Zerar ou Excluir o registro de cashback
    public ResponseEntity<Void> zerarCashback(@PathVariable Long clienteId) {
        cashbackService.zerarSaldo(clienteId);
        return ResponseEntity.noContent().build();
    }
}