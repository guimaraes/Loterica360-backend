# Melhorias no Tratamento de Erros

## Visão Geral

Implementação de um sistema robusto e amigável de tratamento de erros tanto no backend quanto no frontend, garantindo que os usuários recebam mensagens claras e acionáveis quando algo dá errado.

## 🔧 **Backend - Melhorias Implementadas**

### **1. GlobalExceptionHandler Aprimorado**

#### **Novos Handlers de Exception:**
- `DisabledException` - Conta desabilitada
- `LockedException` - Conta bloqueada
- `DataIntegrityViolationException` - Violação de integridade de dados
- `EmptyResultDataAccessException` - Registro não encontrado
- `MissingServletRequestParameterException` - Parâmetro obrigatório ausente
- `MethodArgumentTypeMismatchException` - Tipo de parâmetro inválido
- `HttpMessageNotReadableException` - Requisição malformada
- `ConstraintViolationException` - Violação de constraints

#### **Mensagens Amigáveis:**
```java
// Antes
"não deve estar vazio"

// Depois
"Nome é obrigatório"
```

#### **Mapeamento de Campos:**
```java
private String getFieldName(String field) {
    switch (field) {
        case "nome": return "Nome";
        case "email": return "Email";
        case "senha": return "Senha";
        case "preco": return "Preço";
        case "cpf": return "CPF";
        // ... mais campos
    }
}
```

### **2. Exceptions Customizadas**

#### **BusinessException**
```java
@Getter
public class BusinessException extends RuntimeException {
    private final String errorCode;
    private final String userMessage;
    
    public BusinessException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }
}
```

#### **ResourceNotFoundException**
```java
public class ResourceNotFoundException extends BusinessException {
    private final String resourceType;
    private final String resourceId;
    
    public ResourceNotFoundException(String resourceType, String resourceId) {
        super(
            String.format("%s com ID '%s' não encontrado", resourceType, resourceId),
            String.format("%s não encontrado", resourceType)
        );
    }
}
```

#### **ValidationException**
```java
public class ValidationException extends BusinessException {
    private final List<String> validationErrors;
    
    public ValidationException(List<String> validationErrors) {
        super("Erro de validação", "Por favor, verifique os dados informados");
        this.validationErrors = validationErrors;
    }
}
```

### **3. Estrutura de Resposta de Erro Padronizada**

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
    },
    {
      "field": "email",
      "message": "Email deve ter um formato válido",
      "rejectedValue": "email-invalido"
    }
  ],
  "timestamp": "2024-01-15T10:30:15"
}
```

## 🎨 **Frontend - Melhorias Implementadas**

### **1. Interceptor de API Aprimorado**

#### **Tratamento Inteligente de Erros:**
```typescript
// Response interceptor melhorado
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const errorResponse = error.response?.data
    
    if (errorResponse?.detail) {
      toast.error(errorResponse.detail)
      
      // Handle validation errors with specific field messages
      if (errorResponse.errors && errorResponse.errors.length > 0) {
        errorResponse.errors.forEach((validationError: any) => {
          if (validationError.field && validationError.message) {
            toast.error(`${validationError.field}: ${validationError.message}`, {
              duration: 4000,
            })
          }
        })
      }
    }
    
    return Promise.reject(error)
  }
)
```

### **2. Hook useErrorHandler**

#### **Tratamento Específico por Tipo de Erro:**
```typescript
export function useErrorHandler() {
  const handleError = useCallback((error: any) => {
    switch (error.response?.status) {
      case 400:
        handleBadRequestError(errorResponse)
        break
      case 401:
        handleUnauthorizedError(errorResponse)
        break
      case 403:
        handleForbiddenError(errorResponse)
        break
      case 404:
        handleNotFoundError(errorResponse)
        break
      case 409:
        handleConflictError(errorResponse)
        break
      // ... mais casos
    }
  }, [])
}
```

### **3. Componente ErrorMessage**

#### **Notificações Visuais Aprimoradas:**
```typescript
interface ErrorMessageProps {
  type?: 'error' | 'success' | 'warning' | 'info'
  title?: string
  message: string
  details?: string[]
  onClose?: () => void
  autoClose?: boolean
  duration?: number
}
```

#### **Hook useErrorMessage:**
```typescript
export function useErrorMessage() {
  const [messages, setMessages] = useState<Array<ErrorMessageProps & { id: string }>>([])

  const showError = (message: string, details?: string[], title?: string) => {
    // Implementação para mostrar erros
  }

  const showSuccess = (message: string, title?: string) => {
    // Implementação para mostrar sucessos
  }

  // ... mais métodos
}
```

## 📊 **Mapeamento de Erros por Status HTTP**

### **400 - Bad Request**
- **Validação de Dados**: Campos obrigatórios, formatos inválidos
- **Parâmetros Inválidos**: Tipos incorretos, valores fora do range
- **Requisição Malformada**: JSON inválido, estrutura incorreta

### **401 - Unauthorized**
- **Credenciais Inválidas**: Email/senha incorretos
- **Conta Desabilitada**: Usuário desabilitado pelo admin
- **Conta Bloqueada**: Múltiplas tentativas de login

### **403 - Forbidden**
- **Acesso Negado**: Usuário sem permissão para o recurso
- **Perfil Inadequado**: Vendedor tentando acessar admin

### **404 - Not Found**
- **Recurso Não Encontrado**: ID inexistente
- **Endpoint Não Encontrado**: URL incorreta

### **409 - Conflict**
- **Dados Duplicados**: Email já cadastrado
- **Integridade de Dados**: Violação de constraints

### **422 - Unprocessable Entity**
- **Validação de Negócio**: Regras específicas do domínio
- **Dependências**: Recursos relacionados não encontrados

### **500+ - Server Error**
- **Erro Interno**: Exceções não tratadas
- **Indisponibilidade**: Serviços externos offline

## 🎯 **Exemplos de Uso**

### **Backend - Lançando Exceptions Customizadas**

```java
@Service
public class UsuarioService {
    
    public UsuarioResponse criarUsuario(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(
                "Email já está em uso",
                "Este email já está cadastrado no sistema"
            );
        }
        
        Usuario usuario = usuarioRepository.findById(request.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Usuário", request.getId()));
        
        // ... resto da implementação
    }
}
```

### **Frontend - Usando o Error Handler**

```typescript
function LoginForm() {
  const { handleError } = useErrorHandler()
  const { showError, showSuccess } = useErrorMessage()

  const handleSubmit = async (data: LoginRequest) => {
    try {
      await authService.login(data)
      showSuccess('Login realizado com sucesso!')
    } catch (error) {
      handleError(error)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      {/* Form fields */}
    </form>
  )
}
```

## 🔄 **Fluxo de Tratamento de Erros**

### **1. Backend**
```
Exception lançada → GlobalExceptionHandler → ErrorResponse estruturado → HTTP Response
```

### **2. Frontend**
```
HTTP Error Response → API Interceptor → Toast/ErrorMessage → Usuário vê mensagem amigável
```

### **3. Casos Especiais**
```
401 Unauthorized → Remove token → Redireciona para login
403 Forbidden → Mostra mensagem de permissão
Validation Errors → Mostra erros específicos por campo
```

## 📈 **Benefícios Implementados**

### **✅ Experiência do Usuário**
- **Mensagens Claras**: Erros em linguagem amigável
- **Feedback Específico**: Indicações precisas do que corrigir
- **Notificações Visuais**: Toasts e modais informativos
- **Ações Acionáveis**: Instruções claras sobre próximos passos

### **✅ Desenvolvimento**
- **Debugging Facilitado**: Logs estruturados e detalhados
- **Manutenibilidade**: Código organizado e reutilizável
- **Consistência**: Padrão uniforme de tratamento de erros
- **Extensibilidade**: Fácil adição de novos tipos de erro

### **✅ Segurança**
- **Informações Controladas**: Não exposição de detalhes internos
- **Logs de Auditoria**: Rastreamento de tentativas de erro
- **Sanitização**: Limpeza de dados sensíveis nos logs

## 🧪 **Testes de Validação**

### **Teste 1: Validação de Campos**
```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"email": "email-invalido", "nome": ""}'

# Expected Response:
{
  "type": "https://loteria360.com/errors/validation-failed",
  "title": "Dados Inválidos",
  "status": 400,
  "detail": "Por favor, verifique os dados informados",
  "errors": [
    {
      "field": "email",
      "message": "Email deve ter um formato válido",
      "rejectedValue": "email-invalido"
    },
    {
      "field": "nome",
      "message": "Nome é obrigatório",
      "rejectedValue": ""
    }
  ]
}
```

### **Teste 2: Recurso Não Encontrado**
```bash
curl -X GET http://localhost:8080/api/v1/usuarios/999999 \
  -H "Authorization: Bearer <token>"

# Expected Response:
{
  "type": "https://loteria360.com/errors/resource-not-found",
  "title": "Recurso Não Encontrado",
  "status": 404,
  "detail": "Usuário não encontrado"
}
```

### **Teste 3: Conflito de Dados**
```bash
curl -X POST http://localhost:8080/api/v1/usuarios \
  -H "Content-Type: application/json" \
  -d '{"email": "admin@loteria360.local", "nome": "Teste"}'

# Expected Response:
{
  "type": "https://loteria360.com/errors/business-error",
  "title": "Erro de Negócio",
  "status": 400,
  "detail": "Este email já está cadastrado no sistema"
}
```

## 🚀 **Próximos Passos**

### **Melhorias Futuras**
- [ ] Internacionalização de mensagens de erro
- [ ] Métricas de erros em tempo real
- [ ] Sistema de alertas para administradores
- [ ] Logs estruturados com correlação de IDs
- [ ] Retry automático para erros temporários

### **Monitoramento**
- [ ] Dashboard de erros mais frequentes
- [ ] Alertas para taxas de erro elevadas
- [ ] Análise de padrões de erro por usuário
- [ ] Relatórios de qualidade da API

## 📚 **Documentação de Referência**

### **Backend**
- `GlobalExceptionHandler.java` - Handler principal de exceptions
- `BusinessException.java` - Exception base para regras de negócio
- `ResourceNotFoundException.java` - Exception para recursos não encontrados
- `ValidationException.java` - Exception para erros de validação

### **Frontend**
- `useErrorHandler.tsx` - Hook para tratamento de erros
- `ErrorMessage.tsx` - Componente de notificação de erro
- `api.ts` - Interceptor de API com tratamento de erros

O sistema agora oferece uma experiência de erro muito mais amigável e informativa para os usuários, mantendo a segurança e facilitando o debugging para os desenvolvedores.
