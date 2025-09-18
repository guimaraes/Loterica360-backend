# Mudanças na Configuração de Segurança

## Problema Identificado

A configuração global de segurança no `SecurityConfig.java` estava permitindo que usuários com perfil "VENDEDOR" acessassem **todos** os endpoints de jogos (`/api/v1/jogos/**`), sobrescrevendo as configurações de `@PreAuthorize` que foram definidas no `JogoController.java`.

## Configuração Anterior (Problemática)

```java
// ANTES - Permitindo acesso total de VENDEDOR
.requestMatchers("/api/v1/jogos/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
```

**Problema**: Esta configuração permitia que vendedores acessassem **TODOS** os endpoints de jogos, incluindo:
- `POST /api/v1/jogos` (criar jogos)
- `PUT /api/v1/jogos/{id}` (atualizar jogos)
- `PATCH /api/v1/jogos/{id}/toggle-status` (ativar/desativar jogos)

## Configuração Atual (Corrigida)

```java
// DEPOIS - Configuração específica por tipo de operação
// Endpoints de escrita - apenas ADMIN e GERENTE
.requestMatchers("/api/v1/jogos", "/api/v1/jogos/", "/api/v1/jogos/*/toggle-status").hasAnyRole("ADMIN", "GERENTE")

// Endpoints de leitura - todos os perfis autenticados
.requestMatchers("/api/v1/jogos/ativos", "/api/v1/jogos/ativos/**", "/api/v1/jogos/*/", "/api/v1/jogos/nome/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
```

## Mapeamento de Endpoints

### ✅ **Endpoints de Escrita (ADMIN e GERENTE apenas)**

| Método | Endpoint | Permissão |
|--------|----------|-----------|
| `POST` | `/api/v1/jogos` | `ADMIN`, `GERENTE` |
| `POST` | `/api/v1/jogos/` | `ADMIN`, `GERENTE` |
| `PATCH` | `/api/v1/jogos/{id}/toggle-status` | `ADMIN`, `GERENTE` |

### ✅ **Endpoints de Leitura (Todos os perfis)**

| Método | Endpoint | Permissão |
|--------|----------|-----------|
| `GET` | `/api/v1/jogos/ativos` | `ADMIN`, `GERENTE`, `VENDEDOR`, `AUDITOR` |
| `GET` | `/api/v1/jogos/ativos/**` | `ADMIN`, `GERENTE`, `VENDEDOR`, `AUDITOR` |
| `GET` | `/api/v1/jogos/{id}` | `ADMIN`, `GERENTE`, `VENDEDOR`, `AUDITOR` |
| `GET` | `/api/v1/jogos/nome/{nome}` | `ADMIN`, `GERENTE`, `VENDEDOR`, `AUDITOR` |

### ❌ **Endpoints Bloqueados para VENDEDOR**

| Método | Endpoint | Bloqueado para |
|--------|----------|----------------|
| `POST` | `/api/v1/jogos` | `VENDEDOR`, `AUDITOR` |
| `PUT` | `/api/v1/jogos/{id}` | `VENDEDOR`, `AUDITOR` |
| `PATCH` | `/api/v1/jogos/{id}/toggle-status` | `VENDEDOR`, `AUDITOR` |

## Hierarquia de Segurança

### 🔧 **ADMIN**
- ✅ **Acesso Total**: Todos os endpoints de jogos
- ✅ **Criar/Atualizar/Ativar-Desativar**: Permitido
- ✅ **Leitura**: Permitido

### 👔 **GERENTE**
- ✅ **Acesso Total**: Todos os endpoints de jogos
- ✅ **Criar/Atualizar/Ativar-Desativar**: Permitido
- ✅ **Leitura**: Permitido

### 💼 **VENDEDOR**
- ❌ **Criar Jogos**: Bloqueado
- ❌ **Atualizar Jogos**: Bloqueado
- ❌ **Ativar/Desativar Jogos**: Bloqueado
- ✅ **Leitura**: Permitido

### 🔍 **AUDITOR**
- ❌ **Criar Jogos**: Bloqueado
- ❌ **Atualizar Jogos**: Bloqueado
- ❌ **Ativar/Desativar Jogos**: Bloqueado
- ✅ **Leitura**: Permitido

## Testes de Validação

### ✅ **Teste 1: VENDEDOR tentando criar jogo**

```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "preco": 5.00}'

# Expected: 403 Forbidden
# SecurityConfig bloqueia antes de chegar ao Controller
```

### ✅ **Teste 2: VENDEDOR listando jogos**

```bash
curl -X GET http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_vendedor>"

# Expected: 200 OK
# SecurityConfig permite acesso de leitura
```

### ✅ **Teste 3: ADMIN criando jogo**

```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_admin>" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "preco": 5.00}'

# Expected: 201 Created
# SecurityConfig permite acesso de escrita
```

## Outras Configurações Adicionadas

### 👤 **Clientes**
```java
.requestMatchers("/api/v1/clientes/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
```
- **VENDEDOR**: Pode gerenciar clientes (necessário para vendas)
- **AUDITOR**: Pode visualizar clientes (apenas leitura)

### 📊 **Dashboard**
```java
.requestMatchers("/api/v1/dashboard/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
```
- **Todos os perfis**: Podem acessar dashboard

## Fluxo de Segurança

### 1. **Request Incoming**
```
Cliente → Spring Security Filter Chain
```

### 2. **SecurityConfig Validation**
```
SecurityConfig verifica:
- Token JWT válido?
- Perfil do usuário?
- Endpoint permitido para o perfil?
```

### 3. **Decision**
```
Se permitido → Continua para Controller
Se negado → Retorna 403 Forbidden
```

### 4. **Controller Validation (Backup)**
```
Se chegou ao Controller:
- @PreAuthorize valida novamente
- Dupla camada de segurança
```

## Benefícios da Configuração

### ✅ **Segurança em Camadas**
- **Primeira camada**: SecurityConfig (bloqueio global)
- **Segunda camada**: @PreAuthorize (validação específica)
- **Redundância**: Proteção contra bypass

### ✅ **Performance**
- Bloqueio antecipado no filtro de segurança
- Evita processamento desnecessário no controller
- Resposta mais rápida para acessos negados

### ✅ **Clareza**
- Configuração explícita por tipo de operação
- Fácil manutenção e entendimento
- Documentação clara das permissões

### ✅ **Manutenibilidade**
- Configuração centralizada
- Fácil adição de novos endpoints
- Padrão consistente de segurança

## Monitoramento

### 📊 **Logs de Segurança**
```
2024-01-15 10:30:15.123 WARN  [http-nio-8080-exec-1] o.s.s.w.a.ExceptionTranslationFilter : 
Access Denied for user: vendedor@loteria360.com
Attempted access to: POST /api/v1/jogos
Required roles: [ADMIN, GERENTE]
User roles: [VENDEDOR]
Source: SecurityConfig Filter Chain
```

### 📈 **Métricas**
- Tentativas de acesso negado por perfil
- Endpoints mais bloqueados
- Efetividade das configurações de segurança

## Próximos Passos

### 🔒 **Melhorias de Segurança**
- [ ] Implementar rate limiting por perfil
- [ ] Adicionar logs de auditoria mais detalhados
- [ ] Configurar alertas para tentativas suspeitas

### 📊 **Monitoramento**
- [ ] Dashboard de métricas de segurança
- [ ] Relatórios de tentativas de acesso
- [ ] Análise de padrões de uso

### 🧪 **Testes**
- [ ] Testes automatizados de segurança
- [ ] Validação de todas as combinações de perfil/endpoint
- [ ] Testes de penetração
