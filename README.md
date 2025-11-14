# projeto-final-api-ecommerce
API RESTful de E-commerce desenvolvida em Java e Spring Boot. Projeto final da disciplina de API, com módulos de Clientes, Pedidos, Planos e Assinaturas.

Construímos em equipe uma API REST completa para um sistema varejista, gerenciando as entidades centrais (Clientes, Produtos, Pedidos) e implementando funcionalidades avançadas como autenticação, programa de cashback, cupom de desconto na primeira compra e wishlist.

Como desafio individual, projetei e implementei o Módulo de Assinaturas do e-commerce. Um recurso crucial para gerar receita recorrente.
Meu foco foi criar uma solução robusta que fosse além do CRUD. Os destaques técnicos foram:

• Arquitetura Desacoplada: Criação das entidades Plano e Assinatura com relacionamento @ManyToOne, permitindo um catálogo de planos flexível.

• Gestão de Ciclo de Vida: Implementação de uma máquina de estados (ATIVA, PAUSADA, CANCELADA) com endpoints de ação como /pausar e /reativar.

• Tratamento de Erros Profissional: Usando @ControllerAdvice, implementei um handler global para retornar mensagens claras em erros 404 (Não Encontrado), 400 (Validação) e 400 (Regras de Negócio).

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
