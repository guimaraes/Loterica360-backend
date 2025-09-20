-- Inserir usuário administrador
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES
(UUID(), 'Administrador', 'admin@loteria360.local', '$2a$10$anmoVnCrMaujZO1NAkQAoObTAGHFQjkD/RzPCErGSYJcgU7TN3D/i', 'ADMIN', TRUE);

-- Inserir jogos da loteria
INSERT INTO jogo (id, nome, descricao, preco, ativo) VALUES
(UUID(), 'Mega Sena', 'Loteria com 60 números, apostas de 6 a 15 números', 4.50, TRUE),
(UUID(), 'Quina', 'Loteria com 80 números, apostas de 5 a 15 números', 2.00, TRUE),
(UUID(), 'Lotomania', 'Loteria com 100 números, apostas de 50 números', 2.50, TRUE),
(UUID(), 'Lotofácil', 'Loteria com 25 números, apostas de 15 a 18 números', 2.50, TRUE),
(UUID(), 'Dupla Sena', 'Loteria com 50 números, apostas de 6 números', 2.50, TRUE),
(UUID(), 'Timemania', 'Loteria com 80 números, apostas de 10 números', 3.00, TRUE),
(UUID(), 'Dia de Sorte', 'Loteria com 31 números, apostas de 7 números', 2.50, TRUE),
(UUID(), 'Super Sete', 'Loteria com 10 números, apostas de 7 números', 1.50, TRUE);

-- Inserir caixas padrão
INSERT INTO caixa (id, numero, descricao, ativo) VALUES
(UUID(), 1, 'Caixa Principal', TRUE),
(UUID(), 2, 'Caixa Secundária', TRUE),
(UUID(), 3, 'Caixa Terciária', TRUE),
(UUID(), 4, 'Caixa Quaternária', TRUE),
(UUID(), 5, 'Caixa Quinária', TRUE);