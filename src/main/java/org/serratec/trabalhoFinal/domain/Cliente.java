package org.serratec.trabalhoFinal.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String nome;
	
	@NotBlank
	private String telefone;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	private String senha;
	
	@NotBlank
	private String cpf;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_endereco")
	private Endereco endereco;
	
	public Cliente() {}

	public Cliente(Long id, @NotBlank String nome, @NotBlank String telefone, @NotBlank @Email String email,
			@NotBlank String cpf, Endereco endereco, String senha) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.endereco = endereco;
		this.senha = senha;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
