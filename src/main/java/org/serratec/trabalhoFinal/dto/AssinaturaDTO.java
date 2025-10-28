package org.serratec.trabalhoFinal.dto;

import org.serratec.trabalhoFinal.domain.StatusAssinatura;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssinaturaDTO {

	private Long id;
	
	@NotBlank(message = "O endereço de entrega não pode ser vazio.")
	private String endereco;
	
	@NotNull(message = "O idCliente não pode ser nulo.")
	private Long idCliente;
	
	private StatusAssinatura status;
	
	@NotNull(message = "O idPlano não pode ser nulo.")
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