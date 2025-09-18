# Permissões de Usuários por Perfil

## Visão Geral

Este documento define as permissões de cada perfil de usuário no sistema Loteria360.

## Perfis de Usuário

### 🔧 **ADMIN**
- **Acesso Total**: Pode fazer qualquer operação no sistema
- **Responsabilidades**: Administração completa do sistema

### 👔 **GERENTE** 
- **Acesso Gerencial**: Pode gerenciar jogos, usuários e operações
- **Responsabilidades**: Gerenciamento operacional e de jogos

### 💼 **VENDEDOR**
- **Acesso Operacional**: Pode realizar vendas e gerenciar clientes
- **Responsabilidades**: Operações de venda e atendimento ao cliente

### 🔍 **AUDITOR**
- **Acesso de Consulta**: Pode visualizar dados e relatórios
- **Responsabilidades**: Análise e auditoria de dados

## Permissões Detalhadas

### 🎮 **Jogos**

#### ✅ **ADMIN e GERENTE**
- ✅ Criar jogos (`POST /api/v1/jogos`)
- ✅ Atualizar jogos (`PUT /api/v1/jogos/{id}`)
- ✅ Ativar/Desativar jogos (`PATCH /api/v1/jogos/{id}/toggle-status`)
- ✅ Listar jogos (`GET /api/v1/jogos`)
- ✅ Buscar jogos por ID (`GET /api/v1/jogos/{id}`)
- ✅ Buscar jogos por nome (`GET /api/v1/jogos/nome/{nome}`)
- ✅ Listar jogos ativos (`GET /api/v1/jogos/ativos`)

#### ❌ **VENDEDOR e AUDITOR**
- ❌ Criar jogos
- ❌ Atualizar jogos
- ❌ Ativar/Desativar jogos
- ✅ Listar jogos (apenas leitura)
- ✅ Buscar jogos (apenas leitura)
- ✅ Listar jogos ativos (apenas leitura)

### 👥 **Usuários**

#### ✅ **ADMIN**
- ✅ Criar usuários (`POST /api/v1/usuarios`)
- ✅ Atualizar usuários (`PUT /api/v1/usuarios/{id}`)
- ✅ Ativar/Desativar usuários (`PATCH /api/v1/usuarios/{id}/toggle-status`)
- ✅ Alterar senhas (`PUT /api/v1/usuarios/{id}/senha`)
- ✅ Listar usuários (`GET /api/v1/usuarios`)
- ✅ Buscar usuários por ID (`GET /api/v1/usuarios/{id}`)

#### ✅ **GERENTE**
- ✅ Listar usuários (`GET /api/v1/usuarios`)
- ✅ Buscar usuários por ID (`GET /api/v1/usuarios/{id}`)
- ❌ Criar usuários
- ❌ Atualizar usuários
- ❌ Ativar/Desativar usuários
- ❌ Alterar senhas

#### ❌ **VENDEDOR e AUDITOR**
- ❌ Todas as operações de usuários (exceto dados próprios)
- ✅ Visualizar dados próprios (`GET /api/v1/usuarios/me`)

### 👤 **Clientes**

#### ✅ **ADMIN, GERENTE e VENDEDOR**
- ✅ Criar clientes (`POST /api/v1/clientes`)
- ✅ Atualizar clientes (`PUT /api/v1/clientes/{id}`)
- ✅ Listar clientes (`GET /api/v1/clientes`)
- ✅ Buscar clientes (`GET /api/v1/clientes/search`)
- ✅ Buscar cliente por ID (`GET /api/v1/clientes/{id}`)

#### ✅ **AUDITOR**
- ✅ Listar clientes (apenas leitura)
- ✅ Buscar clientes (apenas leitura)
- ✅ Buscar cliente por ID (apenas leitura)
- ❌ Criar clientes
- ❌ Atualizar clientes

### 🛒 **Vendas**

#### ✅ **ADMIN, GERENTE e VENDEDOR**
- ✅ Criar vendas
- ✅ Listar vendas
- ✅ Buscar vendas
- ✅ Atualizar vendas

#### ✅ **AUDITOR**
- ✅ Listar vendas (apenas leitura)
- ✅ Buscar vendas (apenas leitura)
- ❌ Criar vendas
- ❌ Atualizar vendas

### 📊 **Relatórios**

#### ✅ **ADMIN, GERENTE e AUDITOR**
- ✅ Acessar todos os relatórios
- ✅ Gerar relatórios
- ✅ Exportar dados

#### ✅ **VENDEDOR**
- ✅ Acessar relatórios de vendas próprias
- ✅ Relatórios básicos
- ❌ Relatórios administrativos

### 📈 **Dashboard**

#### ✅ **Todos os Perfis**
- ✅ Acessar dashboard
- ✅ Visualizar métricas gerais
- ✅ Acessar análise avançada (com restrições por perfil)

## Alterações Implementadas

### ❌ **Restrição para VENDEDOR em Jogos**

**Antes:**
```java
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
public ResponseEntity<JogoResponse> criarJogo(...)
```

**Depois:**
```java
@PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
public ResponseEntity<JogoResponse> criarJogo(...)
```

**Métodos Afetados:**
- ✅ `criarJogo()` - Apenas ADMIN e GERENTE
- ✅ `atualizarJogo()` - Apenas ADMIN e GERENTE  
- ✅ `ativarDesativarJogo()` - Apenas ADMIN e GERENTE

**Métodos Mantidos para VENDEDOR:**
- ✅ `listarJogos()` - Apenas leitura
- ✅ `buscarPorId()` - Apenas leitura
- ✅ `buscarPorNome()` - Apenas leitura
- ✅ `listarJogosAtivos()` - Apenas leitura

## Justificativa das Permissões

### 🎮 **Por que VENDEDOR não pode gerenciar jogos?**

1. **Separação de Responsabilidades**
   - Vendedores focam em vendas e atendimento
   - Gerenciamento de jogos é responsabilidade gerencial

2. **Controle de Qualidade**
   - Jogos são produtos centrais do negócio
   - Alterações devem ser aprovadas por níveis superiores

3. **Integridade dos Dados**
   - Previne alterações acidentais em jogos
   - Mantém consistência nos dados de produtos

4. **Auditoria e Compliance**
   - Rastreabilidade de mudanças em jogos
   - Controle de acesso adequado

### 👤 **Por que VENDEDOR pode gerenciar clientes?**

1. **Necessidade Operacional**
   - Vendedores precisam cadastrar novos clientes
   - Atualização de dados durante atendimento

2. **Eficiência no Atendimento**
   - Reduz dependência de outros perfis
   - Agiliza processo de vendas

3. **Responsabilidade Limitada**
   - Não afeta configurações críticas do sistema
   - Dados de clientes são operacionais

## Testes de Permissões

### ✅ **Teste 1: VENDEDOR tentando criar jogo**
```
POST /api/v1/jogos
Authorization: Bearer <token_vendedor>
Body: { "nome": "Novo Jogo", "preco": 5.00 }

Expected: 403 Forbidden
```

### ✅ **Teste 2: VENDEDOR listando jogos**
```
GET /api/v1/jogos
Authorization: Bearer <token_vendedor>

Expected: 200 OK (lista de jogos)
```

### ✅ **Teste 3: VENDEDOR criando cliente**
```
POST /api/v1/clientes
Authorization: Bearer <token_vendedor>
Body: { "nome": "Cliente Novo", "cpf": "123.456.789-00" }

Expected: 201 Created
```

### ✅ **Teste 4: ADMIN criando jogo**
```
POST /api/v1/jogos
Authorization: Bearer <token_admin>
Body: { "nome": "Novo Jogo", "preco": 5.00 }

Expected: 201 Created
```

## Monitoramento e Logs

### 📊 **Logs de Acesso Negado**
```java
// Logs automáticos do Spring Security
Access Denied for user: vendedor@exemplo.com
Attempted access to: POST /api/v1/jogos
Required roles: [ADMIN, GERENTE]
User roles: [VENDEDOR]
```

### 📈 **Métricas de Segurança**
- Tentativas de acesso negado por perfil
- Endpoints mais acessados por perfil
- Alertas para tentativas suspeitas

## Próximos Passos

### 🔒 **Melhorias de Segurança**
- [ ] Implementar rate limiting por perfil
- [ ] Adicionar logs de auditoria detalhados
- [ ] Configurar alertas de segurança

### 👥 **Gestão de Usuários**
- [ ] Interface para gerenciar permissões
- [ ] Relatórios de uso por perfil
- [ ] Treinamento sobre permissões

### 🔧 **Funcionalidades Futuras**
- [ ] Permissões granulares por funcionalidade
- [ ] Aprovação em workflow para alterações críticas
- [ ] Backup automático antes de alterações importantes
