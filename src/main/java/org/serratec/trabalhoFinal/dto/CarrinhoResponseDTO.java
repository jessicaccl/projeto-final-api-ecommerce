package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;
import java.util.List;

public class CarrinhoResponseDTO {
	
	private List<ItemPedidoDTO> carrinho;
	private BigDecimal valorTotal;
	
	public CarrinhoResponseDTO(List<ItemPedidoDTO> carrinho, BigDecimal valorTotal) {
		this.carrinho = carrinho;
		this.valorTotal = valorTotal;
	}
	
	
	public List<ItemPedidoDTO> getCarrinho() {
		return carrinho;
	}
	public void setCarrinho(List<ItemPedidoDTO> carrinho) {
		this.carrinho = carrinho;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}


}
