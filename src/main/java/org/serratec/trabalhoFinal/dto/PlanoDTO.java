package org.serratec.trabalhoFinal.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class PlanoDTO {

    private Long id;

    @NotBlank(message = "O nome do plano não pode ser vazio.")
    @Size(max = 50, message = "O nome do plano deve ter no máximo 50 caracteres.")
    private String nome;

    @NotNull(message = "A quantidade de gramas não pode ser nula.")
    @Positive(message = "A quantidade de gramas deve ser positiva.")
    private Integer gramas;

    @Size(max = 200, message = "A descrição deve ter no máximo 200 caracteres.")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
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