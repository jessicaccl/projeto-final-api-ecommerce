package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.domain.WishlistItem;
import org.serratec.trabalhoFinal.dto.WishlistResponseDTO; // NOVO IMPORT: Para a resposta simplificada!
import org.serratec.trabalhoFinal.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes/{clienteId}/wishlist")
public class WishlistController {
    
    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    // Adiciona um produto à lista de desejos (Create)
    // Retorna a Entidade WishlistItem completa, o que é aceitável, mas você pode mudar para WishlistResponseDTO se preferir.
    @PostMapping("/{produtoId}")
    public ResponseEntity<WishlistItem> adicionar(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        WishlistItem item = service.adicionarProduto(clienteId, produtoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    // Lista os produtos na lista de desejos (Read)
    // AJUSTADO: Agora retorna uma lista de WishlistResponseDTO
    // Ex: GET /clientes/1/wishlist
    @GetMapping
    public ResponseEntity<List<WishlistResponseDTO>> listar(@PathVariable Long clienteId) {
        // O WishlistService.listarProdutos agora retorna List<WishlistResponseDTO>
        List<WishlistResponseDTO> lista = service.listarProdutos(clienteId);
        return ResponseEntity.ok(lista);
    }

    // Remove um produto da lista de desejos (Delete)
    // Ex: DELETE /clientes/1/wishlist/5 
    @DeleteMapping("/{produtoId}")
    public ResponseEntity<Void> remover(@PathVariable Long clienteId, @PathVariable Long produtoId) {
        service.removerProduto(clienteId, produtoId);
        return ResponseEntity.noContent().build();
    }
}