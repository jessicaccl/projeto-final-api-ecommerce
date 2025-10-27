package org.serratec.trabalhoFinal.service;

import java.util.Collections;

import org.serratec.trabalhoFinal.domain.Cliente;
import org.serratec.trabalhoFinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalheImpl implements UserDetailsService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Cliente cliente = clienteRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

	    if (cliente.getUsuario() == null) {
	        throw new UsernameNotFoundException("Usuário não possui credenciais definidas");
	    }
	    
	    String role = cliente.getUsuario().getRole();
	    if (role == null) role = "ROLE_USER";

	    return User.builder()
	            .username(cliente.getUsuario().getUsername())
	            .password(cliente.getUsuario().getPassword())
	            .authorities(Collections.singletonList(
	                    new SimpleGrantedAuthority(cliente.getUsuario().getRole() != null ? cliente.getUsuario().getRole() : "ROLE_USER")
	                ))
	                .build();
	}

}