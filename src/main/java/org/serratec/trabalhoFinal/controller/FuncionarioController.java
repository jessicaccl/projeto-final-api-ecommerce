package org.serratec.trabalhoFinal.controller;

import org.serratec.trabalhoFinal.domain.Funcionario;
import org.serratec.trabalhoFinal.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<Funcionario> cadastrar(@RequestBody Funcionario funcionario) {
        Funcionario novo = funcionarioService.cadastrarFuncionario(funcionario);
        return ResponseEntity.ok(novo);
    }
}
