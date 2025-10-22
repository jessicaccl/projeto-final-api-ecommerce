package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.serratec.trabalhoFinal.domain.StatusPedido;

public class PedidoDTO {

	private Long id;
	private Long clienteId;
	private StatusPedido status;
	private LocalDateTime dataCriacao;
	private List<ItemPedidoDTO> itens;
	private BigDecimal total;
	
	public PedidoDTO() {}

	public PedidoDTO(Long id, Long clienteId, StatusPedido status, LocalDateTime dataCriacao, List<ItemPedidoDTO> itens,
			BigDecimal total) {
		this.id = id;
		this.clienteId = clienteId;
		this.status = status;
		this.dataCriacao = dataCriacao;
		this.itens = itens;
		this.total = total;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getClienteId() {
		return clienteId;
	}
	
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	
	public StatusPedido getStatus() {
		return status;
	}
	
	public void setStatus(StatusPedido status) {
		this.status = status;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public List<ItemPedidoDTO> getItens() {
		return itens;
	}
	
	public void setItens(List<ItemPedidoDTO> itens) {
		this.itens = itens;
	}
	
	public BigDecimal getTotal() {
		return total;
	}
	
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
