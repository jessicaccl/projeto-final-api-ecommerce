package org.serratec.trabalhoFinal.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;

public class PedidoCriacaoDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private List<ItemPedidoCriacaoDTO> itens;
    
    @DecimalMin(value = "0.0", message = "O cashback não pode ser negativo.")
    private BigDecimal cashbackUtilizado = BigDecimal.ZERO;
    
    

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoCriacaoDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoCriacaoDTO> itens) {
        this.itens = itens;
    }
    
    public BigDecimal getCashbackUtilizado() {
        return cashbackUtilizado;
    }

    public void setCashbackUtilizado(BigDecimal cashbackUtilizado) {
        this.cashbackUtilizado = cashbackUtilizado;
    }
}


