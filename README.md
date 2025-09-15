# Loteria360 Backend

Sistema de gestão para casas lotéricas desenvolvido com Spring Boot 3.x, Java 17 e MySQL 8.

## 🚀 Tecnologias

- **Java 17**
- **Spring Boot 3.3.0**
- **Spring Security com JWT**
- **MySQL 8**
- **Flyway** para migrações
- **MapStruct** para mapeamento DTO ↔ Entity
- **Lombok** para redução de boilerplate
- **OpenAPI/Swagger** para documentação
- **Testcontainers** para testes de integração
- **Docker Compose** para orquestração

## 📋 Pré-requisitos

- Java 17+
- Docker e Docker Compose
- Maven 3.8+

## 🏃‍♂️ Execução Rápida

### 1. Subir os serviços de banco de dados

```bash
docker compose up -d
```

### 2. Executar a aplicação

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

### 3. Acessar a aplicação

- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Adminer**: http://localhost:8081

## 🔐 Login Inicial

O sistema vem com um usuário administrador pré-cadastrado:

- **Email**: `admin@loteria360.local`
- **Senha**: `admin`

## 📚 Funcionalidades

### Autenticação e Autorização
- Login com JWT
- RBAC (Role-Based Access Control) com papéis: ADMIN, GERENTE, VENDEDOR, AUDITOR

### Gestão de Usuários
- Cadastro de usuários com diferentes papéis
- Ativação/desativação de usuários
- Listagem com paginação

### Gestão de Jogos
- Cadastro de jogos (Mega-Sena, Lotofácil, etc.)
- Ativação/desativação de jogos
- Configuração de preços e regras

### Gestão de Bolões
- Criação de bolões para jogos
- Controle de cotas disponíveis/vendidas
- Encerramento e cancelamento de bolões

### Sistema de Vendas
- Venda de jogos individuais
- Venda de cotas de bolão
- Múltiplas formas de pagamento (Dinheiro, PIX, Cartão)
- Emissão de recibos

### Gestão de Turnos
- Abertura e fechamento de turnos
- Controle de caixa
- Sangria e suprimento
- Consolidação por método de pagamento

### Relatórios
- Vendas por período
- Status de bolões e cotas
- Pagamentos por método
- Comissões calculadas

### Auditoria
- Trilha de alterações em tabelas críticas
- Logs estruturados em JSON
- Correlação de requisições com traceId

## 🏗️ Arquitetura

```
com.loteria360
├── Loteria360Application.java
├── config/          # Configurações (Security, OpenAPI, CORS)
├── security/        # JWT, Autenticação e Autorização
├── domain/
│   ├── model/       # Entidades JPA
│   └── dto/         # DTOs de request/response
├── mapper/          # Mappers MapStruct
├── repository/      # Repositories JPA
├── service/         # Regras de negócio
├── controller/      # Controllers REST
├── audit/           # Sistema de auditoria
└── util/            # Utilitários
```

## 🗄️ Banco de Dados

### Principais Tabelas

- **usuario**: Usuários do sistema com papéis
- **jogo**: Jogos disponíveis (Mega-Sena, Lotofácil, etc.)
- **bolao**: Bolões de jogos com cotas
- **venda**: Vendas realizadas
- **pagamento**: Pagamentos das vendas
- **turno**: Turnos de trabalho
- **movimento_caixa**: Sangrias e suprimentos
- **auditoria**: Trilha de auditoria

### Migrações

As migrações estão em `src/main/resources/db/migration/`:
- `V1__baseline.sql`: Criação das tabelas
- `V2__seed_data.sql`: Dados iniciais

## 🧪 Testes

### Executar todos os testes

```bash
./mvnw test
```

### Executar testes de integração

```bash
./mvnw test -Dtest="*IntegrationTest"
```

### Executar testes unitários

```bash
./mvnw test -Dtest="*Test" -Dtest="!*IntegrationTest"
```

## 🔧 Configuração

### Profiles Disponíveis

- **dev**: Desenvolvimento (logs detalhados, Swagger habilitado)
- **test**: Testes (Testcontainers, logs mínimos)
- **prod**: Produção (logs estruturados)

### Variáveis de Ambiente

```bash
# JWT
JWT_SECRET=sua-chave-secreta-jwt
JWT_EXPIRATION=86400000

# Database
DB_HOST=localhost
DB_PORT=3306
DB_NAME=loteria360
DB_USER=loteria
DB_PASSWORD=loteria
```

## 📡 API Endpoints

### Autenticação
- `POST /api/v1/auth/login` - Login
- `POST /api/v1/auth/logout` - Logout
- `GET /api/v1/auth/me` - Obter usuário atual

### Usuários
- `GET /api/v1/usuarios` - Listar usuários (ADMIN/GERENTE)
- `POST /api/v1/usuarios` - Criar usuário (ADMIN)
- `GET /api/v1/usuarios/{id}` - Buscar usuário
- `PATCH /api/v1/usuarios/{id}/toggle-status` - Ativar/Desativar
- `PATCH /api/v1/usuarios/{id}/status` - Ativar/Desativar (endpoint alternativo)

### Jogos
- `GET /api/v1/jogos` - Listar jogos
- `POST /api/v1/jogos` - Criar jogo (ADMIN/GERENTE)
- `GET /api/v1/jogos/{id}` - Buscar jogo
- `PATCH /api/v1/jogos/{id}/toggle-status` - Ativar/Desativar

### Bolões
- `GET /api/v1/boloes` - Listar bolões
- `POST /api/v1/boloes` - Criar bolão (ADMIN/GERENTE)
- `GET /api/v1/boloes/{id}` - Buscar bolão
- `PATCH /api/v1/boloes/{id}/encerrar` - Encerrar bolão
- `PATCH /api/v1/boloes/{id}/cancelar` - Cancelar bolão

### Vendas
- `POST /api/v1/vendas/jogo` - Vender jogo
- `POST /api/v1/vendas/bolao` - Vender cotas de bolão
- `POST /api/v1/vendas/{id}/cancelar` - Cancelar venda (GERENTE)
- `GET /api/v1/vendas` - Listar vendas
- `GET /api/v1/vendas/{id}` - Buscar venda

### Turnos
- `POST /api/v1/turnos/abrir` - Abrir turno
- `POST /api/v1/turnos/{id}/fechar` - Fechar turno
- `GET /api/v1/turnos` - Listar turnos
- `GET /api/v1/turnos/{id}` - Buscar turno

### Movimentos de Caixa
- `POST /api/v1/movimentos` - Registrar sangria/suprimento
- `GET /api/v1/movimentos` - Listar movimentos
- `GET /api/v1/movimentos/{id}` - Buscar movimento

### Relatórios
- `GET /api/v1/relatorios/vendas` - Relatório de vendas
- `GET /api/v1/relatorios/boloes/status` - Status dos bolões
- `GET /api/v1/relatorios/pagamentos` - Relatório de pagamentos

## 🔒 Segurança

### Papéis de Usuário

- **ADMIN**: Acesso total ao sistema
- **GERENTE**: Gestão de jogos, bolões, vendas e cancelamentos
- **VENDEDOR**: Realização de vendas e gestão de turnos
- **AUDITOR**: Apenas visualização de relatórios

### JWT

- Token expira em 24 horas
- Header: `Authorization: Bearer <token>`
- Claims incluem email e papéis do usuário

## 📊 Monitoramento

### Logs Estruturados

Os logs são gerados em formato JSON com:
- `traceId`: Correlação de requisições
- `spanId`: Rastreamento de operações
- `application`: Nome da aplicação
- `environment`: Ambiente (dev/prod)

### Auditoria

Todas as operações críticas são auditadas:
- Criação, alteração e exclusão de registros
- Informações de antes/depois em JSON
- Usuário responsável pela operação
- Timestamp da operação

## 🚀 Deploy

### Docker

```bash
# Build da imagem
docker build -t loteria360-backend .

# Executar container
docker run -p 8080:8080 \
  -e JWT_SECRET=sua-chave \
  -e DB_HOST=seu-banco \
  loteria360-backend
```

### Produção

1. Configure as variáveis de ambiente
2. Use um banco MySQL externo
3. Configure logs para um sistema centralizado
4. Configure monitoramento e alertas

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 Suporte

Para suporte, entre em contato:
- Email: contato@loteria360.com
- Documentação: http://localhost:8080/swagger-ui.html