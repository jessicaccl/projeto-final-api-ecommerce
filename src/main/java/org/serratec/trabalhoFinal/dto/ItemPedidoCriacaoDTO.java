package org.serratec.trabalhoFinal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoCriacaoDTO {

    @NotNull
    private Long produtoId;

    @NotNull
    @Min(1)
    private Integer quantidade;
    
    


    public ItemPedidoCriacaoDTO(@NotNull Long produtoId, @NotNull @Min(1) Integer quantidade) {
		super();
		this.produtoId = produtoId;
		this.quantidade = quantidade;
	}

	public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }    
}
