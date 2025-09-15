-- Seed data for Loteria360
-- Usuário admin padrão e jogos da Loteria Federal

-- Usuário administrador padrão
-- Senha: admin (BCrypt hash)
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES 
(UUID(), 'Administrador', 'admin@loteria360.local', '$2a$10$zC1Wdfapz1AKzv1yNtokdOEOjcT625fgL2Xpe40qP2SJ2mJZ/Yf9C', 'ADMIN', TRUE);

-- Jogos da Loteria Federal
INSERT INTO jogo (id, nome, codigo, preco_base, regras_json, ativo) VALUES 
(UUID(), 'Mega-Sena', 'MEGA', 4.50, '{"minimo": 6, "maximo": 15, "numeros": 60, "descricao": "Escolha de 6 a 15 números entre 1 e 60"}', TRUE),
(UUID(), 'Lotofácil', 'LOTO', 2.50, '{"minimo": 15, "maximo": 20, "numeros": 25, "descricao": "Escolha de 15 a 20 números entre 1 e 25"}', TRUE),
(UUID(), 'Quina', 'QUINA', 2.00, '{"minimo": 5, "maximo": 15, "numeros": 80, "descricao": "Escolha de 5 a 15 números entre 1 e 80"}', TRUE),
(UUID(), 'Lotomania', 'LOTOMANIA', 3.00, '{"minimo": 50, "maximo": 50, "numeros": 100, "descricao": "Escolha 50 números entre 1 e 100"}', TRUE),
(UUID(), 'Dia de Sorte', 'DIADESORTE', 2.50, '{"minimo": 7, "maximo": 15, "numeros": 31, "descricao": "Escolha de 7 a 15 números entre 1 e 31"}', TRUE),
(UUID(), 'Super Sete', 'SUPERSETE', 2.50, '{"minimo": 7, "maximo": 7, "numeros": 7, "descricao": "Escolha 7 números entre 1 e 7"}', TRUE),
(UUID(), 'Timemania', 'TIMEMANIA', 3.50, '{"minimo": 10, "maximo": 10, "numeros": 80, "descricao": "Escolha 10 números entre 1 e 80"}', TRUE),
(UUID(), 'Dupla Sena', 'DUPLASENA', 3.00, '{"minimo": 6, "maximo": 15, "numeros": 50, "descricao": "Escolha de 6 a 15 números entre 1 e 50"}', TRUE),
(UUID(), 'Federal', 'FEDERAL', 2.00, '{"minimo": 5, "maximo": 5, "numeros": 25, "descricao": "Escolha 5 números entre 1 e 25"}', TRUE),
(UUID(), 'Loteca', 'LOTECA', 2.50, '{"minimo": 14, "maximo": 14, "numeros": 14, "descricao": "Escolha o resultado de 14 jogos de futebol"}', TRUE);
