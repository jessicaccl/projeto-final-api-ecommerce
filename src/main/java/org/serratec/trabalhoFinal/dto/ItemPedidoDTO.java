package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;

public class ItemPedidoDTO {

    private String produtoNome;
    private Integer quantidade;
    private BigDecimal valorUnitario;
    private BigDecimal desconto; // desconto individual
    //private BigDecimal descontoPrimeiroPedido; // novo campo
    private BigDecimal subtotal;

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    //public BigDecimal getDescontoPrimeiroPedido() {
       // return descontoPrimeiroPedido;
   // }

    //public void setDescontoPrimeiroPedido(BigDecimal descontoPrimeiroPedido) {
        //this.descontoPrimeiroPedido = descontoPrimeiroPedido;
   // }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
