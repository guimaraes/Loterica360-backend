-- Migração V3: Dados de teste de janeiro a setembro de 2025

-- Usuários adicionais
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES
(UUID(), 'João Silva', 'joao@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Maria Santos', 'maria@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Pedro Costa', 'pedro@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Ana Oliveira', 'ana@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'GERENTE', TRUE),
(UUID(), 'Carlos Lima', 'carlos@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE);

-- Clientes
INSERT INTO cliente (id, nome, cpf, telefone, email) VALUES
(UUID(), 'Cliente Teste 1', '12345678901', '11999990001', 'cliente1@email.com'),
(UUID(), 'Cliente Teste 2', '12345678902', '11999990002', 'cliente2@email.com'),
(UUID(), 'Cliente Teste 3', '12345678903', '11999990003', 'cliente3@email.com'),
(UUID(), 'Cliente Teste 4', '12345678904', '11999990004', 'cliente4@email.com'),
(UUID(), 'Cliente Teste 5', '12345678905', '11999990005', 'cliente5@email.com'),
(UUID(), 'Cliente Teste 6', '12345678906', '11999990006', 'cliente6@email.com'),
(UUID(), 'Cliente Teste 7', '12345678907', '11999990007', 'cliente7@email.com'),
(UUID(), 'Cliente Teste 8', '12345678908', '11999990008', 'cliente8@email.com'),
(UUID(), 'Cliente Teste 9', '12345678909', '11999990009', 'cliente9@email.com'),
(UUID(), 'Cliente Teste 10', '12345678910', '11999990010', 'cliente10@email.com');

-- Bolões para Mega-Sena
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2654', 'Bolão Mega-Sena 2654', 100, 25, 4.50, '2025-01-04', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2655', 'Bolão Mega-Sena 2655', 150, 45, 4.50, '2025-01-08', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2656', 'Bolão Mega-Sena 2656', 200, 78, 4.50, '2025-01-11', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2657', 'Bolão Mega-Sena 2657', 120, 32, 4.50, '2025-01-15', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2658', 'Bolão Mega-Sena 2658', 180, 56, 4.50, '2025-01-18', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2659', 'Bolão Mega-Sena 2659', 160, 41, 4.50, '2025-01-22', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2660', 'Bolão Mega-Sena 2660', 140, 28, 4.50, '2025-01-25', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2661', 'Bolão Mega-Sena 2661', 200, 89, 4.50, '2025-01-29', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2662', 'Bolão Mega-Sena 2662', 110, 35, 4.50, '2025-02-01', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2663', 'Bolão Mega-Sena 2663', 170, 62, 4.50, '2025-02-05', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2727', 'Bolão Mega-Sena 2727', 130, 41, 4.50, '2025-09-17', 'ABERTO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2728', 'Bolão Mega-Sena 2728', 170, 65, 4.50, '2025-09-20', 'ABERTO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2729', 'Bolão Mega-Sena 2729', 140, 46, 4.50, '2025-09-24', 'ABERTO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA' LIMIT 1), '2730', 'Bolão Mega-Sena 2730', 210, 89, 4.50, '2025-09-27', 'ABERTO');

-- Bolões para Lotofácil
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2876', 'Bolão Lotofácil 2876', 80, 25, 2.50, '2025-01-06', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2877', 'Bolão Lotofácil 2877', 100, 35, 2.50, '2025-01-09', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2878', 'Bolão Lotofácil 2878', 90, 28, 2.50, '2025-01-13', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2879', 'Bolão Lotofácil 2879', 75, 18, 2.50, '2025-01-16', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2880', 'Bolão Lotofácil 2880', 85, 32, 2.50, '2025-01-20', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2881', 'Bolão Lotofácil 2881', 95, 41, 2.50, '2025-01-23', 'ENCERRADO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2951', 'Bolão Lotofácil 2951', 90, 44, 2.50, '2025-09-25', 'ABERTO'),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO' LIMIT 1), '2952', 'Bolão Lotofácil 2952', 85, 40, 2.50, '2025-09-29', 'ABERTO');

-- Turnos de janeiro a setembro de 2025
INSERT INTO turno (id, usuario_id, caixa_id, valor_inicial, valor_final, status, data_abertura, data_fechamento) VALUES
-- Janeiro 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 2500.00, 'FECHADO', '2025-01-02 08:00:00', '2025-01-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 1800.00, 'FECHADO', '2025-01-03 08:00:00', '2025-01-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 2200.00, 'FECHADO', '2025-01-04 08:00:00', '2025-01-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 1900.00, 'FECHADO', '2025-01-05 08:00:00', '2025-01-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 2800.00, 'FECHADO', '2025-01-06 08:00:00', '2025-01-06 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 1600.00, 'FECHADO', '2025-01-07 08:00:00', '2025-01-07 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 3000.00, 'FECHADO', '2025-01-08 08:00:00', '2025-01-08 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2100.00, 'FECHADO', '2025-01-09 08:00:00', '2025-01-09 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 2400.00, 'FECHADO', '2025-01-10 08:00:00', '2025-01-10 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 1700.00, 'FECHADO', '2025-01-11 08:00:00', '2025-01-11 18:00:00'),
-- Fevereiro 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 3200.00, 'FECHADO', '2025-02-01 08:00:00', '2025-02-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2400.00, 'FECHADO', '2025-02-02 08:00:00', '2025-02-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 2800.00, 'FECHADO', '2025-02-03 08:00:00', '2025-02-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2100.00, 'FECHADO', '2025-02-04 08:00:00', '2025-02-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 3500.00, 'FECHADO', '2025-02-05 08:00:00', '2025-02-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 1900.00, 'FECHADO', '2025-02-06 08:00:00', '2025-02-06 18:00:00'),
-- Março 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 3800.00, 'FECHADO', '2025-03-01 08:00:00', '2025-03-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2600.00, 'FECHADO', '2025-03-02 08:00:00', '2025-03-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3000.00, 'FECHADO', '2025-03-03 08:00:00', '2025-03-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2200.00, 'FECHADO', '2025-03-04 08:00:00', '2025-03-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 4000.00, 'FECHADO', '2025-03-05 08:00:00', '2025-03-05 18:00:00'),
-- Abril 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 4200.00, 'FECHADO', '2025-04-01 08:00:00', '2025-04-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2800.00, 'FECHADO', '2025-04-02 08:00:00', '2025-04-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3200.00, 'FECHADO', '2025-04-03 08:00:00', '2025-04-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2300.00, 'FECHADO', '2025-04-04 08:00:00', '2025-04-04 18:00:00'),
-- Maio 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 4500.00, 'FECHADO', '2025-05-01 08:00:00', '2025-05-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3000.00, 'FECHADO', '2025-05-02 08:00:00', '2025-05-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3400.00, 'FECHADO', '2025-05-03 08:00:00', '2025-05-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2500.00, 'FECHADO', '2025-05-04 08:00:00', '2025-05-04 18:00:00'),
-- Junho 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 4800.00, 'FECHADO', '2025-06-01 08:00:00', '2025-06-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3200.00, 'FECHADO', '2025-06-02 08:00:00', '2025-06-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3600.00, 'FECHADO', '2025-06-03 08:00:00', '2025-06-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2700.00, 'FECHADO', '2025-06-04 08:00:00', '2025-06-04 18:00:00'),
-- Julho 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 5100.00, 'FECHADO', '2025-07-01 08:00:00', '2025-07-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3400.00, 'FECHADO', '2025-07-02 08:00:00', '2025-07-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3800.00, 'FECHADO', '2025-07-03 08:00:00', '2025-07-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2900.00, 'FECHADO', '2025-07-04 08:00:00', '2025-07-04 18:00:00'),
-- Agosto 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 5400.00, 'FECHADO', '2025-08-01 08:00:00', '2025-08-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3600.00, 'FECHADO', '2025-08-02 08:00:00', '2025-08-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 4000.00, 'FECHADO', '2025-08-03 08:00:00', '2025-08-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 3100.00, 'FECHADO', '2025-08-04 08:00:00', '2025-08-04 18:00:00'),
-- Setembro 2025
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 5700.00, 'FECHADO', '2025-09-01 08:00:00', '2025-09-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3800.00, 'FECHADO', '2025-09-02 08:00:00', '2025-09-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 4200.00, 'FECHADO', '2025-09-03 08:00:00', '2025-09-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 3300.00, 'FECHADO', '2025-09-04 08:00:00', '2025-09-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 4800.00, 'FECHADO', '2025-09-05 08:00:00', '2025-09-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 3500.00, 'FECHADO', '2025-09-06 08:00:00', '2025-09-06 18:00:00');

-- Vendas de jogos individuais (amostra representativa)
INSERT INTO venda (id, turno_id, jogo_id, cliente_id, tipo_venda, valor_total, status, data_venda, numeros_jogados) VALUES
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-01-02 09:15:00', '01,05,12,23,35,42'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 2.50, 'CONCLUIDA', '2025-01-02 10:30:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 2.00, 'CONCLUIDA', '2025-01-02 11:45:00', '01,15,30,45,60'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-01-03 09:20:00', '02,08,18,25,33,47'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 2.50, 'CONCLUIDA', '2025-01-03 14:15:00', '03,07,11,16,19,21,22,24,25,26,27,28,29,30,31'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 2.00, 'CONCLUIDA', '2025-01-04 10:00:00', '05,20,35,50,65'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-01-04 15:30:00', '07,14,21,28,35,49'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 2.50, 'CONCLUIDA', '2025-01-05 11:45:00', '01,04,08,12,15,18,20,22,24,25,26,27,28,29,30'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 2.00, 'CONCLUIDA', '2025-01-05 16:20:00', '10,25,40,55,70'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-06 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-01-06 12:10:00', '09,16,24,32,41,56');

-- Vendas de bolões (amostra representativa)
INSERT INTO venda (id, turno_id, bolao_id, cliente_id, tipo_venda, valor_total, status, data_venda, cotas_compradas) VALUES
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2654'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-01-02 09:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2876'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-01-02 10:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2655'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 67.50, 'CONCLUIDA', '2025-01-03 11:00:00', 15),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2877'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 37.50, 'CONCLUIDA', '2025-01-03 14:30:00', 15),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2656'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'BOLAO', 90.00, 'CONCLUIDA', '2025-01-04 10:15:00', 20),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2878'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'BOLAO', 50.00, 'CONCLUIDA', '2025-01-04 15:45:00', 20),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2657'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'BOLAO', 54.00, 'CONCLUIDA', '2025-01-05 12:20:00', 12),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2879'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-01-05 17:10:00', 18),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-06 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2658'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'BOLAO', 81.00, 'CONCLUIDA', '2025-01-06 13:30:00', 18),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-06 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2880'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'BOLAO', 55.00, 'CONCLUIDA', '2025-01-06 16:50:00', 22);

-- Pagamentos para as vendas
INSERT INTO pagamento (id, venda_id, metodo_pagamento, valor, status, data_pagamento) VALUES
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:15:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-01-02 09:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 10:30:00'), 'PIX', 2.50, 'APROVADO', '2025-01-02 10:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 11:45:00'), 'CARTAO_DEBITO', 2.00, 'APROVADO', '2025-01-02 11:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 09:20:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-01-03 09:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 14:15:00'), 'CARTAO_CREDITO', 2.50, 'APROVADO', '2025-01-03 14:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 10:00:00'), 'PIX', 2.00, 'APROVADO', '2025-01-04 10:00:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 15:30:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-01-04 15:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 11:45:00'), 'CARTAO_DEBITO', 2.50, 'APROVADO', '2025-01-05 11:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 16:20:00'), 'PIX', 2.00, 'APROVADO', '2025-01-05 16:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-06 12:10:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-01-06 12:10:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:30:00'), 'DINHEIRO', 45.00, 'APROVADO', '2025-01-02 09:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 10:45:00'), 'PIX', 25.00, 'APROVADO', '2025-01-02 10:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 11:00:00'), 'CARTAO_CREDITO', 67.50, 'APROVADO', '2025-01-03 11:00:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 14:30:00'), 'DINHEIRO', 37.50, 'APROVADO', '2025-01-03 14:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 10:15:00'), 'PIX', 90.00, 'APROVADO', '2025-01-04 10:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 15:45:00'), 'CARTAO_DEBITO', 50.00, 'APROVADO', '2025-01-04 15:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 12:20:00'), 'DINHEIRO', 54.00, 'APROVADO', '2025-01-05 12:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 17:10:00'), 'PIX', 45.00, 'APROVADO', '2025-01-05 17:10:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-06 13:30:00'), 'CARTAO_CREDITO', 81.00, 'APROVADO', '2025-01-06 13:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-06 16:50:00'), 'DINHEIRO', 55.00, 'APROVADO', '2025-01-06 16:50:00');

-- Movimentos de caixa (amostra representativa)
INSERT INTO movimento_caixa (id, turno_id, tipo_movimento, valor, descricao, data_movimento) VALUES
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 4.50, 'Venda Mega-Sena', '2025-01-02 09:15:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 2.50, 'Venda Lotofácil', '2025-01-02 10:30:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 2.00, 'Venda Quina', '2025-01-02 11:45:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 45.00, 'Venda Bolão Mega-Sena', '2025-01-02 09:30:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Lotofácil', '2025-01-02 10:45:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-02 08:00:00'), 'SAIDA', 50.00, 'Sangria para troco', '2025-01-02 14:00:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 4.50, 'Venda Mega-Sena', '2025-01-03 09:20:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 2.50, 'Venda Lotofácil', '2025-01-03 14:15:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 67.50, 'Venda Bolão Mega-Sena', '2025-01-03 11:00:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 37.50, 'Venda Bolão Lotofácil', '2025-01-03 14:30:00'),
(UUID(), (SELECT id FROM turno WHERE data_abertura = '2025-01-03 08:00:00'), 'SAIDA', 30.00, 'Sangria para troco', '2025-01-03 16:00:00');

-- Regras de comissão
INSERT INTO comissao_regra (id, jogo_id, tipo_venda, percentual_comissao, valor_minimo, valor_maximo, ativo) VALUES
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA'), 'JOGO_INDIVIDUAL', 5.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO'), 'JOGO_INDIVIDUAL', 4.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'QUINA'), 'JOGO_INDIVIDUAL', 3.5, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTOMANIA'), 'JOGO_INDIVIDUAL', 4.5, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'DIADESORTE'), 'JOGO_INDIVIDUAL', 4.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'SUPERSETE'), 'JOGO_INDIVIDUAL', 4.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'TIMEMANIA'), 'JOGO_INDIVIDUAL', 4.5, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'DUPLASENA'), 'JOGO_INDIVIDUAL', 4.5, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'FEDERAL'), 'JOGO_INDIVIDUAL', 3.5, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTECA'), 'JOGO_INDIVIDUAL', 4.0, 0.00, 999999.99, TRUE),
(UUID(), NULL, 'BOLAO', 6.0, 0.00, 999999.99, TRUE);

-- Auditoria de algumas vendas importantes
INSERT INTO auditoria (id, tabela_afetada, registro_id, operacao, dados_anteriores, dados_novos, usuario_id, data_operacao) VALUES
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:15:00'), 'INSERT', NULL, '{"valor_total": 4.50, "status": "CONCLUIDA", "tipo_venda": "JOGO_INDIVIDUAL"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:15:00'),
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:30:00'), 'INSERT', NULL, '{"valor_total": 45.00, "status": "CONCLUIDA", "tipo_venda": "BOLAO"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:30:00'),
(UUID(), 'pagamento', (SELECT id FROM pagamento WHERE data_pagamento = '2025-01-02 09:15:00'), 'INSERT', NULL, '{"valor": 4.50, "status": "APROVADO", "metodo_pagamento": "DINHEIRO"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:15:00'),
(UUID(), 'movimento_caixa', (SELECT id FROM movimento_caixa WHERE data_movimento = '2025-01-02 09:15:00'), 'INSERT', NULL, '{"valor": 4.50, "tipo_movimento": "ENTRADA", "descricao": "Venda Mega-Sena"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:15:00');
