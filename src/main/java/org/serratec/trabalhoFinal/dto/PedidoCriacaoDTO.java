package org.serratec.trabalhoFinal.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;

public class PedidoCriacaoDTO {

    @NotNull
    private Long clienteId;

    @NotNull
    private List<ItemPedidoCriacaoDTO> itens;

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
}
