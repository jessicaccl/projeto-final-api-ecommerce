package org.serratec.trabalhoFinal.repository;

import org.serratec.trabalhoFinal.domain.Pedido;
import org.serratec.trabalhoFinal.domain.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	boolean existsByClienteIdAndStatus(Long clienteId, StatusPedido status);
	Pedido findByClienteIdAndStatus(Long clienteId, StatusPedido status);

    boolean existsByClienteId(Long clienteId);
}
