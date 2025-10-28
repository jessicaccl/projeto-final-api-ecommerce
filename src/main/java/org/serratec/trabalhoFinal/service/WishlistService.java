package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors;
import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.domain.WishlistItem;
import org.serratec.trabalhoFinal.dto.WishlistResponseDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.serratec.trabalhoFinal.repository.ProdutoRepository;
import org.serratec.trabalhoFinal.repository.WishlistItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WishlistService {

    private final WishlistItemRepository wishlistRepo;
    private final ClienteRepository clienteRepo;
    private final ProdutoRepository produtoRepo;
    
    public WishlistService(WishlistItemRepository wishlistRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo) {
        this.wishlistRepo = wishlistRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }
    
    @Transactional
    public WishlistItem adicionarProduto(Long clienteId, Long produtoId) {
        //Verifica se Cliente e Produto existem
        Cliente cliente = clienteRepo.findById(clienteId)
                                     .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        Produto produto = produtoRepo.findById(produtoId)
                                     .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        
        //Verifica se o item já está na lista
        if (wishlistRepo.findByClienteIdAndProdutoId(clienteId, produtoId).isPresent()) {
            throw new IllegalArgumentException("Esse produto já está na sua lista de desejos.");
        }
        
 
        WishlistItem novoItem = new WishlistItem(cliente, produto);
        return wishlistRepo.save(novoItem);
    }
    
    public List<WishlistResponseDTO> listarProdutos(Long clienteId) {
 
        if (!clienteRepo.existsById(clienteId)) {
            throw new NotFoundException("Cliente não encontrado");
        }
        
        return wishlistRepo.findByClienteId(clienteId)
                           .stream()
                           .map(this::toDto)
                           .collect(Collectors.toList());
    }
    
    @Transactional
    public void removerProduto(Long clienteId, Long produtoId) {
        // Busca e verifica se o item existe na lista
        WishlistItem item = wishlistRepo.findByClienteIdAndProdutoId(clienteId, produtoId)
                                        .orElseThrow(() -> new NotFoundException("Produto não encontrado na lista de desejos do cliente."));

        wishlistRepo.delete(item);
    }
    
    private WishlistResponseDTO toDto(WishlistItem item) {
        WishlistResponseDTO dto = new WishlistResponseDTO();
        
        dto.setId(item.getId());
        

        if (item.getProduto() != null) {
            dto.setProdutoId(item.getProduto().getId());
            dto.setNomeProduto(item.getProduto().getNome());
            dto.setPreco(item.getProduto().getPreco());
            
   
            if (item.getProduto().getCategoria() != null) {
                dto.setCategoriaId(item.getProduto().getCategoria().getId());
                dto.setNomeCategoria(item.getProduto().getCategoria().getNome());
            }
        }
        
        return dto;
    }
}