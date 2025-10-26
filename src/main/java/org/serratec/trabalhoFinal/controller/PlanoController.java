package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.PlanoDTO;
import org.serratec.trabalhoFinal.service.PlanoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/planos")
public class PlanoController {

    @Autowired
    private PlanoService planoService;

    @GetMapping
    public ResponseEntity<List<PlanoDTO>> listarPlanos() {
        return ResponseEntity.ok(planoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoDTO> buscarPlanoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanoDTO> cadastrarNovoPlano(@Validated @RequestBody PlanoDTO dto) {
        PlanoDTO novoPlano = planoService.criar(dto);
        return new ResponseEntity<>(novoPlano, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoDTO> alterarPlano(@PathVariable Long id, @Validated @RequestBody PlanoDTO dto) {
        PlanoDTO planoAtualizado = planoService.atualizar(id, dto);
        return ResponseEntity.ok(planoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPlano(@PathVariable Long id) {
        planoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}