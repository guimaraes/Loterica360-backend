-- Seed data for Loteria360
-- Usuário admin padrão e dados iniciais

-- Senha: admin (BCrypt hash)
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES 
(UUID(), 'Administrador', 'admin@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', TRUE);

-- Jogos básicos
INSERT INTO jogo (id, nome, codigo, preco_base, regras_json, ativo) VALUES 
(UUID(), 'Mega-Sena', 'MEGA', 4.50, '{"minimo": 6, "maximo": 15, "numeros": 60, "descricao": "Escolha de 6 a 15 números entre 1 e 60"}', TRUE),
(UUID(), 'Lotofácil', 'LOTO', 2.50, '{"minimo": 15, "maximo": 20, "numeros": 25, "descricao": "Escolha de 15 a 20 números entre 1 e 25"}', TRUE),
(UUID(), 'Quina', 'QUINA', 2.00, '{"minimo": 5, "maximo": 15, "numeros": 80, "descricao": "Escolha de 5 a 15 números entre 1 e 80"}', TRUE);

-- Bolão de exemplo
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) 
SELECT 
    UUID(),
    (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1),
    '2695',
    'Bolão Mega-Sena Concurso 2695',
    20,
    0,
    10.00,
    DATE_ADD(CURDATE(), INTERVAL 3 DAY),
    'ABERTO';
