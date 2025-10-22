package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;

public class ItemPedidoDTO {

	private Long id;
	private Long produtoId;
	private String produtoNome;
	private Integer quantidade;
	private BigDecimal valorVenda;
	private BigDecimal desconto;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getProdutoId() {
		return produtoId;
	}
	
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	
	public String getProdutoNome() {
		return produtoNome;
	}
	
	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}
	
	public Integer getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	public BigDecimal getValorVenda() {
		return valorVenda;
	}
	
	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}
	
	public BigDecimal getDesconto() {
		return desconto;
	}
	
	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}
}
