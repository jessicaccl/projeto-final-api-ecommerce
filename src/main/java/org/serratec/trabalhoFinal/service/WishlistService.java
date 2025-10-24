package org.serratec.trabalhoFinal.service;

import java.util.List;
import java.util.stream.Collectors; // Import necessário para usar stream().collect()

import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.Produto;
import org.serratec.trabalhoFinal.domain.WishlistItem;
import org.serratec.trabalhoFinal.dto.WishlistResponseDTO; // NOVO IMPORT!
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
    
    // Injete os repositórios necessários
    public WishlistService(WishlistItemRepository wishlistRepo, ClienteRepository clienteRepo, ProdutoRepository produtoRepo) {
        this.wishlistRepo = wishlistRepo;
        this.clienteRepo = clienteRepo;
        this.produtoRepo = produtoRepo;
    }
    
    // --- C (Create) ---
    @Transactional
    public WishlistItem adicionarProduto(Long clienteId, Long produtoId) {
        // 1. Verifica se Cliente e Produto existem
        Cliente cliente = clienteRepo.findById(clienteId)
                                     .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
        Produto produto = produtoRepo.findById(produtoId)
                                     .orElseThrow(() -> new NotFoundException("Produto não encontrado"));
        
        // 2. Verifica se o item já está na lista
        if (wishlistRepo.findByClienteIdAndProdutoId(clienteId, produtoId).isPresent()) {
            throw new IllegalArgumentException("Esse produto já está na sua lista de desejos.");
        }
        
        // 3. Cria e salva o item
        WishlistItem novoItem = new WishlistItem(cliente, produto);
        return wishlistRepo.save(novoItem);
    }
    
    // --- R (Read) - ATUALIZADO PARA RETORNAR WishlistResponseDTO ---
    public List<WishlistResponseDTO> listarProdutos(Long clienteId) {
        // Verifica se o cliente existe (opcional, mas recomendado)
        if (!clienteRepo.existsById(clienteId)) {
            throw new NotFoundException("Cliente não encontrado");
        }
        
        // Busca a lista de Entidades e converte para DTO
        return wishlistRepo.findByClienteId(clienteId)
                           .stream()
                           .map(this::toDto) // Chama o novo método de conversão
                           .collect(Collectors.toList());
    }
    
    // --- D (Delete) ---
    @Transactional
    public void removerProduto(Long clienteId, Long produtoId) {
        // Busca e verifica se o item existe na lista
        WishlistItem item = wishlistRepo.findByClienteIdAndProdutoId(clienteId, produtoId)
                                        .orElseThrow(() -> new NotFoundException("Produto não encontrado na lista de desejos do cliente."));

        // Remove o item
        wishlistRepo.delete(item);
    }
    
    // --- NOVO MÉTODO: Converte Entidade para DTO de Resposta Simplificado ---
    private WishlistResponseDTO toDto(WishlistItem item) {
        WishlistResponseDTO dto = new WishlistResponseDTO();
        
        dto.setId(item.getId());
        
        // Mapeamento dos dados essenciais do Produto
        if (item.getProduto() != null) {
            dto.setProdutoId(item.getProduto().getId());
            dto.setNomeProduto(item.getProduto().getNome());
            dto.setPreco(item.getProduto().getPreco());
            
            // Mapeamento dos dados essenciais da Categoria
            if (item.getProduto().getCategoria() != null) {
                dto.setCategoriaId(item.getProduto().getCategoria().getId());
                dto.setNomeCategoria(item.getProduto().getCategoria().getNome());
            }
        }
        
        return dto;
    }
}