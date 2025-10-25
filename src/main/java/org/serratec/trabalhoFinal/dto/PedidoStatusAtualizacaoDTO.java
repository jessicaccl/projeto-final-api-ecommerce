package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.NotBlank;
import org.serratec.trabalhoFinal.domain.StatusPedido;

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