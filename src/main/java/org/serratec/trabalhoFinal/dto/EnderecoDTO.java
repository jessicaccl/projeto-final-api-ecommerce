package org.serratec.trabalhoFinal.dto;

import org.serratec.trabalhoFinal.domain.Endereco;

public class EnderecoDTO {

	private String cep;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;

	public EnderecoDTO() {}
	
	public EnderecoDTO(Endereco e) {
		this.cep = e.getCep();
		this.logradouro = e.getLogradouro();
		this.complemento = e.getComplemento();
		this.bairro = e.getBairro();
		this.localidade = e.getLocalidade();
		this.uf = e.getUf();
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}