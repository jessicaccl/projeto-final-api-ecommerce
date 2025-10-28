package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}