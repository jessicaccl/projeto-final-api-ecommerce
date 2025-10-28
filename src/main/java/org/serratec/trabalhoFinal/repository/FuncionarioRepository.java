package org.serratec.trabalhoFinal.repository;

import java.util.Optional;

import org.serratec.trabalhoFinal.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	Optional<Funcionario> findByEmail(String email);
}