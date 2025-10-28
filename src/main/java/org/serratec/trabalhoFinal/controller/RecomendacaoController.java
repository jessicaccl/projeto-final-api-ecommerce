package org.serratec.trabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.dto.ProdutoDTO;
import org.serratec.trabalhoFinal.service.RecomendacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recomendacoes")
public class RecomendacaoController {

    @Autowired
    private RecomendacaoService recomendacaoService;

    @GetMapping("/{clienteId}")
    public ResponseEntity<List<ProdutoDTO>> recomendar(@PathVariable Long clienteId) {
        List<Produto> recomendados = recomendacaoService.recomendarCafes(clienteId);
        List<ProdutoDTO> dtos = recomendados.stream()
                .map(ProdutoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}