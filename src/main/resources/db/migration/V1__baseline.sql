-- Loteria360 Database Schema
-- Baseline migration with all tables

-- Usuários do sistema
CREATE TABLE usuario (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    papel ENUM('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR') NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_usuario_email (email),
    INDEX idx_usuario_papel (papel),
    INDEX idx_usuario_ativo (ativo)
);

-- Turnos de trabalho
CREATE TABLE turno (
    id VARCHAR(36) PRIMARY KEY,
    usuario_id VARCHAR(36) NOT NULL,
    caixa_id VARCHAR(50) NOT NULL,
    data_abertura TIMESTAMP NOT NULL,
    data_fechamento TIMESTAMP NULL,
    valor_inicial DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    valor_final DECIMAL(12,2) NULL,
    status ENUM('ABERTO', 'FECHADO') NOT NULL DEFAULT 'ABERTO',
    
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    INDEX idx_turno_usuario (usuario_id),
    INDEX idx_turno_status (status),
    INDEX idx_turno_data_abertura (data_abertura)
);

-- Jogos disponíveis
CREATE TABLE jogo (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    preco_base DECIMAL(10,2) NOT NULL,
    regras_json JSON,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    
    INDEX idx_jogo_codigo (codigo),
    INDEX idx_jogo_ativo (ativo)
);

-- Bolões de jogos
CREATE TABLE bolao (
    id VARCHAR(36) PRIMARY KEY,
    jogo_id VARCHAR(36) NOT NULL,
    concurso VARCHAR(20) NOT NULL,
    descricao TEXT,
    cotas_totais INT NOT NULL,
    cotas_vendidas INT NOT NULL DEFAULT 0,
    valor_cota DECIMAL(10,2) NOT NULL,
    data_sorteio DATE NOT NULL,
    status ENUM('ABERTO', 'ENCERRADO', 'CANCELADO') NOT NULL DEFAULT 'ABERTO',
    
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    INDEX idx_bolao_jogo (jogo_id),
    INDEX idx_bolao_concurso (concurso),
    INDEX idx_bolao_data_sorteio (data_sorteio),
    INDEX idx_bolao_status (status),
    CONSTRAINT chk_bolao_cotas CHECK (cotas_vendidas <= cotas_totais)
);

-- Clientes (opcional)
CREATE TABLE cliente (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(255),
    consentimento_lgpd BOOLEAN NOT NULL DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_cliente_cpf (cpf),
    INDEX idx_cliente_email (email)
);

-- Vendas realizadas
CREATE TABLE venda (
    id VARCHAR(36) PRIMARY KEY,
    turno_id VARCHAR(36) NOT NULL,
    jogo_id VARCHAR(36) NULL,
    bolao_id VARCHAR(36) NULL,
    cliente_id VARCHAR(36) NULL,
    tipo_venda ENUM('JOGO_INDIVIDUAL', 'BOLAO') NOT NULL,
    valor_total DECIMAL(12,2) NOT NULL,
    status ENUM('CONCLUIDA', 'CANCELADA') NOT NULL DEFAULT 'CONCLUIDA',
    data_venda TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    numeros_jogados VARCHAR(255) NULL,
    cotas_compradas INT NULL,
    
    FOREIGN KEY (turno_id) REFERENCES turno(id),
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    FOREIGN KEY (bolao_id) REFERENCES bolao(id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id),
    INDEX idx_venda_turno (turno_id),
    INDEX idx_venda_jogo (jogo_id),
    INDEX idx_venda_bolao (bolao_id),
    INDEX idx_venda_cliente (cliente_id),
    INDEX idx_venda_data_venda (data_venda),
    INDEX idx_venda_status (status),
    INDEX idx_venda_tipo (tipo_venda)
);

-- Pagamentos das vendas
CREATE TABLE pagamento (
    id VARCHAR(36) PRIMARY KEY,
    venda_id VARCHAR(36) NOT NULL,
    metodo_pagamento ENUM('DINHEIRO', 'PIX', 'CARTAO_DEBITO', 'CARTAO_CREDITO') NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    status ENUM('APROVADO', 'PENDENTE', 'ESTORNADO') NOT NULL DEFAULT 'APROVADO',
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (venda_id) REFERENCES venda(id),
    INDEX idx_pagamento_venda (venda_id),
    INDEX idx_pagamento_metodo (metodo_pagamento),
    INDEX idx_pagamento_status (status)
);

-- Movimentos de caixa (sangria/suprimento)
CREATE TABLE movimento_caixa (
    id VARCHAR(36) PRIMARY KEY,
    turno_id VARCHAR(36) NOT NULL,
    tipo_movimento ENUM('ENTRADA', 'SAIDA') NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    descricao VARCHAR(200),
    data_movimento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (turno_id) REFERENCES turno(id),
    INDEX idx_movimento_turno (turno_id),
    INDEX idx_movimento_tipo (tipo_movimento),
    INDEX idx_movimento_data (data_movimento)
);

-- Auditoria de mudanças
CREATE TABLE auditoria (
    id VARCHAR(36) PRIMARY KEY,
    tabela_afetada VARCHAR(50) NOT NULL,
    registro_id VARCHAR(36) NOT NULL,
    operacao ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    dados_anteriores JSON NULL,
    dados_novos JSON NULL,
    usuario_id VARCHAR(36) NOT NULL,
    data_operacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    INDEX idx_auditoria_tabela (tabela_afetada),
    INDEX idx_auditoria_registro (registro_id),
    INDEX idx_auditoria_usuario (usuario_id),
    INDEX idx_auditoria_data (data_operacao)
);

-- Regras de comissão
CREATE TABLE comissao_regra (
    id VARCHAR(36) PRIMARY KEY,
    jogo_id VARCHAR(36) NULL,
    tipo_venda ENUM('JOGO_INDIVIDUAL', 'BOLAO') NOT NULL,
    percentual_comissao DECIMAL(5,2) NOT NULL,
    valor_minimo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    valor_maximo DECIMAL(12,2) NOT NULL DEFAULT 999999.99,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    INDEX idx_comissao_jogo (jogo_id),
    INDEX idx_comissao_tipo_venda (tipo_venda),
    INDEX idx_comissao_ativo (ativo)
);
