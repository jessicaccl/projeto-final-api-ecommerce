package org.serratec.trabalhoFinal.domain;

import java.math.BigDecimal; 

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plano")
    private Long id;

    @NotBlank(message = "O nome do plano não pode ser vazio.")
    @Size(max = 50, message = "O nome do plano deve ter no máximo 50 caracteres.")
    @Column(name = "nome", nullable = false, length = 50)
    private String nome; 

    @NotNull(message = "A quantidade de gramas não pode ser nula.")
    @Positive(message = "A quantidade de gramas deve ser positiva.")
    @Column(name = "gramas")
    private Integer gramas; 

    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres.")
    @Column(name = "descricao")
    private String descricao; 

    @NotNull(message = "O preço não pode ser nulo.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
    @Column(name = "preco_mensal")
    private BigDecimal precoMensal; 

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getGramas() {
        return gramas;
    }

    public void setGramas(Integer gramas) {
        this.gramas = gramas;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoMensal() {
        return precoMensal;
    }

    public void setPrecoMensal(BigDecimal precoMensal) {
        this.precoMensal = precoMensal;
    }
}