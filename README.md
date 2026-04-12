# E-Commerce API & Notification Microservice

Este projeto é um ecossistema de e-commerce robusto construído com **Spring Boot 3 (Java 21)**, utilizando uma arquitetura orientada a eventos com **RabbitMQ** e segurança via **JWT**.

O sistema é composto por:
1.  **Ecommerce Service:** API principal que gerencia clientes, produtos e autenticação.
2.  **Notification Service:** Microserviço independente que consome eventos do RabbitMQ e processa notificações (e-mails).

## 🚀 Tecnologias Utilizadas

*   **Linguagem:** Java 21
*   **Framework:** Spring Boot 3.4.2 (Parent: 4.0.2 custom)
*   **Banco de Dados:** PostgreSQL 16 (via Docker)
*   **Mensageria:** RabbitMQ (AMQP)
*   **Segurança:** Spring Security + JWT (JJWT)
*   **Mapeamento:** MapStruct
*   **Documentação:** Swagger / OpenAPI 3
*   **Containerização:** Docker & Docker Compose

## 🛠️ Como Iniciar o Ambiente

### 1. Requisitos
*   Docker e Docker Compose instalados.
*   Java 21+.

### 2. Subir a Infraestrutura (Banco e RabbitMQ)
Na raiz do projeto, execute:
```bash
sudo docker compose up -d
```
*   **PostgreSQL:** Rodando na porta `5433` (para evitar conflito com instâncias locais na 5432).
*   **RabbitMQ:** Rodando na porta `5672` (AMQP) e `15672` (Painel Web).

### 3. Executar as Aplicações
Você pode rodar as classes principais a partir do seu IDE:
*   `com.example.ecommerce.EcommerceApplication`
*   `com.example.notification.NotificationApplication` (dentro do módulo notification-service)

## 📖 Documentação da API (Swagger)

Com a aplicação rodando, acesse:
*   **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
*   **OpenAPI Docs:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 🔐 Endpoints de Autenticação

| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | `/auth/register` | Cadastra um novo cliente | Público |
| POST | `/auth/login` | Realiza login e gera Token JWT | Público |

## 📦 Gerenciamento de Clientes e Produtos

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| GET | `/customers` | Lista todos os clientes |
| POST | `/customers` | Cria um cliente (dispara e-mail de boas-vindas) |
| GET | `/products` | Lista todos os produtos |
| POST | `/products` | Cadastra um novo produto |
| POST | `/customers/{cId}/products/{pId}` | Adiciona produto ao cliente |

## 📬 Mensageria (RabbitMQ)

O sistema utiliza o padrão **Direct Exchange** para garantir que as mensagens cheguem à fila correta.

*   **Exchange:** `ecommerce.direct`
*   **Fila de Boas-vindas:** `customer.v1.welcome-email`
*   **Fila de Erro (DLQ):** `customer.v1.welcome-email.dlq`
*   **Painel de Gerenciamento:** [http://localhost:15672](http://localhost:15672) (Usuário: `guest` | Senha: `guest`)

## 🗄️ Administração do Banco de Dados

Para acessar o banco via ferramenta externa (DBeaver, IntelliJ, etc.):
*   **Host:** `localhost`
*   **Porta:** `5433`
*   **Banco:** `ecommerce`
*   **Usuário/Senha:** `postgres` / `postgres`

---
Desenvolvido para a disciplina de LAB Prog - 6º Período.
