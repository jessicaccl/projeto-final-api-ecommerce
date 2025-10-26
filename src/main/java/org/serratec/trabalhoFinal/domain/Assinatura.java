package org.serratec.trabalhoFinal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;        
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Assinatura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_assinatura")
	private Long id;
	
	@NotBlank(message = "O endereço não pode ser vazio.")
	@Size(max = 100, message = "O endereço deve ter no máximo 100 caracteres.")
	@Column(name = "endereco", nullable = false, length = 100)
	private String endereco;
	
	@ManyToOne
	@JoinColumn(name = "id_plano") 
	private Plano plano;
		    
	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@Enumerated(EnumType.STRING) 
	@Column(name = "status")
	private StatusAssinatura status;
	
	
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

	public Cliente getCliente() {
	    return cliente;
	    }

	public void setCliente(Cliente cliente) {
	    this.cliente = cliente;
	    }

	public StatusAssinatura getStatus() {
		return status;
		}

	public void setStatus(StatusAssinatura status) {
		this.status = status;
		}
		
	public Plano getPlano() {
		return plano;
	}

	public void setPlano(Plano plano) {
		this.plano = plano;
	}
}