package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
	
	public Endereco findByCep(String cep);
}
