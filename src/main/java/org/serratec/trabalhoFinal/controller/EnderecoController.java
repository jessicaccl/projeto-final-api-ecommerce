package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.EnderecoDTO;
import org.serratec.trabalhoFinal.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoService enderecoService;
	
	@GetMapping
    public ResponseEntity<List<EnderecoDTO>> listarTodos() {
        List<EnderecoDTO> enderecos = enderecoService.listarTodos();
        return ResponseEntity.ok(enderecos);
    }
	
	@GetMapping("/{cep}")
	public ResponseEntity<EnderecoDTO> buscar(@PathVariable String cep) {
		
		EnderecoDTO enderecoDTO = enderecoService.buscar(cep);
		if (enderecoDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(enderecoDTO);
	}
	
}
