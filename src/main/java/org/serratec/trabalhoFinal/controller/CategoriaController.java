package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.CategoriaDTO;
import org.serratec.trabalhoFinal.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	private final CategoriaService service;
	public CategoriaController(CategoriaService service) { 
		this.service = service; 
	}
	
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listar() {
		
		return ResponseEntity.ok(service.listar()); 
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        CategoriaDTO dto = (CategoriaDTO) service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping
	public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) {
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
		
		return ResponseEntity.ok(service.atualizar(id, dto));
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
	    service.deletar(id);
	    return ResponseEntity.noContent().build();
	}
}
