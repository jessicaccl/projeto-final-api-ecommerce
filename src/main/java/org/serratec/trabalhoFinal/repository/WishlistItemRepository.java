package org.serratec.trabalhoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalhoFinal.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByClienteId(Long clienteId);

    Optional<WishlistItem> findByClienteIdAndProdutoId(Long clienteId, Long produtoId);
    
    void deleteByClienteIdAndProdutoId(Long clienteId, Long produtoId);
}