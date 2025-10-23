<<<<<<< HEAD
package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.ProdutoDTO;
import org.serratec.trabalhoFinal.service.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {
	
	private final ProdutoService service;
	public ProdutoController(ProdutoService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar() {
		
		return ResponseEntity.ok(service.listar());
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO dto){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizar (@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto){
		
		return ResponseEntity.ok(service.atualizar(id, dto));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


=======
package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.ProdutoDTO;
import org.serratec.trabalhoFinal.service.ProdutoService;
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
@RequestMapping("/produtos")
public class ProdutoController {
	
	private final ProdutoService service;
	public ProdutoController(ProdutoService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar() {
		
		return ResponseEntity.ok(service.listar());
	}
	
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        ProdutoDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> criar(@Valid @RequestBody ProdutoDTO dto){
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProdutoDTO> atualizar (@PathVariable Long id, @Valid @RequestBody ProdutoDTO dto){
		
		return ResponseEntity.ok(service.atualizar(id, dto));
	}
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }


>>>>>>> upstream/main
}