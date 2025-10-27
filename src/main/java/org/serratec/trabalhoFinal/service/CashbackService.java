package org.serratec.trabalhoFinal.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.serratec.trabalhoFinal.domain.Cashback;
import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.dto.CashbackDTO;
import org.serratec.trabalhoFinal.dto.CashbackResponseDTO;
import org.serratec.trabalhoFinal.exception.NotFoundException;
import org.serratec.trabalhoFinal.repository.CashbackRepository;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CashbackService {

	private final CashbackRepository cashbackRepo;
	private static final BigDecimal PORCENTAGEM_CASHBACK = new BigDecimal("0.05");
	private final ClienteRepository clienteRepo;

	public CashbackService(CashbackRepository cashbackRepo, ClienteRepository clienteRepo) {
		this.cashbackRepo = cashbackRepo;
		this.clienteRepo = clienteRepo;
	}

	public List<CashbackResponseDTO> buscarPorClienteId(Long clienteId) { // procura saldo
		List<Cashback> c = getCashbackByClienteId(clienteId);
		List<CashbackResponseDTO> cDTO = new ArrayList<CashbackResponseDTO>();
		for (Cashback cashback : c) {
			CashbackResponseDTO cashbackDTO = toTransacaoDto(cashback);
			cDTO.add(cashbackDTO);
		}
		return cDTO;
	}

	@Transactional
	public CashbackDTO adicionar(Long clienteId, BigDecimal valor) {
		Cliente cliente = clienteRepo.findById(clienteId)
				.orElseThrow(() -> new NotFoundException("Cliente não encontrado com o ID: " + clienteId));
		Cashback cashback = new Cashback(cliente, valor);

		return toDto(cashbackRepo.save(cashback));
	}

	public List<Cashback> getCashbackByClienteId(Long clienteId) { // Método Auxiliar para obter a Entity
		List<Cashback> cashback = cashbackRepo.findByClienteIdAndIsActiveTrueOrderByDataVencimentoDesc(clienteId);
		if (cashback.isEmpty()) {
			throw new NotFoundException("Cashback não encontrado para o Cliente ID: " + clienteId);
		}
		return cashback;
	}

	private CashbackDTO toDto(Cashback c) { // mapeam. dtos
		CashbackDTO dto = new CashbackDTO();
		dto.setId(c.getId());
		dto.setClienteId(c.getCliente().getId());
		dto.setNomeCliente(c.getCliente().getNome());
		dto.setSaldo(c.getSaldo());
		return dto;
	}

	private CashbackResponseDTO toTransacaoDto(Cashback c) {
		return new CashbackResponseDTO(c.getSaldo(), c.getDataVencimento());
	}

	public Cashback ganharCashback(Cliente cliente, BigDecimal totalDoPedido) {
		return cashbackRepo.save(new Cashback(cliente, totalDoPedido.multiply(PORCENTAGEM_CASHBACK)));

	}

// ----- método para calcular vencimento LocalDate - desativar os cupons usados

	public void desativarCashbackUsado(Cliente cliente) {
		// buscar cahsbks ativos
		List<Cashback> listaCashback = cashbackRepo
				.findByClienteIdAndIsActiveTrueOrderByDataVencimentoDesc(cliente.getId());
		// buscar carteira
		BigDecimal saldo = cliente.getCarteira();
		// percorrer a lista
		for (Cashback c : listaCashback) {
			if (saldo.compareTo(BigDecimal.ZERO) > 0) {
				saldo = saldo.subtract(c.getSaldo());
			} else {
				c.setActive(false);
				cashbackRepo.save(c);
			}
		}

	}

// ----- método para calcular saldo a vencer - deduzir cupons ativos e calcular resto do último

	public BigDecimal calcularSaldoAVencer(Cliente cliente) {
		desativarCashbackUsado(cliente);
		List<Cashback> listaCashback = cashbackRepo
				.findByClienteIdAndIsActiveTrueOrderByDataVencimentoDesc(cliente.getId());

		BigDecimal saldo = cliente.getCarteira();

		BigDecimal saldoAVencer = BigDecimal.ZERO;

		for (Cashback c : listaCashback) {
			if (ChronoUnit.DAYS.between(c.getDataVencimento(), LocalDateTime.now()) < 1
					&& ChronoUnit.DAYS.between(c.getDataVencimento(), LocalDateTime.now()) > 0) {
				saldo = saldo.subtract(c.getSaldo());
				saldoAVencer = saldoAVencer.add(c.getSaldo());
			} else {
				saldo = saldo.subtract(c.getSaldo());
			}
		}
		saldoAVencer = saldoAVencer.add(saldo);

		return saldoAVencer;
	}

// ----- vencer cupom - deduzir saldo do cupom - desativar

	public BigDecimal expirarCashback(Cliente cliente) {
		desativarCashbackUsado(cliente);
		List<Cashback> listaCashback = cashbackRepo
				.findByClienteIdAndIsActiveTrueOrderByDataVencimentoDesc(cliente.getId());

		BigDecimal saldo = cliente.getCarteira();

		BigDecimal saldoVencido = BigDecimal.ZERO;

		for (Cashback c : listaCashback) {
			if (c.getDataVencimento().isBefore(LocalDateTime.now())) {
				saldo = saldo.subtract(c.getSaldo());
				saldoVencido = saldoVencido.add(c.getSaldo());
				c.setActive(false);
				cashbackRepo.save(c);
			} else {
				saldo = saldo.subtract(c.getSaldo());
			}
		}
		saldoVencido = saldoVencido.add(saldo);
		cliente.setCarteira(cliente.getCarteira().subtract(saldoVencido));
		clienteRepo.save(cliente);

		return saldoVencido;

	}
	
	public void creditar(Long clienteId, BigDecimal valor) {
	    Cliente cliente = clienteRepo.findById(clienteId)
	            .orElseThrow(() -> new NotFoundException("Cliente não encontrado"));
	    
	    Cashback cashback = new Cashback(cliente, valor); // cria cashback com o valor
	    cliente.aumentarCarteira(cashback);             // usa o método existente
	    cashbackRepo.save(cashback);                    // salva o cashback no banco
	    clienteRepo.save(cliente);                      // atualiza cliente
	}


// scheduled para avisar vencimento

//scheduled para vencimento automático

}
