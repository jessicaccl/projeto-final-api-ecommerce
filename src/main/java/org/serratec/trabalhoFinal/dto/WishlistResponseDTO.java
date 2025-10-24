package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;

public class WishlistResponseDTO {

    // ID do Item da Wishlist
    private Long id;
    
    // Dados Essenciais do Produto
    private Long produtoId;
    private String nomeProduto;
    private BigDecimal preco;
    
    // Dados Essenciais da Categoria
    private Long categoriaId;
    private String nomeCategoria;

    // --- Construtor Vazio ---
    public WishlistResponseDTO() {}

    // --- Getters e Setters ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}