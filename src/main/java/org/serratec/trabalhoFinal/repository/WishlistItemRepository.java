package org.serratec.trabalhoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalhoFinal.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    // Método 1: Listar todos os itens de um cliente específico (Read)
    List<WishlistItem> findByClienteId(Long clienteId);

    // Método 2: Buscar um item específico para verificar se já existe ou para deletar (Auxiliar)
    Optional<WishlistItem> findByClienteIdAndProdutoId(Long clienteId, Long produtoId);
    
    // Método 3: Deletar um item diretamente pela referência (Delete)
    void deleteByClienteIdAndProdutoId(Long clienteId, Long produtoId);
}