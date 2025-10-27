package org.serratec.trabalhoFinal.service;

import org.serratec.trabalhoFinal.domain.Funcionario;
import org.serratec.trabalhoFinal.domain.Usuario;
import org.serratec.trabalhoFinal.repository.FuncionarioRepository;
import org.serratec.trabalhoFinal.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private FuncionarioRepository funcionarioRepository;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public FuncionarioService(FuncionarioRepository funcionarioRepository, UsuarioRepository usuarioRepository,
			PasswordEncoder passwordEncoder, EmailService emailService) {
		super();
		this.funcionarioRepository = funcionarioRepository;
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	public Funcionario cadastrarFuncionario(Funcionario funcionario) {
        Usuario usuario = new Usuario();
        usuario.setUsername(funcionario.getEmail());
        usuario.setPassword(passwordEncoder.encode(funcionario.getSenha()));
        usuario.setRole("ROLE_ADMIN");

        usuarioRepository.save(usuario);
        funcionario.setUsuario(usuario);
        
        Funcionario saved = funcionarioRepository.save(funcionario);
        emailService.enviarNotificacaoFuncionario(saved, "Criado");

        return funcionarioRepository.save(funcionario);
    }
}