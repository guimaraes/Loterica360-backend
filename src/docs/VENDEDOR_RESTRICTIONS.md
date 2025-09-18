# Restrições de Acesso para Usuários VENDEDOR

## Visão Geral

Implementação de restrições rigorosas para usuários com perfil **VENDEDOR**, limitando seu acesso apenas às funcionalidades essenciais para realização de vendas.

## 🔒 **Backend - Configuração de Segurança**

### **SecurityConfig.java - Restrições Implementadas**

```java
// Endpoints apenas para ADMIN e GERENTE
.requestMatchers("/api/v1/usuarios/**").hasRole("ADMIN")
.requestMatchers("/api/v1/jogos/**").hasAnyRole("ADMIN", "GERENTE")
.requestMatchers("/api/v1/boloes/**").hasAnyRole("ADMIN", "GERENTE")
.requestMatchers("/api/v1/caixas/**").hasAnyRole("ADMIN", "GERENTE")
.requestMatchers("/api/v1/turnos/**").hasAnyRole("ADMIN", "GERENTE")
.requestMatchers("/api/v1/movimentos/**").hasAnyRole("ADMIN", "GERENTE")
.requestMatchers("/api/v1/relatorios/**").hasAnyRole("ADMIN", "GERENTE", "AUDITOR")
.requestMatchers("/api/v1/dashboard/**").hasAnyRole("ADMIN", "GERENTE", "AUDITOR")

// Endpoints para VENDEDOR - apenas vendas e clientes
.requestMatchers("/api/v1/vendas/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
.requestMatchers("/api/v1/vendas-caixa/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
.requestMatchers("/api/v1/contagem-caixa/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR")
.requestMatchers("/api/v1/clientes/**").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")

// Endpoints de leitura para VENDEDOR (necessários para vendas)
.requestMatchers("/api/v1/jogos/ativos").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
.requestMatchers("/api/v1/caixas/ativas").hasAnyRole("ADMIN", "GERENTE", "VENDEDOR", "AUDITOR")
```

### **Mapeamento de Permissões por Endpoint**

#### ✅ **VENDEDOR - Endpoints Permitidos**

| Método | Endpoint | Justificativa |
|--------|----------|---------------|
| `GET/POST` | `/api/v1/vendas/**` | Realizar vendas |
| `GET/POST` | `/api/v1/vendas-caixa/**` | Controle de vendas por caixa |
| `GET/POST` | `/api/v1/contagem-caixa/**` | Contagem de caixa |
| `GET/POST/PUT` | `/api/v1/clientes/**` | Gerenciar clientes (necessário para vendas) |
| `GET` | `/api/v1/jogos/ativos` | Listar jogos ativos para venda |
| `GET` | `/api/v1/caixas/ativas` | Listar caixas ativas |

#### ❌ **VENDEDOR - Endpoints Bloqueados**

| Método | Endpoint | Motivo do Bloqueio |
|--------|----------|-------------------|
| `ALL` | `/api/v1/usuarios/**` | Não pode gerenciar usuários |
| `POST/PUT/PATCH` | `/api/v1/jogos/**` | Não pode criar/editar jogos |
| `ALL` | `/api/v1/boloes/**` | Não pode gerenciar bolões |
| `ALL` | `/api/v1/caixas/**` | Não pode gerenciar caixas |
| `ALL` | `/api/v1/turnos/**` | Não pode gerenciar turnos |
| `ALL` | `/api/v1/movimentos/**` | Não pode gerenciar movimentos |
| `ALL` | `/api/v1/relatorios/**` | Não pode acessar relatórios |
| `ALL` | `/api/v1/dashboard/**` | Não pode acessar dashboard |

## 🎨 **Frontend - Sistema de Permissões**

### **Hook usePermissions.tsx**

```typescript
export function usePermissions(): Permissions {
  const { user } = useAuth()

  switch (papel) {
    case 'VENDEDOR':
      return {
        canAccessDashboard: false,
        canAccessAnalysis: false,
        canManageUsers: false,
        canManageClients: true, // Necessário para vendas
        canManageGames: false,
        canManageBoloes: false,
        canManageSales: true,
        canManageShifts: false,
        canManageMovements: false,
        canAccessReports: false,
        canOnlyAccessSales: true, // Vendedor só pode acessar vendas
      }
  }
}
```

### **Menu Filtrado por Permissões**

#### **VENDEDOR - Menu Simplificado**

```typescript
// Menu para vendedores
[
  {
    id: 'vendas',
    title: 'Vendas',
    items: [
      {
        id: 'vendas-geral',
        name: 'Sistema de Vendas',
        icon: 'ShoppingCart',
        children: [
          {
            id: 'vendas',
            name: 'Vendas',
            href: '/vendas',
            icon: 'ShoppingCart'
          },
          {
            id: 'clientes',
            name: 'Clientes',
            href: '/clientes',
            icon: 'UserCheck'
          }
        ]
      }
    ]
  }
]
```

### **Componentes de Proteção**

#### **VendedorRedirect.tsx**
- Redireciona automaticamente vendedores para `/vendas`
- Permite acesso apenas a `/vendas`, `/clientes` e `/login`

#### **PermissionRoute.tsx**
- Componentes específicos para diferentes perfis
- `AdminRoute`, `GerenteRoute`, `VendedorRoute`, `AuditorRoute`

### **Rotas Protegidas no App.tsx**

```typescript
// Rotas acessíveis por todos os perfis autenticados
<Route path="/vendas" element={<VendasPage />} />
<Route path="/clientes" element={<ClientesPage />} />

// Rotas restritas por perfil
<Route path="/" element={
  <AuditorRoute>
    <DashboardPage />
  </AuditorRoute>
} />

<Route path="/jogos" element={
  <GerenteRoute>
    <JogosPage />
  </GerenteRoute>
} />

<Route path="/usuarios" element={
  <AdminRoute>
    <UsuariosPage />
  </AdminRoute>
} />
```

## 🔄 **Fluxo de Funcionamento**

### **1. Login do Vendedor**
```
Vendedor faz login → Token JWT gerado → Informações do usuário carregadas
```

### **2. Verificação de Permissões**
```
usePermissions() → Verifica papel do usuário → Retorna permissões específicas
```

### **3. Menu Filtrado**
```
useFilteredMenu() → Filtra menu baseado nas permissões → Exibe apenas opções permitidas
```

### **4. Redirecionamento Automático**
```
VendedorRedirect → Verifica se está em página permitida → Redireciona se necessário
```

### **5. Proteção de Rotas**
```
PermissionRoute → Verifica permissões → Bloqueia acesso ou permite
```

## 🎯 **Experiência do Usuário VENDEDOR**

### **Interface Simplificada**
- **Header**: Mostra "Sistema de Vendas" com ícone de carrinho
- **Sidebar**: Apenas menu de vendas e clientes
- **Navegação**: Redirecionamento automático para vendas

### **Funcionalidades Disponíveis**
1. **Realizar Vendas**
   - Selecionar jogos ativos
   - Selecionar caixas ativas
   - Processar pagamentos
   - Registrar vendas

2. **Gerenciar Clientes**
   - Cadastrar novos clientes
   - Editar informações de clientes
   - Buscar clientes existentes

3. **Contagem de Caixa**
   - Registrar contagem de notas e moedas
   - Calcular totais

### **Funcionalidades Bloqueadas**
- ❌ Dashboard e análises
- ❌ Gerenciamento de jogos
- ❌ Gerenciamento de bolões
- ❌ Gerenciamento de usuários
- ❌ Relatórios
- ❌ Turnos e movimentos

## 🛡️ **Segurança em Camadas**

### **1ª Camada: SecurityConfig (Backend)**
- Bloqueio antecipado no filtro de segurança
- Performance otimizada
- Logs de segurança automáticos

### **2ª Camada: @PreAuthorize (Controller)**
- Validação adicional nos controllers
- Redundância de segurança
- Proteção contra bypass

### **3ª Camada: Frontend Permissions**
- Filtro de menu baseado em permissões
- Redirecionamento automático
- Proteção de rotas

### **4ª Camada: Component Protection**
- Componentes específicos por perfil
- Verificação contínua de permissões
- Interface adaptada por perfil

## 📊 **Testes de Validação**

### **✅ Teste 1: VENDEDOR acessando vendas**
```bash
# Login como vendedor
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "vendedor@loteria360.com", "password": "123456"}'

# Acesso a vendas (deve funcionar)
curl -X GET http://localhost:8080/api/v1/vendas \
  -H "Authorization: Bearer <token_vendedor>"

# Expected: 200 OK
```

### **❌ Teste 2: VENDEDOR acessando jogos**
```bash
# Tentativa de criar jogo
curl -X POST http://localhost:8080/api/v1/jogos \
  -H "Authorization: Bearer <token_vendedor>" \
  -H "Content-Type: application/json" \
  -d '{"nome": "Teste", "preco": 5.00}'

# Expected: 403 Forbidden
```

### **❌ Teste 3: VENDEDOR acessando dashboard**
```bash
# Tentativa de acessar dashboard
curl -X GET http://localhost:8080/api/v1/dashboard \
  -H "Authorization: Bearer <token_vendedor>"

# Expected: 403 Forbidden
```

## 🔧 **Manutenção e Extensão**

### **Adicionando Novas Restrições**
1. Atualizar `SecurityConfig.java`
2. Modificar `usePermissions.tsx`
3. Ajustar `useFilteredMenu()`
4. Atualizar componentes de proteção

### **Monitoramento**
- Logs de tentativas de acesso negado
- Métricas de uso por perfil
- Alertas de segurança

### **Escalabilidade**
- Sistema modular e extensível
- Fácil adição de novos perfis
- Configuração centralizada

## 📈 **Benefícios Implementados**

### **✅ Segurança**
- Acesso restrito por perfil
- Múltiplas camadas de proteção
- Logs de auditoria

### **✅ Usabilidade**
- Interface simplificada para vendedores
- Redirecionamento automático
- Menu contextual

### **✅ Performance**
- Bloqueio antecipado no backend
- Interface otimizada
- Carregamento rápido

### **✅ Manutenibilidade**
- Código modular
- Configuração centralizada
- Fácil extensão

## 🎯 **Resultado Final**

**Usuários VENDEDOR agora têm acesso restrito apenas às funcionalidades essenciais para vendas:**

- ✅ **Sistema de Vendas**: Interface completa para realização de vendas
- ✅ **Gerenciamento de Clientes**: Cadastro e edição de clientes
- ✅ **Contagem de Caixa**: Registro de contagens
- ❌ **Todas as outras funcionalidades**: Bloqueadas por segurança

O sistema garante que vendedores foquem exclusivamente em suas responsabilidades de venda, mantendo a segurança e integridade dos dados do sistema.
