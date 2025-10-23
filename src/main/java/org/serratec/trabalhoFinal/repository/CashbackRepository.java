// org.serratec.trabalhoFinal.repository.CashbackRepository
package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CashbackRepository extends JpaRepository<Cashback, Long> {
   Optional<Cashback> findByClienteId(Long clienteId);
}