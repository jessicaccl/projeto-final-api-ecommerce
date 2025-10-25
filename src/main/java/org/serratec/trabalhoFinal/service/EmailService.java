package org.serratec.trabalhoFinal.service;

import java.math.BigDecimal;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.domain.Pedido;
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
	
	public void enviarNotificacaoCashback(Pedido pedido, BigDecimal valorCreditado, BigDecimal novoSaldo) {
        Cliente cliente = pedido.getCliente();
        
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(cliente.getEmail());
            msg.setSubject("Cashback Creditado com Sucesso! Pedido #" + pedido.getId());
            
            String corpoEmail = String.format(
                "Olá %s,\n\n" +
                "Parabéns! Seu pedido #%d foi confirmado como PAGO.\n" +
                "Você acaba de ganhar R$ %.2f de Cashback!\n\n" +
                "Seu novo saldo total de Cashback é de R$ %.2f.\n\n" +
                "Use seu saldo na próxima compra!\n\n" +
                "Atenciosamente,\n" +
                "Serratec E-Commerce de Cafés",
                cliente.getNome(), 
                pedido.getId(), 
                valorCreditado, 
                novoSaldo
            );
            
            msg.setText(corpoEmail);
            mailSender.send(msg);
            System.out.println("E-mail de Cashback enviado para " + cliente.getEmail());
        } catch (Exception e) {
            System.err.println("Falha ao enviar e-mail de Cashback: " + e.getMessage());
        }
    }
}
	



