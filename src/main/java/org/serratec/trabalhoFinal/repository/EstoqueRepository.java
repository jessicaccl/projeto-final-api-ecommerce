package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	
    Estoque findByProdutoId(Long produtoId);
}