-- Enable UUID extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create usuarios table
CREATE TABLE usuarios (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL,
    papel VARCHAR(20) NOT NULL CHECK (papel IN ('ADMIN', 'GERENTE', 'VENDEDOR', 'AUDITOR')),
    ativo BOOLEAN NOT NULL DEFAULT true,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create turnos table
CREATE TABLE turnos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    usuario_id UUID NOT NULL REFERENCES usuarios(id),
    caixa_id VARCHAR(50) NOT NULL,
    aberto_em TIMESTAMP NOT NULL,
    fechado_em TIMESTAMP,
    valor_inicial DECIMAL(10,2) NOT NULL,
    valor_fechamento DECIMAL(10,2),
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTO' CHECK (status IN ('ABERTO', 'FECHADO')),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create jogos table
CREATE TABLE jogos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    preco_base DECIMAL(10,2) NOT NULL,
    regras_json TEXT,
    ativo BOOLEAN NOT NULL DEFAULT true,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create boloes table
CREATE TABLE boloes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    jogo_id UUID NOT NULL REFERENCES jogos(id),
    concurso VARCHAR(20) NOT NULL,
    descricao VARCHAR(255),
    cotas_totais INTEGER NOT NULL,
    cotas_vendidas INTEGER NOT NULL DEFAULT 0,
    valor_cota DECIMAL(10,2) NOT NULL,
    data_sorteio TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTO' CHECK (status IN ('ABERTO', 'ENCERRADO', 'CANCELADO')),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT check_cotas CHECK (cotas_vendidas <= cotas_totais)
);

-- Create clientes table
CREATE TABLE clientes (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(255),
    consentimento_lgpd BOOLEAN NOT NULL DEFAULT false,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create vendas table
CREATE TABLE vendas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    turno_id UUID NOT NULL REFERENCES turnos(id),
    usuario_id UUID NOT NULL REFERENCES usuarios(id),
    tipo VARCHAR(10) NOT NULL CHECK (tipo IN ('JOGO', 'BOLAO')),
    jogo_id UUID REFERENCES jogos(id),
    bolao_id UUID REFERENCES boloes(id),
    quantidade_apostas INTEGER,
    cotas_vendidas INTEGER,
    valor_bruto DECIMAL(10,2) NOT NULL,
    desconto DECIMAL(10,2) DEFAULT 0,
    acrescimo DECIMAL(10,2) DEFAULT 0,
    valor_liquido DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ATIVA' CHECK (status IN ('ATIVA', 'CANCELADA')),
    motivo_cancelamento VARCHAR(500),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create pagamentos table
CREATE TABLE pagamentos (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    venda_id UUID NOT NULL REFERENCES vendas(id),
    metodo VARCHAR(20) NOT NULL CHECK (metodo IN ('DINHEIRO', 'PIX', 'CARTAO_DEBITO', 'CARTAO_CREDITO')),
    valor DECIMAL(10,2) NOT NULL,
    nsu VARCHAR(50),
    tid VARCHAR(50),
    referencia VARCHAR(100),
    status VARCHAR(20) NOT NULL DEFAULT 'APROVADO' CHECK (status IN ('APROVADO', 'PENDENTE', 'ESTORNADO')),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create movimentos_caixa table
CREATE TABLE movimentos_caixa (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    turno_id UUID NOT NULL REFERENCES turnos(id),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('SANGRIA', 'SUPRIMENTO')),
    valor DECIMAL(10,2) NOT NULL,
    observacao VARCHAR(500),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create auditoria table
CREATE TABLE auditoria (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tabela VARCHAR(50) NOT NULL,
    registro_id UUID NOT NULL,
    acao VARCHAR(20) NOT NULL CHECK (acao IN ('INSERT', 'UPDATE', 'DELETE')),
    antes_json TEXT,
    depois_json TEXT,
    usuario_id UUID REFERENCES usuarios(id),
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create comissao_regras table
CREATE TABLE comissao_regras (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    escopo VARCHAR(20) NOT NULL CHECK (escopo IN ('JOGO', 'BOLAO', 'VENDEDOR')),
    referencia_id UUID,
    percentual DECIMAL(5,4) NOT NULL,
    vigente_de TIMESTAMP NOT NULL,
    vigente_ate TIMESTAMP,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes
CREATE INDEX idx_turnos_usuario_id ON turnos(usuario_id);
CREATE INDEX idx_turnos_caixa_id ON turnos(caixa_id);
CREATE INDEX idx_turnos_status ON turnos(status);
CREATE INDEX idx_vendas_turno_id ON vendas(turno_id);
CREATE INDEX idx_vendas_usuario_id ON vendas(usuario_id);
CREATE INDEX idx_vendas_criado_em ON vendas(criado_em);
CREATE INDEX idx_pagamentos_venda_id ON pagamentos(venda_id);
CREATE INDEX idx_boloes_jogo_id ON boloes(jogo_id);
CREATE INDEX idx_boloes_status ON boloes(status);
CREATE INDEX idx_auditoria_tabela_registro ON auditoria(tabela, registro_id);
CREATE INDEX idx_auditoria_criado_em ON auditoria(criado_em);

-- Create unique constraints for NSU/TID when provided
CREATE UNIQUE INDEX idx_pagamentos_nsu ON pagamentos(nsu) WHERE nsu IS NOT NULL;
CREATE UNIQUE INDEX idx_pagamentos_tid ON pagamentos(tid) WHERE tid IS NOT NULL;

-- Insert default admin user (password: admin123)
INSERT INTO usuarios (nome, email, senha_hash, papel) VALUES 
('Administrador', 'admin@loteria360.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN');

-- Insert sample games
INSERT INTO jogos (nome, codigo, preco_base, regras_json) VALUES 
('Mega Sena', 'MEGA', 4.50, '{"numeros": 6, "faixa": [1, 60], "premios": ["Sena", "Quina", "Quadra"]}'),
('Lotofácil', 'LOTO', 2.50, '{"numeros": 15, "faixa": [1, 25], "premios": ["15 acertos", "14 acertos", "13 acertos"]}'),
('Quina', 'QUINA', 2.00, '{"numeros": 5, "faixa": [1, 80], "premios": ["Quina", "Quadra", "Terno"]}');

-- Insert sample bolao
INSERT INTO boloes (jogo_id, concurso, descricao, cotas_totais, valor_cota, data_sorteio) 
SELECT j.id, '2024-001', 'Bolão Mega Sena 2024-001', 100, 2.00, CURRENT_TIMESTAMP + INTERVAL '7 days'
FROM jogos j WHERE j.codigo = 'MEGA';
