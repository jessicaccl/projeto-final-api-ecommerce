package org.serratec.trabalhoFinal.repository;

import java.util.List;
import java.util.Set;

import org.serratec.trabalhoFinal.domain.Categoria;
import org.serratec.trabalhoFinal.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoria(Categoria categoria);

	@Query("SELECT p FROM Produto p WHERE p.categoria IN "
			+ "(SELECT p2.categoria FROM Produto p2 WHERE p2.id IN :ids) "
			+ "AND p.id NOT IN :ids ORDER BY p.quantidadeVendida DESC")
	List<Produto> findProdutosRelacionados(@Param("ids") Set<Long> ids);

	List<Produto> findTop5ByOrderByQuantidadeVendidaDesc();
}
