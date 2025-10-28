package org.serratec.trabalhoFinal.controller;

import java.util.List;

import org.serratec.trabalhoFinal.dto.WishlistInputDTO;
import org.serratec.trabalhoFinal.dto.WishlistResponseDTO;
import org.serratec.trabalhoFinal.dto.SuccessResponseDTO; // NOVO IMPORT!
import org.serratec.trabalhoFinal.service.WishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes/{clienteId}/wishlist")
public class WishlistController {
    
    private final WishlistService wishlistService;


    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping
   
    public ResponseEntity<SuccessResponseDTO> adicionarProduto(
            @PathVariable Long clienteId,
            @Valid @RequestBody WishlistInputDTO inputDTO) {
        
        // Chama o serviço para adicionar o produto. 
        wishlistService.adicionarProduto(clienteId, inputDTO.getProdutoId());
        
        // Cria o objeto de resposta JSON
        SuccessResponseDTO response = new SuccessResponseDTO(
            "Produto adicionado à sua lista de desejos com sucesso!"
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    

    @GetMapping
    public ResponseEntity<List<WishlistResponseDTO>> listarProdutos(@PathVariable Long clienteId) {
        
        List<WishlistResponseDTO> lista = wishlistService.listarProdutos(clienteId);
        
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/{produtoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna 204
    public void removerProduto(
            @PathVariable Long clienteId, 
            @PathVariable Long produtoId) {
        

        wishlistService.removerProduto(clienteId, produtoId);
        
    }
}