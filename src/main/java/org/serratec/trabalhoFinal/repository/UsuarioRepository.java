package org.serratec.trabalhoFinal.repository;

import java.util.Optional;

import org.serratec.trabalhoFinal.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsername(String username);
}
