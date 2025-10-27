package org.serratec.trabalhoFinal.dto;

public class EstoqueDTO {

	private Long Id;
	private String Nome;
	private Integer quantidade;
	
	public EstoqueDTO() {}

	public EstoqueDTO(Long id, String nome, Integer quantidade) {
		Id = id;
		Nome = nome;
		this.quantidade = quantidade;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
}
