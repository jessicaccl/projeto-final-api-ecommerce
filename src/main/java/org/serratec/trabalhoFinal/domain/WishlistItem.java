package org.serratec.trabalhoFinal.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "wishlist_item", uniqueConstraints = {
    // Garante que um cliente não possa adicionar o mesmo produto duas vezes
    @UniqueConstraint(columnNames = {"cliente_id", "produto_id"})
})
public class WishlistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento com Cliente
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Relacionamento com Produto
    @ManyToOne(optional = false)
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public WishlistItem() {}

    // Construtor útil para criar o item
    public WishlistItem(Cliente cliente, Produto produto) {
        this.cliente = cliente;
        this.produto = produto;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}