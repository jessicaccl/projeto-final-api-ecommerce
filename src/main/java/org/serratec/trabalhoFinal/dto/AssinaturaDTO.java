package org.serratec.trabalhoFinal.dto;

import org.serratec.trabalhoFinal.domain.StatusAssinatura;

public class AssinaturaDTO {

	private Long id;
	private String endereco;
	private Long idCliente;
	private StatusAssinatura status;
	private Long idPlano;
	
	public Long getId() {
		return id;
	}
 	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getEndereco() {
		return endereco;
	}
	
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	
	public StatusAssinatura getStatus() {
		return status;
	}
	
	public void setStatus(StatusAssinatura status) {
		this.status = status;
	}
	
	public Long getIdPlano() {
		return idPlano;
	}

	public void setIdPlano(Long idPlano) {
		this.idPlano = idPlano;
	}
}
	
