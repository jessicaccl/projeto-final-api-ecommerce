package org.serratec.trabalhoFinal.utils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.serratec.trabalhoFinal.service.CashbackService;
import org.serratec.trabalhoFinal.service.EmailService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
public class TarefasAgendadas {

	private final ClienteRepository clienteRepo;
	private final CashbackService cashbackService;
	private final EmailService emailService;

	public TarefasAgendadas(ClienteRepository clienteRepo, CashbackService cashbackService, EmailService emailService) {
		this.clienteRepo = clienteRepo;
		this.cashbackService = cashbackService;
		this.emailService = emailService;
	}

	@Scheduled(cron = "0 0 0 * * ?", zone = "America/Sao_Paulo")
	public void executarTarefasRecorrentes() {

		List<Cliente> allClientes = clienteRepo.findAll();
				
		for (Cliente c : allClientes) {
			
			// Notificação antes do vencimento
			BigDecimal saldoAVencer = cashbackService.calcularSaldoAVencer(c);
			if (saldoAVencer.compareTo(BigDecimal.ZERO) > 0) {
				emailService.cashbackAVencer(c, saldoAVencer);
			}

			// Expiração
			BigDecimal cashbackVencido = cashbackService.expirarCashback(c);
			if (cashbackVencido.compareTo(BigDecimal.ZERO) > 0) {
				emailService.notificarCashbackVencido(c, cashbackVencido);
			}
		}

	}
	

}
