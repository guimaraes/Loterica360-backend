# Loteria360 Backend

Backend API REST para sistema de casa lotérica desenvolvido com Java 17 e Spring Boot 3.

## Funcionalidades

- **Autenticação e Autorização**: JWT com roles (ADMIN, GERENTE, VENDEDOR, AUDITOR)
- **Cadastros**: Usuários, Jogos, Bolões, Formas de Pagamento, Caixas/Turnos
- **Vendas**: Sistema completo com multi-pagamento (PIX, dinheiro, cartão)
- **Caixa/Turno**: Abertura, sangria/suprimento, fechamento com resumo X/Z
- **Comissões**: Regras por jogo/bolão e por vendedor
- **Relatórios**: Vendas por período/vendedor/jogo/bolão, mix de pagamentos
- **Auditoria**: Trilha completa de alterações e logs estruturados
- **Documentação**: OpenAPI/Swagger UI completa

## Tecnologias

- **Java 17** + Spring Boot 3.3+
- **PostgreSQL 16+** + Flyway
- **JWT** para autenticação
- **MapStruct** para mapeamento DTO/Entity
- **Lombok** para redução de boilerplate
- **Testcontainers** para testes de integração
- **Docker** para ambiente de desenvolvimento
- **OpenAPI 3** para documentação
- **Micrometer** para observabilidade

## Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.9+
- Docker e Docker Compose

### Execução Rápida

```bash
# 1. Subir o banco de dados
make up

# 2. Executar a aplicação
make run

# 3. Acessar a documentação
# Swagger UI: http://localhost:8080/swagger-ui.html
# API Docs: http://localhost:8080/v3/api-docs
```

### Comandos Disponíveis

```bash
make help          # Ver todos os comandos disponíveis
make up            # Subir PostgreSQL e PgAdmin
make down          # Parar containers
make run           # Executar aplicação
make test          # Executar testes
make build         # Build da aplicação
make clean         # Limpar build
make format        # Formatar código
make coverage      # Gerar relatório de cobertura
```

## API Endpoints

### Autenticação
- `POST /auth/login` - Realizar login e obter JWT

### Usuários (ADMIN/GERENTE)
- `GET /usuarios` - Listar usuários
- `POST /usuarios` - Criar usuário
- `GET /usuarios/{id}` - Buscar usuário por ID

### Vendas (VENDEDOR/GERENTE)
- `POST /vendas` - Criar venda (jogo ou bolão)
- `POST /vendas/{id}/cancelar` - Cancelar venda (GERENTE)

### Turnos (VENDEDOR/GERENTE)
- `POST /turnos/abrir` - Abrir turno de caixa
- `POST /turnos/{id}/fechar` - Fechar turno e gerar resumo X/Z

## Exemplos de Uso

### 1. Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@loteria360.com",
    "senha": "admin123"
  }'
```

### 2. Criar Venda de Bolão com Multi-pagamento

```bash
curl -X POST http://localhost:8080/vendas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "bolaoId": "uuid-do-bolao",
    "cotas": 3,
    "pagamentos": [
      {
        "metodo": "PIX",
        "valor": 4.00,
        "referencia": "PIX123456"
      },
      {
        "metodo": "DINHEIRO",
        "valor": 2.00
      }
    ]
  }'
```

### 3. Criar Venda de Jogo

```bash
curl -X POST http://localhost:8080/vendas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <seu-token>" \
  -d '{
    "jogoId": "uuid-do-jogo",
    "quantidade": 5,
    "pagamentos": [
      {
        "metodo": "CARTAO_DEBITO",
        "valor": 12.50,
        "nsu": "123456789"
      }
    ]
  }'
```

## Estrutura do Projeto

```
loteria360-backend/
├── src/
│   ├── main/
│   │   ├── java/com/loteria360/
│   │   │   ├── LoteriaApplication.java
│   │   │   ├── config/
│   │   │   ├── security/
│   │   │   ├── domain/
│   │   │   │   ├── model/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   ├── repository/
│   │   │   │   └── service/
│   │   │   └── web/
│   │   │       └── controller/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   └── test/
├── docker-compose.yml
├── Makefile
├── pom.xml
└── README.md
```

## Licença

MIT License
