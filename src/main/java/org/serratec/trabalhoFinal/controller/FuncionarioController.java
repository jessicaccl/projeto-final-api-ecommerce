package org.serratec.trabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Funcionario;
import org.serratec.trabalhoFinal.dto.FuncionarioCadastroDTO;
import org.serratec.trabalhoFinal.dto.FuncionarioDTO;
import org.serratec.trabalhoFinal.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;
    
    public FuncionarioController(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@GetMapping
    public ResponseEntity<List<FuncionarioDTO>> listarTudo() {
        List<FuncionarioDTO> dtos = funcionarioService.listarTodosEntidades()
            .stream()
            .map(f -> new FuncionarioDTO(
                f.getId(),
                f.getNome(),
                f.getEmail(),
                f.getUsuario() != null ? f.getUsuario().getRole() : null
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); 
    }
    
    @PostMapping
    public ResponseEntity<FuncionarioDTO> cadastrar(@Valid @RequestBody FuncionarioCadastroDTO funcionario) {
        Funcionario novo = funcionarioService.cadastrarFuncionario(funcionario);
        
        FuncionarioDTO funcionarioDTO = new FuncionarioDTO(
        		novo.getId(),
        		novo.getNome(),
        		novo.getEmail(),
        		novo.getUsuario() != null ? novo.getUsuario().getRole() : null
        		);
        
        return new ResponseEntity<>(funcionarioDTO, HttpStatus.CREATED);
    }
}
