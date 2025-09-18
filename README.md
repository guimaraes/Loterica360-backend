# 🎰 Loteria360 Backend

Sistema de gestão completo para casas lotéricas, desenvolvido em Spring Boot com arquitetura moderna e robusta.

## 📋 **Índice**

- [Visão Geral](#-visão-geral)
- [Tecnologias](#-tecnologias)
- [Arquitetura](#-arquitetura)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Domínio e Entidades](#-domínio-e-entidades)
- [API e Endpoints](#-api-e-endpoints)
- [Segurança e Autenticação](#-segurança-e-autenticação)
- [Configuração e Deploy](#-configuração-e-deploy)
- [Desenvolvimento](#-desenvolvimento)
- [Documentação da API](#-documentação-da-api)

## 🎯 **Visão Geral**

O **Loteria360 Backend** é uma API RESTful robusta desenvolvida para gerenciar todas as operações de uma casa lotérica, incluindo:

- **Gestão de Usuários**: Sistema de perfis (Admin, Gerente, Vendedor, Auditor)
- **Gestão de Jogos**: Cadastro e controle de jogos de loteria
- **Gestão de Bolões**: Criação e gerenciamento de bolões
- **Gestão de Vendas**: Controle de vendas por caixa e jogo
- **Gestão de Caixas**: Controle de caixas e contagem de dinheiro
- **Gestão de Clientes**: Cadastro e controle de clientes
- **Relatórios**: Dashboard e relatórios gerenciais
- **Auditoria**: Rastreamento completo de operações

## 🛠️ **Tecnologias**

### **Backend Stack**
- **Java 17** - Linguagem principal
- **Spring Boot 3.3.0** - Framework principal
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **Spring AOP** - Programação orientada a aspectos
- **MySQL 8** - Banco de dados
- **Flyway** - Migração de banco de dados
- **JWT** - Autenticação stateless
- **MapStruct** - Mapeamento de objetos
- **Lombok** - Redução de boilerplate
- **OpenAPI/Swagger** - Documentação da API

### **Ferramentas de Desenvolvimento**
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **Testcontainers** - Testes de integração
- **Logback** - Sistema de logs
- **Jackson** - Serialização JSON

## 🏗️ **Arquitetura**

### **Padrão Arquitetural**
O projeto segue o padrão **Clean Architecture** com separação clara de responsabilidades:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                      │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Controllers   │  │   DTOs/Request  │  │   Responses  │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                     Business Layer                         │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │    Services     │  │   Mappers       │  │  Validation  │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                   Persistence Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Repositories  │  │   Entities      │  │   Database   │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### **Camadas da Aplicação**

1. **Controller Layer** - Endpoints REST e validação de entrada
2. **Service Layer** - Lógica de negócio e orquestração
3. **Repository Layer** - Acesso a dados e queries
4. **Entity Layer** - Modelo de domínio e persistência
5. **Security Layer** - Autenticação e autorização
6. **Configuration Layer** - Configurações da aplicação

## 📁 **Estrutura do Projeto**

```
src/main/java/com/loteria360/
├── Loteria360Application.java          # Classe principal
├── audit/                              # Sistema de auditoria
│   ├── Auditable.java                  # Interface para auditoria
│   ├── AuditAspect.java                # Aspect para auditoria
│   └── AuditService.java               # Serviço de auditoria
├── config/                             # Configurações
│   ├── GlobalExceptionHandler.java     # Tratamento global de erros
│   ├── JacksonConfig.java              # Configuração JSON
│   ├── OpenApiConfig.java              # Configuração Swagger
│   ├── SecurityConfig.java             # Configuração de segurança
│   └── TraceIdFilter.java              # Filtro de trace ID
├── controller/                         # Controllers REST
│   ├── AuthController.java             # Autenticação
│   ├── BolaoController.java            # Gestão de bolões
│   ├── CaixaController.java            # Gestão de caixas
│   ├── ClienteController.java          # Gestão de clientes
│   ├── ContagemCaixaController.java    # Contagem de caixas
│   ├── DashboardController.java        # Dashboard e relatórios
│   ├── JogoController.java             # Gestão de jogos
│   ├── RelatorioController.java        # Relatórios
│   ├── UsuarioController.java          # Gestão de usuários
│   └── VendaCaixaController.java       # Gestão de vendas
├── domain/                             # Camada de domínio
│   ├── dto/                            # Data Transfer Objects
│   ├── mapper/                         # Mappers MapStruct
│   └── model/                          # Entidades JPA
├── exception/                          # Exceptions customizadas
│   ├── BusinessException.java          # Exception de negócio
│   ├── ResourceNotFoundException.java  # Recurso não encontrado
│   └── ValidationException.java        # Exception de validação
├── repository/                         # Repositories JPA
├── security/                           # Segurança
│   ├── CurrentUser.java                # Anotação para usuário atual
│   ├── CurrentUserArgumentResolver.java # Resolver de usuário atual
│   ├── JwtAuthFilter.java              # Filtro JWT
│   ├── JwtService.java                 # Serviço JWT
│   └── UsuarioDetailsService.java      # UserDetailsService
├── service/                            # Serviços de negócio
└── util/                               # Utilitários
    └── PasswordGeneratorUtil.java      # Gerador de senhas
```

## 🗄️ **Domínio e Entidades**

### **Entidades Principais**

#### **👤 Usuario**
```java
@Entity
@Table(name = "usuario")
public class Usuario {
    private String id;                    // UUID
    private String nome;                  // Nome completo
    private String email;                 // Email único
    private String senhaHash;             // Hash da senha
    private PapelUsuario papel;           // ADMIN, GERENTE, VENDEDOR, AUDITOR
    private Boolean ativo;                // Status ativo/inativo
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **🎮 Jogo**
```java
@Entity
@Table(name = "jogo")
public class Jogo {
    private String id;                    // UUID
    private String nome;                  // Nome do jogo
    private String descricao;             // Descrição
    private BigDecimal preco;             // Preço por aposta
    private Boolean ativo;                // Status ativo/inativo
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **👥 Cliente**
```java
@Entity
@Table(name = "cliente")
public class Cliente {
    private String id;                    // UUID
    private String nome;                  // Nome completo
    private String cpf;                   // CPF único
    private String telefone;              // Telefone
    private String email;                 // Email
    private Boolean consentimentoLgpd;    // Consentimento LGPD
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **🎯 Bolao**
```java
@Entity
@Table(name = "bolao")
public class Bolao {
    private String id;                    // UUID
    private Jogo jogo;                    // Jogo relacionado
    private String concurso;              // Número do concurso
    private String descricao;             // Descrição
    private Integer cotasTotais;          // Total de cotas
    private Integer cotasVendidas;        // Cotas vendidas
    private Integer cotasDisponiveis;     // Cotas disponíveis
    private BigDecimal valorCota;         // Valor por cota
    private LocalDate dataSorteio;        // Data do sorteio
    private StatusBolao status;           // ABERTO, ENCERRADO, CANCELADO
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **💰 Caixa**
```java
@Entity
@Table(name = "caixa")
public class Caixa {
    private String id;                    // UUID
    private Integer numero;               // Número único
    private String descricao;             // Descrição
    private Boolean ativo;                // Status ativo/inativo
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **📊 VendaCaixa**
```java
@Entity
@Table(name = "venda_caixa")
public class VendaCaixa {
    private String id;                    // UUID
    private Caixa caixa;                  // Caixa relacionado
    private Jogo jogo;                    // Jogo relacionado
    private Integer quantidade;           // Quantidade vendida
    private BigDecimal valorTotal;        // Valor total
    private LocalDate dataVenda;          // Data da venda
    private Usuario usuario;              // Usuário que vendeu
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **💵 ContagemCaixa**
```java
@Entity
@Table(name = "contagem_caixa")
public class ContagemCaixa {
    private String id;                    // UUID
    private Caixa caixa;                  // Caixa relacionado
    private LocalDate dataContagem;       // Data da contagem
    private Usuario usuario;              // Usuário responsável
    
    // Notas
    private Integer notas200, notas100, notas50, notas20, notas10, notas5, notas2;
    
    // Moedas
    private Integer moedas1, moedas050, moedas025, moedas010, moedas005;
    
    // Totais calculados
    private BigDecimal totalNotas;        // Total em notas
    private BigDecimal totalMoedas;       // Total em moedas
    private BigDecimal totalGeral;        // Total geral
    private LocalDateTime criadoEm;       // Data de criação
}
```

#### **📝 Auditoria**
```java
@Entity
@Table(name = "auditoria")
public class Auditoria {
    private String id;                    // UUID
    private String tabela;                // Nome da tabela
    private String registroId;            // ID do registro
    private AcaoAuditoria acao;           // INSERT, UPDATE, DELETE
    private String antesJson;             // Dados anteriores (JSON)
    private String depoisJson;            // Dados novos (JSON)
    private Usuario usuario;              // Usuário responsável
    private LocalDateTime criadoEm;       // Data da operação
}
```

### **Enums e Tipos**

#### **PapelUsuario**
- `ADMIN` - Administrador completo
- `GERENTE` - Gerente operacional
- `VENDEDOR` - Vendedor
- `AUDITOR` - Auditor/Visualizador

#### **StatusBolao**
- `ABERTO` - Bolão aberto para vendas
- `ENCERRADO` - Bolão encerrado
- `CANCELADO` - Bolão cancelado

#### **AcaoAuditoria**
- `INSERT` - Inserção de registro
- `UPDATE` - Atualização de registro
- `DELETE` - Exclusão de registro

## 🔌 **API e Endpoints**

### **Autenticação**
```
POST /api/v1/auth/login              # Login e obtenção de token
GET  /api/v1/auth/me                 # Dados do usuário atual
POST /api/v1/auth/logout             # Logout
POST /api/v1/auth/refresh            # Renovar token
```

### **Usuários**
```
POST   /api/v1/usuarios              # Criar usuário (ADMIN)
GET    /api/v1/usuarios              # Listar usuários (ADMIN/GERENTE)
GET    /api/v1/usuarios/{id}         # Buscar usuário (ADMIN/GERENTE)
PUT    /api/v1/usuarios/{id}         # Atualizar usuário (ADMIN)
DELETE /api/v1/usuarios/{id}         # Excluir usuário (ADMIN)
POST   /api/v1/usuarios/{id}/senha   # Alterar senha (ADMIN)
```

### **Jogos**
```
POST   /api/v1/jogos                 # Criar jogo (ADMIN/GERENTE)
GET    /api/v1/jogos                 # Listar jogos (todos)
GET    /api/v1/jogos/ativos          # Listar jogos ativos (todos)
GET    /api/v1/jogos/{id}            # Buscar jogo (todos)
PUT    /api/v1/jogos/{id}            # Atualizar jogo (ADMIN/GERENTE)
DELETE /api/v1/jogos/{id}            # Excluir jogo (ADMIN/GERENTE)
PUT    /api/v1/jogos/{id}/toggle     # Ativar/desativar jogo (ADMIN/GERENTE)
```

### **Bolões**
```
POST   /api/v1/boloes                # Criar bolão (ADMIN/GERENTE)
GET    /api/v1/boloes                # Listar bolões (todos)
GET    /api/v1/boloes/ativos         # Listar bolões ativos (todos)
GET    /api/v1/boloes/{id}           # Buscar bolão (todos)
PUT    /api/v1/boloes/{id}           # Atualizar bolão (ADMIN/GERENTE)
DELETE /api/v1/boloes/{id}           # Excluir bolão (ADMIN/GERENTE)
PUT    /api/v1/boloes/{id}/status    # Alterar status (ADMIN/GERENTE)
```

### **Caixas**
```
POST   /api/v1/caixas                # Criar caixa (ADMIN/GERENTE)
GET    /api/v1/caixas                # Listar caixas (ADMIN/GERENTE)
GET    /api/v1/caixas/ativas         # Listar caixas ativas (todos)
GET    /api/v1/caixas/{id}           # Buscar caixa (ADMIN/GERENTE)
PUT    /api/v1/caixas/{id}           # Atualizar caixa (ADMIN/GERENTE)
PUT    /api/v1/caixas/{id}/toggle    # Ativar/desativar caixa (todos)
```

### **Vendas**
```
POST   /api/v1/vendas-caixa          # Registrar venda (ADMIN/GERENTE/VENDEDOR)
GET    /api/v1/vendas-caixa          # Listar vendas (ADMIN/GERENTE)
GET    /api/v1/vendas-caixa/{id}     # Buscar venda (ADMIN/GERENTE)
PUT    /api/v1/vendas-caixa/{id}     # Atualizar venda (ADMIN/GERENTE)
DELETE /api/v1/vendas-caixa/{id}     # Excluir venda (ADMIN/GERENTE)
```

### **Contagem de Caixa**
```
POST   /api/v1/contagem-caixa        # Registrar contagem (ADMIN/GERENTE/VENDEDOR)
GET    /api/v1/contagem-caixa        # Listar contagens (ADMIN/GERENTE)
GET    /api/v1/contagem-caixa/{id}   # Buscar contagem (ADMIN/GERENTE)
PUT    /api/v1/contagem-caixa/{id}   # Atualizar contagem (ADMIN/GERENTE)
DELETE /api/v1/contagem-caixa/{id}   # Excluir contagem (ADMIN/GERENTE)
```

### **Clientes**
```
POST   /api/v1/clientes              # Criar cliente (ADMIN/GERENTE/VENDEDOR)
GET    /api/v1/clientes              # Listar clientes (ADMIN/GERENTE/VENDEDOR/AUDITOR)
GET    /api/v1/clientes/{id}         # Buscar cliente (ADMIN/GERENTE/VENDEDOR/AUDITOR)
PUT    /api/v1/clientes/{id}         # Atualizar cliente (ADMIN/GERENTE/VENDEDOR)
DELETE /api/v1/clientes/{id}         # Excluir cliente (ADMIN/GERENTE)
```

### **Dashboard e Relatórios**
```
GET    /api/v1/dashboard             # Dashboard principal (ADMIN/GERENTE/AUDITOR)
GET    /api/v1/dashboard/metricas    # Métricas gerais (ADMIN/GERENTE/AUDITOR)
GET    /api/v1/dashboard/vendas      # Vendas por período (ADMIN/GERENTE/AUDITOR)
GET    /api/v1/dashboard/caixas      # Status dos caixas (ADMIN/GERENTE/AUDITOR)
GET    /api/v1/relatorios/vendas     # Relatório de vendas (ADMIN/GERENTE/AUDITOR)
GET    /api/v1/relatorios/financeiro # Relatório financeiro (ADMIN/GERENTE/AUDITOR)
```

## 🔐 **Segurança e Autenticação**

### **Autenticação JWT**
- **Stateless**: Não mantém sessão no servidor
- **Token Expiration**: 24 horas por padrão
- **Refresh Token**: Renovação automática
- **Bearer Token**: Autenticação via header Authorization

### **Autorização por Perfis**

#### **🔴 ADMIN**
- Acesso completo a todos os recursos
- Gestão de usuários
- Configurações do sistema
- Todos os relatórios

#### **🟡 GERENTE**
- Gestão de jogos, bolões e caixas
- Gestão de vendas e contagens
- Relatórios operacionais
- **Não pode**: Gerenciar usuários

#### **🟢 VENDEDOR**
- Apenas tela de vendas
- Acesso a clientes (para vendas)
- Visualização de jogos ativos
- Contagem de caixa
- **Não pode**: Criar/editar jogos, bolões, usuários

#### **🔵 AUDITOR**
- Apenas visualização
- Relatórios e dashboard
- Consulta de dados
- **Não pode**: Modificar dados

### **Tratamento de Erros**
Sistema robusto de tratamento de erros com mensagens amigáveis:

```json
{
  "type": "https://loteria360.com/errors/validation-failed",
  "title": "Dados Inválidos",
  "status": 400,
  "detail": "Por favor, verifique os dados informados",
  "errors": [
    {
      "field": "nome",
      "message": "Nome é obrigatório",
      "rejectedValue": null
    }
  ],
  "timestamp": "2024-01-15T10:30:15"
}
```

## ⚙️ **Configuração e Deploy**

### **Variáveis de Ambiente**
```bash
# Banco de Dados
DB_URL=jdbc:mysql://localhost:3306/loteria360
DB_USERNAME=loteria
DB_PASSWORD=loteria

# JWT
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:3000
```

### **Docker Compose**
```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: loteria360
      MYSQL_USER: loteria
      MYSQL_PASSWORD: loteria
    ports:
      - "3306:3306"
    volumes:
      - mysql_data:/var/lib/mysql

  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - DB_URL=jdbc:mysql://mysql:3306/loteria360
    depends_on:
      - mysql

volumes:
  mysql_data:
```

## 🚀 **Desenvolvimento**

### **Pré-requisitos**
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Docker (opcional)

### **Configuração Local**
```bash
# 1. Clone o repositório
git clone <repository-url>
cd loteria360-backend

# 2. Configure o banco de dados
# Crie o banco 'loteria360' no MySQL
# Configure as credenciais em application-dev.yml

# 3. Execute as migrações
mvn flyway:migrate

# 4. Execute a aplicação
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### **Comandos Úteis**
```bash
# Executar testes
mvn test

# Executar testes de integração
mvn verify

# Gerar documentação
mvn javadoc:javadoc

# Limpar e compilar
mvn clean compile

# Executar com perfil específico
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

## 📚 **Documentação da API**

### **Swagger UI**
Acesse a documentação interativa da API em:
- **Local**: http://localhost:8080/swagger-ui.html
- **Produção**: https://api.loteria360.com/swagger-ui.html

### **OpenAPI Specification**
- **JSON**: http://localhost:8080/v3/api-docs
- **YAML**: http://localhost:8080/v3/api-docs.yaml

### **Exemplos de Uso**

#### **Login**
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@loteria360.local",
    "password": "123456"
  }'
```

#### **Criar Jogo**
```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "nome": "Mega Sena",
    "descricao": "Loteria com 60 números",
    "preco": 4.50
  }'
```

#### **Registrar Venda**
```bash
curl -X POST http://localhost:8080/api/v1/vendas-caixa \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "caixaId": "caixa-id",
    "jogoId": "jogo-id",
    "quantidade": 10,
    "dataVenda": "2024-01-15"
  }'
```

## 📊 **Monitoramento e Logs**

### **Logs Estruturados**
```json
{
  "timestamp": "2024-01-15T10:30:15.123Z",
  "level": "INFO",
  "logger": "com.loteria360.service.UsuarioService",
  "message": "Usuário criado com sucesso: user-123",
  "traceId": "abc123def456",
  "spanId": "def456ghi789"
}
```

### **Métricas de Aplicação**
- **Health Check**: `/actuator/health`
- **Info**: `/actuator/info`
- **Metrics**: `/actuator/metrics`

### **Auditoria**
Todas as operações são auditadas automaticamente:
- **Usuário**: Quem fez a operação
- **Timestamp**: Quando foi feita
- **Operação**: INSERT, UPDATE, DELETE
- **Dados**: Antes e depois (JSON)

## 🔧 **Manutenção**

### **Backup do Banco**
```bash
# Backup completo
mysqldump -u loteria -p loteria360 > backup_$(date +%Y%m%d_%H%M%S).sql

# Backup apenas dados
mysqldump -u loteria -p --no-create-info loteria360 > data_backup.sql
```

### **Limpeza de Logs**
```bash
# Limpar logs antigos (manter últimos 30 dias)
find logs/ -name "*.log" -mtime +30 -delete
```

### **Atualização de Dependências**
```bash
# Verificar atualizações
mvn versions:display-dependency-updates

# Atualizar dependências
mvn versions:use-latest-versions
```

## 🤝 **Contribuição**

### **Padrões de Código**
- **Java**: Google Java Style Guide
- **Commits**: Conventional Commits
- **Branches**: Git Flow
- **PRs**: Sempre com testes e documentação

### **Processo de Contribuição**
1. Fork o projeto
2. Crie uma branch para sua feature
3. Implemente com testes
4. Submeta um Pull Request
5. Aguarde review e aprovação

## 📄 **Licença**

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## 📞 **Suporte**

Para suporte e dúvidas:
- **Email**: suporte@loteria360.com
- **Documentação**: https://docs.loteria360.com
- **Issues**: GitHub Issues

---

**Loteria360 Backend** - Sistema robusto e escalável para gestão de casas lotéricas 🎰
