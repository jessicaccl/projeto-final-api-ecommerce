// org.serratec.trabalhoFinal.repository.CashbackRepository
package org.serratec.trabalhoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashbackRepository extends JpaRepository<Cashback, Long> {
   Optional<Cashback> findByClienteId(Long clienteId);
   
   List<Cashback> findByClienteIdAndIsActiveTrue(Long clienteId);
}