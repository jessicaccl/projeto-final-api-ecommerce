package org.serratec.trabalhoFinal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;

@Entity
@Table(name = "cashback")
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @DecimalMin(value = "0.0", inclusive = true)
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;
    
    private LocalDateTime dataVencimento;
    
    private boolean isActive = true;


    public Cashback() {}

    public Cashback(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = BigDecimal.ZERO;
        this.dataVencimento = LocalDateTime.now().plusDays(30);
    }
    
    public Cashback(Cliente cliente, BigDecimal saldo) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.dataVencimento = LocalDateTime.now().plusDays(30);
    }

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

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

	public LocalDateTime getDate() {
		return dataVencimento;
	}

	public void setDate(LocalDateTime date) {
		this.dataVencimento = date;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
    
}



