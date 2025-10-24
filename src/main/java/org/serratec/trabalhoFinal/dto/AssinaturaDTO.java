package org.serratec.trabalhoFinal.dto;

import org.serratec.trabalhoFinal.domain.StatusAssinatura;

public class AssinaturaDTO {

    private Long id;
    private String plano;
    private String endereco;
    private String tipoCafe;
    private Long idCliente;
    
    private StatusAssinatura status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTipoCafe() {
        return tipoCafe;
    }

    public void setTipoCafe(String tipoCafe) {
        this.tipoCafe = tipoCafe;
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
}