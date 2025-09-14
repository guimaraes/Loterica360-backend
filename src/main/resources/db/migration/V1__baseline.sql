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
    aberto_em TIMESTAMP NOT NULL,
    fechado_em TIMESTAMP NULL,
    valor_inicial DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    valor_fechamento DECIMAL(12,2) NULL,
    status ENUM('ABERTO', 'FECHADO') NOT NULL DEFAULT 'ABERTO',
    
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    INDEX idx_turno_usuario (usuario_id),
    INDEX idx_turno_status (status),
    INDEX idx_turno_aberto_em (aberto_em)
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

-- Vendas realizadas
CREATE TABLE venda (
    id VARCHAR(36) PRIMARY KEY,
    turno_id VARCHAR(36) NOT NULL,
    usuario_id VARCHAR(36) NOT NULL,
    tipo ENUM('JOGO', 'BOLAO') NOT NULL,
    jogo_id CHAR(36) NULL,
    bolao_id CHAR(36) NULL,
    quantidade_apostas INT NULL,
    cotas_vendidas INT NULL,
    valor_bruto DECIMAL(12,2) NOT NULL,
    desconto DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    acrescimo DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    valor_liquido DECIMAL(12,2) NOT NULL,
    status ENUM('ATIVA', 'CANCELADA') NOT NULL DEFAULT 'ATIVA',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    motivo_cancelamento VARCHAR(200) NULL,
    
    FOREIGN KEY (turno_id) REFERENCES turno(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    FOREIGN KEY (bolao_id) REFERENCES bolao(id),
    INDEX idx_venda_turno (turno_id),
    INDEX idx_venda_usuario (usuario_id),
    INDEX idx_venda_bolao (bolao_id),
    INDEX idx_venda_criado_em (criado_em),
    INDEX idx_venda_status (status),
    INDEX idx_venda_tipo (tipo)
);

-- Pagamentos das vendas
CREATE TABLE pagamento (
    id VARCHAR(36) PRIMARY KEY,
    venda_id VARCHAR(36) NOT NULL,
    metodo ENUM('DINHEIRO', 'PIX', 'CARTAO_DEBITO', 'CARTAO_CREDITO') NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    nsu VARCHAR(60) NULL,
    tid VARCHAR(60) NULL,
    referencia VARCHAR(100) NULL,
    status ENUM('APROVADO', 'PENDENTE', 'ESTORNADO') NOT NULL DEFAULT 'APROVADO',
    
    FOREIGN KEY (venda_id) REFERENCES venda(id),
    INDEX idx_pagamento_venda (venda_id),
    INDEX idx_pagamento_metodo (metodo),
    INDEX idx_pagamento_status (status)
);

-- Movimentos de caixa (sangria/suprimento)
CREATE TABLE movimento_caixa (
    id VARCHAR(36) PRIMARY KEY,
    turno_id VARCHAR(36) NOT NULL,
    tipo ENUM('SANGRIA', 'SUPRIMENTO') NOT NULL,
    valor DECIMAL(12,2) NOT NULL,
    observacao VARCHAR(200),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (turno_id) REFERENCES turno(id),
    INDEX idx_movimento_turno (turno_id),
    INDEX idx_movimento_tipo (tipo),
    INDEX idx_movimento_criado_em (criado_em)
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

-- Auditoria de mudanças
CREATE TABLE auditoria (
    id VARCHAR(36) PRIMARY KEY,
    tabela VARCHAR(50) NOT NULL,
    registro_id VARCHAR(36) NOT NULL,
    acao ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    antes_json JSON NULL,
    depois_json JSON NULL,
    usuario_id VARCHAR(36) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    INDEX idx_auditoria_tabela (tabela),
    INDEX idx_auditoria_registro (registro_id),
    INDEX idx_auditoria_usuario (usuario_id),
    INDEX idx_auditoria_criado_em (criado_em)
);

-- Regras de comissão
CREATE TABLE comissao_regra (
    id VARCHAR(36) PRIMARY KEY,
    escopo ENUM('JOGO', 'BOLAO', 'VENDEDOR') NOT NULL,
    referencia_id VARCHAR(36) NOT NULL,
    percentual DECIMAL(5,2) NOT NULL,
    vigente_de DATE NOT NULL,
    vigente_ate DATE NULL,
    
    INDEX idx_comissao_escopo (escopo),
    INDEX idx_comissao_referencia (referencia_id),
    INDEX idx_comissao_vigente_de (vigente_de),
    INDEX idx_comissao_vigente_ate (vigente_ate)
);
