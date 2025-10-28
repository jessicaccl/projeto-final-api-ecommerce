package org.serratec.trabalhoFinal.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class PedidoCriacaoDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private List<ItemPedidoCriacaoDTO> itens;
    
    /*@DecimalMin(value = "0.0", message = "O cashback não pode ser negativo.")
    private BigDecimal cashbackUtilizado = BigDecimal.ZERO;*/
    
    private Boolean usarCashbackIntegral = false;

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
    
    /*public BigDecimal getCashbackUtilizado() {
        return cashbackUtilizado;
    }

    public void setCashbackUtilizado(BigDecimal cashbackUtilizado) {
        this.cashbackUtilizado = cashbackUtilizado;
    }*/
    
    public Boolean getUsarCashbackIntegral() {
        return usarCashbackIntegral;
    }

    public void setUsarCashbackIntegral(Boolean usarCashbackIntegral) {
        this.usarCashbackIntegral = usarCashbackIntegral;
    }
}