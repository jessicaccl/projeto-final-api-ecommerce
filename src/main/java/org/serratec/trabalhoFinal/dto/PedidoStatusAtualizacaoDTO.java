package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.NotBlank;

public class PedidoStatusAtualizacaoDTO {


    @NotBlank(message = "O status é obrigatório.")
    private String novoStatus;

    public String getNovoStatus() {
        return novoStatus;
    }

    public void setNovoStatus(String novoStatus) {
        this.novoStatus = novoStatus;
    }
}