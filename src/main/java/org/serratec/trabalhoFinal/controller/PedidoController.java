package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.PedidoCriacaoDTO;
import org.serratec.trabalhoFinal.dto.PedidoDTO;
import org.serratec.trabalhoFinal.service.PedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
    private final PedidoService service;
    
    public PedidoController(PedidoService service) { 
    	this.service = service; 
    }

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
    	
    	return ResponseEntity.ok(service.listarTodos());
    }
    
    @PostMapping
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoCriacaoDTO dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> buscar(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<PedidoDTO> alterarStatus(@PathVariable Long id, @RequestParam String status) {
    	
        return ResponseEntity.ok(service.alterarStatus(id, status));
    }
}
