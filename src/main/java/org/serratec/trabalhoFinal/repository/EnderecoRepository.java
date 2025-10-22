package org.serratec.trabalhoFinal.repository;

import java.util.Optional;
import org.serratec.trabalhoFinal.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByCep(String cep);
}
