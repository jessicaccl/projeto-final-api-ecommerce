package org.serratec.trabalhoFinal.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.trabalhoFinal.dto.EstoqueDTO;
import org.serratec.trabalhoFinal.service.EstoqueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }
    
    @GetMapping
    public ResponseEntity<List<EstoqueDTO>> listarTodos() {
    	List<EstoqueDTO> estoqueDTO = estoqueService.listarTodosProdutos();
    	return ResponseEntity.ok(estoqueDTO);
    }
    public ResponseEntity<List<EstoqueDTO>> listarTudo() {
        List<EstoqueDTO> dtos = estoqueService.listarTodosProdutos()
            .stream()
            .map(e -> new EstoqueDTO(
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtos); 
    }
    
    @GetMapping("/{produtoId}")
    public ResponseEntity<EstoqueDTO> consultarEstoque(@PathVariable Long produtoId) {
    	
        EstoqueDTO dto = estoqueService.getEstoquePorProduto(produtoId);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EstoqueDTO> adicionarEstoque(@RequestParam Long produtoId, @RequestParam Integer quantidade) {
    	
        estoqueService.adicionarEstoque(produtoId, quantidade);
        EstoqueDTO dto = estoqueService.getEstoquePorProduto(produtoId);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<EstoqueDTO> atualizarEstoque(@PathVariable Long produtoId, @RequestParam Integer quantidade) {
    	
        estoqueService.adicionarEstoque(produtoId, quantidade); // Pode usar mesmo método de adicionar
        EstoqueDTO dto = estoqueService.getEstoquePorProduto(produtoId);
        return ResponseEntity.ok(dto);
    }
}
