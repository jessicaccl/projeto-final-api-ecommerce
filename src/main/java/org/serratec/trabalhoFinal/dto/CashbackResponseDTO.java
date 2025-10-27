package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class CashbackResponseDTO {
    
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal valor;
    
    private LocalDateTime data;
    
    
    
    public CashbackResponseDTO(@NotNull @DecimalMin("0.0") BigDecimal valor, LocalDateTime data) {
		super();
		this.valor = valor;
		this.data = data;
	}

	public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
    
}