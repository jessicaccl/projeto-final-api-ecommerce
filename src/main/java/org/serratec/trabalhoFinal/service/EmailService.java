package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void enviarNotificacaoCliente(Cliente cliente, String acao) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(cliente.getEmail());
			msg.setSubject("Cadastro " + acao);
			msg.setText("Olá " + cliente.getNome() + ", seu cadastro foi " + acao + ".");
			mailSender.send(msg);
			System.out.println("E-mail enviado para " + cliente.getEmail());
		} catch (Exception e) {
			System.err.println("Falha ao enviar e-mail: " + e.getMessage());
		}
	}
}
