<<<<<<< HEAD
package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.NotBlank;

public class ClienteAtualizacaoDTO {

    @NotBlank
    private String nome;
    
    @NotBlank
    private String telefone;
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String senha;
    
    @NotBlank
    private String cpf;

    @NotBlank
    private String cep;
    
    public ClienteAtualizacaoDTO() {}

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
}
=======
package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.NotBlank;

public class ClienteAtualizacaoDTO {

    @NotBlank
    private String nome;
    
    @NotBlank
    private String telefone;
    
    @NotBlank
    private String email;
    
    @NotBlank
    private String senha;
    
    @NotBlank
    private String cpf;

    @NotBlank
    private String cep;
    
    public ClienteAtualizacaoDTO() {}

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
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
}
>>>>>>> upstream/main
