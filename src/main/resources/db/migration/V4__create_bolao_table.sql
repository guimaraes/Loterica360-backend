-- Tabela de bolões
CREATE TABLE bolao (
    id VARCHAR(36) PRIMARY KEY,
    jogo_id VARCHAR(36) NOT NULL,
    concurso VARCHAR(20) NOT NULL,
    descricao TEXT,
    cotas_totais INT NOT NULL,
    cotas_vendidas INT NOT NULL DEFAULT 0,
    cotas_disponiveis INT NOT NULL,
    valor_cota DECIMAL(10,2) NOT NULL,
    data_sorteio DATE NOT NULL,
    status ENUM('ABERTO', 'ENCERRADO', 'CANCELADO') NOT NULL DEFAULT 'ABERTO',
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (jogo_id) REFERENCES jogo(id),
    UNIQUE KEY uk_bolao_jogo_concurso (jogo_id, concurso)
);

-- Índices para melhorar performance
CREATE INDEX idx_bolao_jogo ON bolao(jogo_id);
CREATE INDEX idx_bolao_status ON bolao(status);
CREATE INDEX idx_bolao_data_sorteio ON bolao(data_sorteio);
