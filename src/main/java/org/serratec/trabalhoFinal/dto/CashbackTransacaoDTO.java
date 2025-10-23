package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CashbackTransacaoDTO {
    
    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal valor;
    
    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}