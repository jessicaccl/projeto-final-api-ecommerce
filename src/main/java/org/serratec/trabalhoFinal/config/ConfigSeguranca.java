package org.serratec.trabalhoFinal.config;

import java.util.Arrays;

import org.serratec.trabalhoFinal.security.JwtAuthenticationFilter;
import org.serratec.trabalhoFinal.security.JwtAuthorizationFilter;
import org.serratec.trabalhoFinal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class ConfigSeguranca {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtUtil jwtUtil;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfigurationSource()))
	            .authorizeHttpRequests(auth -> auth
	                    // ----------------------------------------------------------------------------------
	                    // RECURSOS PÚBLICOS
	                    // ----------------------------------------------------------------------------------
	                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
	                    .requestMatchers(HttpMethod.POST, "/login").permitAll()
	                    .requestMatchers(HttpMethod.POST, "/clientes").permitAll()
	                    .requestMatchers(HttpMethod.GET, "/produtos/**").permitAll()
	                    .requestMatchers(HttpMethod.GET, "/categorias/**").permitAll()
	                    .requestMatchers(HttpMethod.GET, "/enderecos/**").permitAll()
	                    .requestMatchers(HttpMethod.GET, "/planos/**").permitAll()

	                    // ----------------------------------------------------------------------------------
	                    // ACESSO DE ADMINISTRADOR (ROLE_ADMIN)
	                    // ----------------------------------------------------------------------------------
	                    .requestMatchers("/funcionarios/**").hasRole("ADMIN")
	                    .requestMatchers("/cashbacks/adicionar/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/clientes").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/pedidos").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.POST, "/produtos").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.PUT, "/produtos/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.DELETE, "/produtos/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.POST, "/categorias").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.PUT, "/categorias/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.DELETE, "/categorias/**").hasRole("ADMIN")
	                    .requestMatchers("/estoque/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.POST, "/planos").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.PUT, "/planos/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.DELETE, "/planos/**").hasRole("ADMIN")

	                    // ----------------------------------------------------------------------------------
	                    // ACESSO DE USUÁRIO (ROLE_USER ou ROLE_ADMIN)
	                    // ----------------------------------------------------------------------------------
	                    // Cliente, pedidos e cashback por ID
	                    .requestMatchers(HttpMethod.GET, "/clientes/{id}").hasAnyRole("ADMIN", "USER")
	                    .requestMatchers(HttpMethod.PUT, "/clientes/{id}").hasAnyRole("ADMIN", "USER")
	                    .requestMatchers(HttpMethod.DELETE, "/clientes/{id}").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/pedidos/meus").hasRole("USER")
	                    .requestMatchers(HttpMethod.GET, "/pedidos/{id}").hasAnyRole("ADMIN", "USER")
	                    .requestMatchers(HttpMethod.DELETE, "/pedidos/{id}").hasRole("ADMIN")
	                    .requestMatchers("/cashbacks/cliente/{clienteId}").hasRole("USER") 

	                    // Pedidos e Carrinho de Compras (usando * para o ID)
	                    .requestMatchers("/pedidos/cart/*").hasRole("USER")      // POST /pedidos/cart/{clienteId}
	                    .requestMatchers("/pedidos/pagar/*").hasRole("USER")     // PUT /pedidos/pagar/{pedidoId}

	                    // Wishlist e Recomendações (CORRIGIDO: usando * para o {clienteId})
	                    .requestMatchers("/clientes/*/wishlist/**").hasRole("USER") 
	                    
	                    // Assinaturas e Recomendações
	                    .requestMatchers("/assinaturas/**").hasRole("USER")
	                    .requestMatchers("/recomendacoes/**").hasRole("USER")

	                    // ----------------------------------------------------------------------------------
	                    // OUTRAS REQUISIÇÕES
	                    // ----------------------------------------------------------------------------------
	                    .anyRequest().authenticated() 

	            ).sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil);
		jwtAuthenticationFilter.setFilterProcessesUrl("/login");

		JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(
				authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil,
				userDetailsService);

		http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
		http.addFilter(jwtAuthenticationFilter);

		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Authorization"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}

	@Bean
	public BCryptPasswordEncoder bcryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
