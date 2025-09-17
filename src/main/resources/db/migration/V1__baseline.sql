-- Tabela de usuários
CREATE TABLE usuario (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha_hash VARCHAR(255) NOT NULL,
    papel ENUM('ADMIN', 'GERENTE', 'VENDEDOR') NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de jogos
CREATE TABLE jogo (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL(10,2) NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de clientes
CREATE TABLE cliente (
    id VARCHAR(36) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100) UNIQUE,
    consentimento_lgpd BOOLEAN DEFAULT FALSE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de caixas
CREATE TABLE caixa (
    id VARCHAR(36) PRIMARY KEY,
    numero INT UNIQUE NOT NULL,
    descricao VARCHAR(100),
    ativo BOOLEAN DEFAULT TRUE,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de vendas por caixa (quantidades de jogos vendidos)
CREATE TABLE venda_caixa (
    id VARCHAR(36) PRIMARY KEY,
    caixa_id VARCHAR(36) NOT NULL,
    jogo_id VARCHAR(36) NOT NULL,
    quantidade INT NOT NULL DEFAULT 0,
    valor_total DECIMAL(12,2) NOT NULL DEFAULT 0,
    data_venda DATE NOT NULL,
    usuario_id VARCHAR(36) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (caixa_id) REFERENCES caixa(id),
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    UNIQUE KEY uk_venda_caixa_jogo_data (caixa_id, jogo_id, data_venda)
);

-- Tabela de contagem de cédulas e moedas por caixa
CREATE TABLE contagem_caixa (
    id VARCHAR(36) PRIMARY KEY,
    caixa_id VARCHAR(36) NOT NULL,
    data_contagem DATE NOT NULL,
    usuario_id VARCHAR(36) NOT NULL,
    
    -- Notas
    notas_200 INT DEFAULT 0,
    notas_100 INT DEFAULT 0,
    notas_50 INT DEFAULT 0,
    notas_20 INT DEFAULT 0,
    notas_10 INT DEFAULT 0,
    notas_5 INT DEFAULT 0,
    notas_2 INT DEFAULT 0,
    
    -- Moedas
    moedas_1 INT DEFAULT 0,
    moedas_050 INT DEFAULT 0,
    moedas_025 INT DEFAULT 0,
    moedas_010 INT DEFAULT 0,
    moedas_005 INT DEFAULT 0,
    
    -- Totais calculados
    total_notas DECIMAL(12,2) DEFAULT 0,
    total_moedas DECIMAL(12,2) DEFAULT 0,
    total_geral DECIMAL(12,2) DEFAULT 0,
    
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (caixa_id) REFERENCES caixa(id),
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    UNIQUE KEY uk_contagem_caixa_data (caixa_id, data_contagem)
);

-- Tabela de auditoria
CREATE TABLE auditoria (
    id VARCHAR(36) PRIMARY KEY,
    tabela VARCHAR(50) NOT NULL,
    registro_id VARCHAR(36) NOT NULL,
    operacao ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    usuario_id VARCHAR(36),
    dados_anteriores JSON,
    dados_novos JSON,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Índices para performance
CREATE INDEX idx_usuario_email ON usuario(email);
CREATE INDEX idx_usuario_papel ON usuario(papel);
CREATE INDEX idx_cliente_cpf ON cliente(cpf);
CREATE INDEX idx_cliente_email ON cliente(email);
CREATE INDEX idx_venda_caixa_data ON venda_caixa(data_venda);
CREATE INDEX idx_venda_caixa_caixa ON venda_caixa(caixa_id);
CREATE INDEX idx_venda_caixa_jogo ON venda_caixa(jogo_id);
CREATE INDEX idx_contagem_caixa_data ON contagem_caixa(data_contagem);
CREATE INDEX idx_contagem_caixa_caixa ON contagem_caixa(caixa_id);
CREATE INDEX idx_auditoria_tabela ON auditoria(tabela);
CREATE INDEX idx_auditoria_registro ON auditoria(registro_id);
CREATE INDEX idx_auditoria_timestamp ON auditoria(timestamp);