package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ClienteCriacaoDTO {

	@NotBlank
	private String nome;
	
	@NotBlank
	private String telefone;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos")
	private String cpf;
	
	@NotBlank
	private String cep;
	
	private EnderecoDTO enderecoDTO;
	
	public ClienteCriacaoDTO(@NotBlank String nome, @NotBlank String telefone, @NotBlank @Email String email,
			@NotBlank @Pattern(regexp = "\\d{11}", message = "CPF deve ter 11 dígitos") String cpf,
			@NotBlank String cep, EnderecoDTO enderecoDTO) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.cpf = cpf;
		this.cep = cep;
		this.enderecoDTO = enderecoDTO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
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

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public EnderecoDTO getEnderecoDTO() {
		return enderecoDTO;
	}

	public void setEnderecoDTO(EnderecoDTO enderecoDTO) {
		this.enderecoDTO = enderecoDTO;
	}
}
