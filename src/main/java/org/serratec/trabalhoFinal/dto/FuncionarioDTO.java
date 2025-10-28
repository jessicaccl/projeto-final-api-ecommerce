package org.serratec.trabalhoFinal.dto;

// Assumindo que esta é a sua DTO de resposta
public class FuncionarioDTO {

    private Long id;
    private String nome;
    private String email;
    private String role; // Campo que armazena a Role do usuário

    // O construtor que o FuncionarioController está tentando chamar:
    public FuncionarioDTO(Long id, String nome, String email, String role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

    // Se você estiver usando o Jackson para serializar, é bom ter o construtor padrão
    public FuncionarioDTO() {} 

    // Getters e Setters (Se estiver usando Lombok, a anotação @Data resolve isso)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}