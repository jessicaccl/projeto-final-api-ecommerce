package org.serratec.trabalhoFinal.repository;

import java.util.List;

import org.serratec.trabalhoFinal.domain.Categoria;
import org.serratec.trabalhoFinal.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	List<Produto> findByCategoria(Categoria categoria);
}
