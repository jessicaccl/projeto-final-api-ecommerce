package org.serratec.trabalhoFinal.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido")
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Cliente cliente;

	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;

	private LocalDateTime dataCriacao = LocalDateTime.now();

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemPedido> itens = new ArrayList<>();

	@Column(precision = 10, scale = 2)
	private BigDecimal cashbackUtilizado = BigDecimal.ZERO;

	public BigDecimal getTotal() {

		BigDecimal subtotal = itens.stream()
				.map(i -> i.getValorVenda().multiply(new BigDecimal(i.getQuantidade()))
						.subtract(i.getDesconto() == null ? BigDecimal.ZERO : i.getDesconto()))
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		return subtotal.subtract(cashbackUtilizado);
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

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(List<ItemPedido> itens) {
		this.itens = itens;
	}
	
	public BigDecimal getCashbackUtilizado() {
        return cashbackUtilizado;
    }

    public void setCashbackUtilizado(BigDecimal cashbackUtilizado) {
        this.cashbackUtilizado = cashbackUtilizado;
    }
}

