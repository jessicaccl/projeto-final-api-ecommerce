package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.CashbackDTO;
import org.serratec.trabalhoFinal.dto.CashbackResponseDTO;
import org.serratec.trabalhoFinal.service.CashbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/cashbacks")
public class CashbackController {

    @Autowired
    private CashbackService cashbackService;
    
    @GetMapping("/cliente/{clienteId}")          // ----- Busca registros de cashbacks ATIVOS de um cliente
    public ResponseEntity<List<CashbackResponseDTO>> buscarPorCliente(@PathVariable Long clienteId) {
        List<CashbackResponseDTO> listDto = cashbackService.buscarPorClienteId(clienteId);
        return ResponseEntity.ok(listDto);
    }
    

    @PostMapping("/adicionar/{clienteId}") // ----- Adiciona crédito
    public ResponseEntity<CashbackDTO> creditarCashback(@PathVariable Long clienteId, @RequestBody String valor) {
        CashbackDTO dto = cashbackService.adicionar(clienteId, new java.math.BigDecimal(valor));
        return ResponseEntity.ok(dto);
    }

  
}


