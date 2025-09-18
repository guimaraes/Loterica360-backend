# Exemplos de Teste de Permissões

## Cenários de Teste

### 🔧 **ADMIN - Acesso Total**

#### ✅ **Criar Jogo (Permitido)**
```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_admin>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mega Sena",
    "descricao": "Jogo da Mega Sena",
    "preco": 4.50
  }'

# Expected: 201 Created
```

#### ✅ **Atualizar Jogo (Permitido)**
```bash
curl -X PUT http://localhost:8080/api/v1/jogos/{id} \
  -H "Authorization: Bearer <token_admin>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Mega Sena Atualizada",
    "descricao": "Jogo da Mega Sena com nova descrição",
    "preco": 5.00
  }'

# Expected: 200 OK
```

#### ✅ **Ativar/Desativar Jogo (Permitido)**
```bash
curl -X PATCH http://localhost:8080/api/v1/jogos/{id}/toggle-status \
  -H "Authorization: Bearer <token_admin>"

# Expected: 200 OK
```

### 👔 **GERENTE - Acesso Gerencial**

#### ✅ **Criar Jogo (Permitido)**
```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_gerente>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Quina",
    "descricao": "Jogo da Quina",
    "preco": 2.00
  }'

# Expected: 201 Created
```

#### ✅ **Listar Usuários (Permitido)**
```bash
curl -X GET http://localhost:8080/api/v1/usuarios \
  -H "Authorization: Bearer <token_gerente>"

# Expected: 200 OK
```

#### ❌ **Criar Usuário (Negado)**
```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Authorization: Bearer <token_gerente>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Novo Usuário",
    "email": "novo@exemplo.com",
    "senha": "123456",
    "papel": "VENDEDOR"
  }'

# Expected: 403 Forbidden
```

### 💼 **VENDEDOR - Acesso Operacional**

#### ❌ **Criar Jogo (Negado)**
```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Novo Jogo",
    "descricao": "Tentativa de criar jogo",
    "preco": 3.00
  }'

# Expected: 403 Forbidden
# Response: {
#   "timestamp": "2024-01-15T10:30:00.000Z",
#   "status": 403,
#   "error": "Forbidden",
#   "message": "Access Denied",
#   "path": "/api/v1/jogos"
# }
```

#### ❌ **Atualizar Jogo (Negado)**
```bash
curl -X PUT http://localhost:8080/api/v1/jogos/{id} \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Jogo Atualizado",
    "descricao": "Tentativa de atualizar",
    "preco": 4.00
  }'

# Expected: 403 Forbidden
```

#### ✅ **Listar Jogos (Permitido)**
```bash
curl -X GET http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_vendedor>"

# Expected: 200 OK
# Response: {
#   "content": [
#     {
#       "id": "1",
#       "nome": "Mega Sena",
#       "descricao": "Jogo da Mega Sena",
#       "preco": 4.50,
#       "ativo": true
#     }
#   ],
#   "totalElements": 1,
#   "totalPages": 1
# }
```

#### ✅ **Criar Cliente (Permitido)**
```bash
curl -X POST http://localhost:8080/api/v1/clientes \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "cpf": "123.456.789-00",
    "email": "joao@exemplo.com",
    "telefone": "(11) 99999-9999"
  }'

# Expected: 201 Created
```

#### ✅ **Atualizar Cliente (Permitido)**
```bash
curl -X PUT http://localhost:8080/api/v1/clientes/{id} \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva Atualizado",
    "cpf": "123.456.789-00",
    "email": "joao.novo@exemplo.com",
    "telefone": "(11) 88888-8888"
  }'

# Expected: 200 OK
```

### 🔍 **AUDITOR - Acesso de Consulta**

#### ❌ **Criar Jogo (Negado)**
```bash
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_auditor>" \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Jogo Auditor",
    "descricao": "Tentativa de auditor",
    "preco": 2.50
  }'

# Expected: 403 Forbidden
```

#### ✅ **Listar Jogos (Permitido)**
```bash
curl -X GET http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_auditor>"

# Expected: 200 OK
```

#### ✅ **Listar Vendas (Permitido)**
```bash
curl -X GET http://localhost:8080/api/v1/vendas \
  -H "Authorization: Bearer <token_auditor>"

# Expected: 200 OK
```

## Testes Automatizados

### 🧪 **Teste de Integração - VENDEDOR**

```java
@Test
@WithMockUser(roles = "VENDEDOR")
void testVendedorNaoPodeCriarJogo() throws Exception {
    CriarJogoRequest request = new CriarJogoRequest();
    request.setNome("Novo Jogo");
    request.setDescricao("Teste");
    request.setPreco(BigDecimal.valueOf(3.00));
    
    mockMvc.perform(post("/api/v1/jogos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isForbidden());
}

@Test
@WithMockUser(roles = "VENDEDOR")
void testVendedorPodeCriarCliente() throws Exception {
    ClienteRequest request = new ClienteRequest();
    request.setNome("Cliente Teste");
    request.setCpf("123.456.789-00");
    request.setEmail("cliente@teste.com");
    
    mockMvc.perform(post("/api/v1/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated());
}
```

### 🧪 **Teste de Integração - ADMIN**

```java
@Test
@WithMockUser(roles = "ADMIN")
void testAdminPodeCriarJogo() throws Exception {
    CriarJogoRequest request = new CriarJogoRequest();
    request.setNome("Jogo Admin");
    request.setDescricao("Criado por admin");
    request.setPreco(BigDecimal.valueOf(5.00));
    
    mockMvc.perform(post("/api/v1/jogos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome").value("Jogo Admin"));
}
```

## Logs de Segurança

### 📊 **Exemplo de Log de Acesso Negado**

```
2024-01-15 10:30:15.123 WARN  [http-nio-8080-exec-1] o.s.s.a.i.a.ExceptionTranslationFilter : Access Denied for user: vendedor@loteria360.com
Attempted access to: POST /api/v1/jogos
Required roles: [ADMIN, GERENTE]
User roles: [VENDEDOR]
IP: 192.168.1.100
User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36
```

### 📈 **Métricas de Segurança**

```json
{
  "timestamp": "2024-01-15T10:30:00Z",
  "security_metrics": {
    "access_denied_by_role": {
      "VENDEDOR": {
        "POST /api/v1/jogos": 5,
        "PUT /api/v1/jogos/{id}": 2,
        "PATCH /api/v1/jogos/{id}/toggle-status": 1
      },
      "AUDITOR": {
        "POST /api/v1/jogos": 3,
        "POST /api/v1/clientes": 2
      }
    },
    "successful_access_by_role": {
      "VENDEDOR": {
        "GET /api/v1/jogos": 25,
        "POST /api/v1/clientes": 15,
        "PUT /api/v1/clientes/{id}": 8
      }
    }
  }
}
```

## Cenários de Erro

### ❌ **Token Inválido**
```bash
curl -X GET http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer token_invalido"

# Expected: 401 Unauthorized
# Response: {
#   "timestamp": "2024-01-15T10:30:00.000Z",
#   "status": 401,
#   "error": "Unauthorized",
#   "message": "JWT signature does not match locally computed signature",
#   "path": "/api/v1/jogos"
# }
```

### ❌ **Token Expirado**
```bash
curl -X GET http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_expirado>"

# Expected: 401 Unauthorized
# Response: {
#   "timestamp": "2024-01-15T10:30:00.000Z",
#   "status": 401,
#   "error": "Unauthorized",
#   "message": "JWT expired at 2024-01-15T09:00:00Z",
#   "path": "/api/v1/jogos"
# }
```

### ❌ **Sem Token**
```bash
curl -X GET http://localhost:8080/api/v1/jogos

# Expected: 401 Unauthorized
# Response: {
#   "timestamp": "2024-01-15T10:30:00.000Z",
#   "status": 401,
#   "error": "Unauthorized",
#   "message": "Full authentication is required to access this resource",
#   "path": "/api/v1/jogos"
# }
```

## Monitoramento em Produção

### 📊 **Dashboards de Segurança**

1. **Tentativas de Acesso Negado por Hora**
   - Gráfico de linha mostrando tentativas de acesso negado
   - Alertas para picos anômalos

2. **Top Endpoints Acessados por Perfil**
   - Ranking dos endpoints mais acessados
   - Identificação de padrões de uso

3. **Distribuição de Acessos por Perfil**
   - Gráfico de pizza com percentual de uso
   - Identificação de perfis mais ativos

### 🚨 **Alertas de Segurança**

```yaml
alerts:
  - name: "High Access Denied Rate"
    condition: "rate(access_denied_total[5m]) > 10"
    severity: "warning"
    message: "High rate of access denied attempts detected"
    
  - name: "Multiple Failed Attempts from Same IP"
    condition: "count by (source_ip) (access_denied_total) > 20"
    severity: "critical"
    message: "Multiple failed access attempts from {{ $labels.source_ip }}"
    
  - name: "Unauthorized Game Creation Attempts"
    condition: "increase(game_creation_denied_total[1h]) > 5"
    severity: "warning"
    message: "Multiple unauthorized game creation attempts detected"
```

## Próximos Passos

### 🔒 **Melhorias de Segurança**
- [ ] Implementar rate limiting por IP
- [ ] Adicionar 2FA para perfis administrativos
- [ ] Logs de auditoria mais detalhados
- [ ] Alertas em tempo real

### 📊 **Monitoramento**
- [ ] Dashboard de métricas de segurança
- [ ] Relatórios de uso por perfil
- [ ] Análise de padrões de acesso
- [ ] Detecção de anomalias

### 🧪 **Testes**
- [ ] Cobertura completa de testes de permissão
- [ ] Testes de penetração automatizados
- [ ] Simulação de ataques
- [ ] Validação de tokens JWT
