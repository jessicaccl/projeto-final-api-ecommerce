package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Assinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

}
