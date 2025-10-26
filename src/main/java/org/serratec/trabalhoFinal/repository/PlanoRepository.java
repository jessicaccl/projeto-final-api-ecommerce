package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoRepository extends JpaRepository<Plano, Long> {
    
}