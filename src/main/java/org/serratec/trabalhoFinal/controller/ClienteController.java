package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.ClienteCriacaoDTO;
import org.serratec.trabalhoFinal.dto.ClienteDTO;
import org.serratec.trabalhoFinal.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
    private final ClienteService service;
    public ClienteController(ClienteService service) { 
    	this.service = service; 
    }
    
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        List<ClienteDTO> clientes = service.listarTodos();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteCriacaoDTO dto) {
    	
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteCriacaoDTO dto) {
    	
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscar(@PathVariable Long id) {
    	
        return ResponseEntity.ok(service.buscarPorId(id));
    }
}
