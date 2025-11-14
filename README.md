# projeto-final-api-ecommerce
API RESTful de E-commerce desenvolvida em Java e Spring Boot. Projeto final da disciplina de API, com módulos de Clientes, Pedidos, Planos e Assinaturas.

🚀 Tecnologias
Linguagem: Java 17
Framework: Spring Boot 3
Banco de Dados: Spring Data JPA (Hibernate) & PostgreSQL
Documentação: Swagger (OpenAPI)
Conceitos: Arquitetura em Camadas (MVC), Padrão DTO, Injeção de Dependência.

✨ Funcionalidades
Módulos de Grupo:
Gestão de Clientes (com integração ViaCEP)
Gestão de Produtos & Categorias
Sistema de Pedidos (com relacionamento N:N)
Autenticação, Cashback, Wishlist, Envio de E-mail.
Módulo Individual:
Sistema de Planos (CRUD)
Sistema de Assinaturas (CRUD e Ciclo de Vida)
Tratamento de Erros (@ControllerAdvice):
NotFoundException (404)
MethodArgumentNotValidException (400)
IllegalArgumentException para Regras de Negócio (400)
