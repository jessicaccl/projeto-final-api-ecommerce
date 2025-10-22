package org.serratec.trabalhoFinal.repository;

import java.util.Optional;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
	Optional<Cliente> findByCpf(String cpf);
}
