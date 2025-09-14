-- Migração V4: Dados de vendas de janeiro a setembro de 2025
-- Volume massivo de dados para testes e relatórios

-- Usuários adicionais (vendedores e gerentes)
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES
(UUID(), 'João Silva', 'joao@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Maria Santos', 'maria@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Pedro Costa', 'pedro@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE),
(UUID(), 'Ana Oliveira', 'ana@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'GERENTE', TRUE),
(UUID(), 'Carlos Lima', 'carlos@loteria360.local', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'VENDEDOR', TRUE);

-- Clientes
INSERT INTO cliente (id, nome, cpf, telefone, email, ativo) VALUES
(UUID(), 'Roberto Alves', '12345678901', '11999990001', 'roberto@email.com', TRUE),
(UUID(), 'Fernanda Lima', '12345678902', '11999990002', 'fernanda@email.com', TRUE),
(UUID(), 'Marcos Silva', '12345678903', '11999990003', 'marcos@email.com', TRUE),
(UUID(), 'Juliana Costa', '12345678904', '11999990004', 'juliana@email.com', TRUE),
(UUID(), 'Ricardo Santos', '12345678905', '11999990005', 'ricardo@email.com', TRUE),
(UUID(), 'Patricia Oliveira', '12345678906', '11999990006', 'patricia@email.com', TRUE),
(UUID(), 'Antonio Pereira', '12345678907', '11999990007', 'antonio@email.com', TRUE),
(UUID(), 'Lucia Ferreira', '12345678908', '11999990008', 'lucia@email.com', TRUE),
(UUID(), 'Paulo Rodrigues', '12345678909', '11999990009', 'paulo@email.com', TRUE),
(UUID(), 'Sandra Martins', '12345678910', '11999990010', 'sandra@email.com', TRUE);

-- Turnos de janeiro a setembro de 2025
INSERT INTO turno (id, usuario_id, caixa_id, valor_inicial, valor_final, status, data_abertura, data_fechamento) VALUES
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
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 3200.00, 'FECHADO', '2025-02-01 08:00:00', '2025-02-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2400.00, 'FECHADO', '2025-02-02 08:00:00', '2025-02-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 2800.00, 'FECHADO', '2025-02-03 08:00:00', '2025-02-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2100.00, 'FECHADO', '2025-02-04 08:00:00', '2025-02-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 3500.00, 'FECHADO', '2025-02-05 08:00:00', '2025-02-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 1900.00, 'FECHADO', '2025-03-01 08:00:00', '2025-03-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 3800.00, 'FECHADO', '2025-03-02 08:00:00', '2025-03-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2600.00, 'FECHADO', '2025-03-03 08:00:00', '2025-03-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3000.00, 'FECHADO', '2025-03-04 08:00:00', '2025-03-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2200.00, 'FECHADO', '2025-03-05 08:00:00', '2025-03-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 4000.00, 'FECHADO', '2025-04-01 08:00:00', '2025-04-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 2300.00, 'FECHADO', '2025-04-02 08:00:00', '2025-04-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 4200.00, 'FECHADO', '2025-04-03 08:00:00', '2025-04-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 2800.00, 'FECHADO', '2025-04-04 08:00:00', '2025-04-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3200.00, 'FECHADO', '2025-04-05 08:00:00', '2025-04-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2500.00, 'FECHADO', '2025-05-01 08:00:00', '2025-05-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 4500.00, 'FECHADO', '2025-05-02 08:00:00', '2025-05-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 2700.00, 'FECHADO', '2025-05-03 08:00:00', '2025-05-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 4800.00, 'FECHADO', '2025-05-04 08:00:00', '2025-05-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3000.00, 'FECHADO', '2025-05-05 08:00:00', '2025-05-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3600.00, 'FECHADO', '2025-06-01 08:00:00', '2025-06-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 2900.00, 'FECHADO', '2025-06-02 08:00:00', '2025-06-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 5000.00, 'FECHADO', '2025-06-03 08:00:00', '2025-06-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 3100.00, 'FECHADO', '2025-06-04 08:00:00', '2025-06-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 5200.00, 'FECHADO', '2025-06-05 08:00:00', '2025-06-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3300.00, 'FECHADO', '2025-07-01 08:00:00', '2025-07-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 3800.00, 'FECHADO', '2025-07-02 08:00:00', '2025-07-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 3200.00, 'FECHADO', '2025-07-03 08:00:00', '2025-07-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 5500.00, 'FECHADO', '2025-07-04 08:00:00', '2025-07-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 3400.00, 'FECHADO', '2025-07-05 08:00:00', '2025-07-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 5800.00, 'FECHADO', '2025-08-01 08:00:00', '2025-08-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 3600.00, 'FECHADO', '2025-08-02 08:00:00', '2025-08-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 4200.00, 'FECHADO', '2025-08-03 08:00:00', '2025-08-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 3500.00, 'FECHADO', '2025-08-04 08:00:00', '2025-08-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'ana@loteria360.local'), 'CAIXA-001', 100.00, 6000.00, 'FECHADO', '2025-08-05 08:00:00', '2025-08-05 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'carlos@loteria360.local'), 'CAIXA-002', 100.00, 3800.00, 'FECHADO', '2025-09-01 08:00:00', '2025-09-01 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), 'CAIXA-001', 100.00, 6200.00, 'FECHADO', '2025-09-02 08:00:00', '2025-09-02 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'joao@loteria360.local'), 'CAIXA-002', 100.00, 4000.00, 'FECHADO', '2025-09-03 08:00:00', '2025-09-03 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'maria@loteria360.local'), 'CAIXA-001', 100.00, 4600.00, 'FECHADO', '2025-09-04 08:00:00', '2025-09-04 18:00:00'),
(UUID(), (SELECT id FROM usuario WHERE email = 'pedro@loteria360.local'), 'CAIXA-002', 100.00, 3900.00, 'FECHADO', '2025-09-05 08:00:00', '2025-09-05 18:00:00');

-- Vendas de jogos individuais - Janeiro a Setembro 2025
INSERT INTO venda (id, turno_id, jogo_id, cliente_id, tipo_venda, valor_total, status, data_venda, numeros_jogados) VALUES
-- Janeiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-01-02 09:15:00', '01,05,12,23,35,42'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 2.50, 'CONCLUIDA', '2025-01-02 10:30:00', '01,03,05,07,09,11,13,15,17,19,21,23,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 2.00, 'CONCLUIDA', '2025-01-03 11:45:00', '02,08,15,27,34'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 9.00, 'CONCLUIDA', '2025-01-04 14:20:00', '03,07,14,21,28,35,42,49,56'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 5.00, 'CONCLUIDA', '2025-01-05 16:10:00', '02,04,06,08,10,12,14,16,18,20,22,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-06 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 4.00, 'CONCLUIDA', '2025-01-06 13:30:00', '01,05,12,18,25,32,39'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-07 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 13.50, 'CONCLUIDA', '2025-01-07 15:45:00', '04,09,16,23,30,37,44,51,58'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-08 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 7.50, 'CONCLUIDA', '2025-01-08 12:15:00', '01,03,05,07,09,11,13,15,17,19,21,23,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-09 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 6.00, 'CONCLUIDA', '2025-01-09 17:20:00', '03,07,14,21,28,35,42,49'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-10 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 18.00, 'CONCLUIDA', '2025-01-10 14:35:00', '02,06,12,18,24,30,36,42,48,54,60'),

-- Fevereiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 4.50, 'CONCLUIDA', '2025-02-01 09:15:00', '01,05,12,23,35,42'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-02-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 2.50, 'CONCLUIDA', '2025-02-02 10:30:00', '01,03,05,07,09,11,13,15,17,19,21,23,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 2.00, 'CONCLUIDA', '2025-02-03 11:45:00', '02,08,15,27,34'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-02-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 9.00, 'CONCLUIDA', '2025-02-04 14:20:00', '03,07,14,21,28,35,42,49,56'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 5.00, 'CONCLUIDA', '2025-02-05 16:10:00', '02,04,06,08,10,12,14,16,18,20,22,24,25'),

-- Março
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-03-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 4.00, 'CONCLUIDA', '2025-03-01 13:30:00', '01,05,12,18,25,32,39'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-03-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 13.50, 'CONCLUIDA', '2025-03-02 15:45:00', '04,09,16,23,30,37,44,51,58'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-03-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 7.50, 'CONCLUIDA', '2025-03-03 12:15:00', '01,03,05,07,09,11,13,15,17,19,21,23,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-03-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 6.00, 'CONCLUIDA', '2025-03-04 17:20:00', '03,07,14,21,28,35,42,49'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-03-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 18.00, 'CONCLUIDA', '2025-03-05 14:35:00', '02,06,12,18,24,30,36,42,48,54,60'),

-- Abril
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-04-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 10.00, 'CONCLUIDA', '2025-04-01 11:20:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-04-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 8.00, 'CONCLUIDA', '2025-04-02 15:30:00', '01,05,12,18,25,32,39,46'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-04-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 22.50, 'CONCLUIDA', '2025-04-03 16:45:00', '01,05,12,18,25,32,39,46,53,60'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-04-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 12.50, 'CONCLUIDA', '2025-04-04 13:15:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-04-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 10.00, 'CONCLUIDA', '2025-04-05 17:40:00', '01,05,12,18,25,32,39,46,53,60,67,74'),

-- Maio
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-05-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 27.00, 'CONCLUIDA', '2025-05-01 10:25:00', '01,05,12,18,25,32,39,46,53,60,67,74,81'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-05-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 15.00, 'CONCLUIDA', '2025-05-02 14:50:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-05-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 12.00, 'CONCLUIDA', '2025-05-03 16:15:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-05-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 31.50, 'CONCLUIDA', '2025-05-04 11:30:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 17.50, 'CONCLUIDA', '2025-05-05 15:20:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),

-- Junho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-06-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 14.00, 'CONCLUIDA', '2025-06-01 12:45:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-06-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 36.00, 'CONCLUIDA', '2025-06-02 09:10:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-06-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 20.00, 'CONCLUIDA', '2025-06-03 17:35:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-06-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 16.00, 'CONCLUIDA', '2025-06-04 14:20:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-06-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 40.50, 'CONCLUIDA', '2025-06-05 16:55:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123'),

-- Julho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 22.50, 'CONCLUIDA', '2025-07-01 13:40:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-07-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 18.00, 'CONCLUIDA', '2025-07-02 15:25:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 45.00, 'CONCLUIDA', '2025-07-03 11:15:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-07-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 25.00, 'CONCLUIDA', '2025-07-04 18:30:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 20.00, 'CONCLUIDA', '2025-07-05 14:45:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144'),

-- Agosto
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-08-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'JOGO_INDIVIDUAL', 49.50, 'CONCLUIDA', '2025-08-01 10:20:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-08-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'JOGO_INDIVIDUAL', 27.50, 'CONCLUIDA', '2025-08-02 16:35:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-08-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'JOGO_INDIVIDUAL', 22.00, 'CONCLUIDA', '2025-08-03 12:50:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-08-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'JOGO_INDIVIDUAL', 54.00, 'CONCLUIDA', '2025-08-04 15:10:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158,165'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-08-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'JOGO_INDIVIDUAL', 30.00, 'CONCLUIDA', '2025-08-05 17:25:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),

-- Setembro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-01 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'JOGO_INDIVIDUAL', 24.00, 'CONCLUIDA', '2025-09-01 14:15:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158,165,172'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-09-02 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'JOGO_INDIVIDUAL', 58.50, 'CONCLUIDA', '2025-09-02 11:40:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158,165,172,179'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-03 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'LOTO'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'JOGO_INDIVIDUAL', 32.50, 'CONCLUIDA', '2025-09-03 16:20:00', '01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-09-04 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'QUINA'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'JOGO_INDIVIDUAL', 26.00, 'CONCLUIDA', '2025-09-04 13:55:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158,165,172,179,186'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-05 08:00:00'), (SELECT id FROM jogo WHERE codigo = 'MEGA'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'JOGO_INDIVIDUAL', 63.00, 'CONCLUIDA', '2025-09-05 15:30:00', '01,05,12,18,25,32,39,46,53,60,67,74,81,88,95,102,109,116,123,130,137,144,151,158,165,172,179,186,193');

-- Vendas de bolões - Janeiro a Setembro 2025
INSERT INTO venda (id, turno_id, bolao_id, cliente_id, tipo_venda, valor_total, status, data_venda, cotas_compradas) VALUES
-- Janeiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2654'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-01-02 09:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2876'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-01-03 10:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6354'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-01-04 11:20:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2345'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-01-05 13:15:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-06 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1876'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-01-06 15:30:00', 10),

-- Fevereiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-02-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2655'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-02-02 14:10:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2877'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-02-03 17:25:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-02-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6355'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-02-04 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2346'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-02-05 16:15:00', 10),

-- Março
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-03-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1877'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-03-01 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-03-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '9876'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-03-02 16:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-03-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '4567'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 35.00, 'CONCLUIDA', '2025-03-03 12:20:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-03-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2656'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-03-04 14:10:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-03-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2878'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-03-05 17:25:00', 10),

-- Abril
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-04-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6356'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-04-01 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-04-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2347'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-04-02 16:15:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-04-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1878'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-04-03 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-04-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '9877'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-04-04 16:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-04-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2657'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-04-05 14:10:00', 10),

-- Maio
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2879'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-05-01 17:25:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-05-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6354'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-05-02 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2345'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-05-03 16:15:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-05-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1876'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-05-04 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '9876'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-05-05 16:45:00', 10),

-- Junho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-06-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '4567'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'BOLAO', 35.00, 'CONCLUIDA', '2025-06-01 12:20:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-06-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2654'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-06-02 14:10:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-06-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2876'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-06-03 17:25:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-06-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6354'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-06-04 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-06-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2345'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-06-05 16:15:00', 10),

-- Julho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1876'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-07-01 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-07-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '9876'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-07-02 16:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '4567'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 35.00, 'CONCLUIDA', '2025-07-03 12:20:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-07-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2655'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-07-04 14:10:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2877'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-07-05 17:25:00', 10),

-- Agosto
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-08-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6355'), (SELECT id FROM cliente WHERE cpf = '12345678905'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-08-01 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-08-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2346'), (SELECT id FROM cliente WHERE cpf = '12345678906'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-08-02 16:15:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-08-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1877'), (SELECT id FROM cliente WHERE cpf = '12345678907'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-08-03 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-08-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '9877'), (SELECT id FROM cliente WHERE cpf = '12345678908'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-08-04 16:45:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-08-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2656'), (SELECT id FROM cliente WHERE cpf = '12345678909'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-08-05 14:10:00', 10),

-- Setembro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-01 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2878'), (SELECT id FROM cliente WHERE cpf = '12345678910'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-09-01 17:25:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-09-02 08:00:00'), (SELECT id FROM bolao WHERE concurso = '6356'), (SELECT id FROM cliente WHERE cpf = '12345678901'), 'BOLAO', 20.00, 'CONCLUIDA', '2025-09-02 13:40:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-03 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2347'), (SELECT id FROM cliente WHERE cpf = '12345678902'), 'BOLAO', 30.00, 'CONCLUIDA', '2025-09-03 16:15:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-09-04 08:00:00'), (SELECT id FROM bolao WHERE concurso = '1878'), (SELECT id FROM cliente WHERE cpf = '12345678903'), 'BOLAO', 25.00, 'CONCLUIDA', '2025-09-04 12:30:00', 10),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-05 08:00:00'), (SELECT id FROM bolao WHERE concurso = '2657'), (SELECT id FROM cliente WHERE cpf = '12345678904'), 'BOLAO', 45.00, 'CONCLUIDA', '2025-09-05 14:10:00', 10);

-- Pagamentos para todas as vendas (amostra representativa)
INSERT INTO pagamento (id, venda_id, metodo_pagamento, valor, status, data_pagamento) VALUES
-- Pagamentos para vendas de jogos individuais
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:15:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-01-02 09:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 10:30:00'), 'CARTAO_DEBITO', 2.50, 'APROVADO', '2025-01-02 10:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 11:45:00'), 'DINHEIRO', 2.00, 'APROVADO', '2025-01-03 11:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 14:20:00'), 'CARTAO_CREDITO', 9.00, 'APROVADO', '2025-01-04 14:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 16:10:00'), 'PIX', 5.00, 'APROVADO', '2025-01-05 16:10:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-06 13:30:00'), 'DINHEIRO', 4.00, 'APROVADO', '2025-01-06 13:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-07 15:45:00'), 'CARTAO_DEBITO', 13.50, 'APROVADO', '2025-01-07 15:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-08 12:15:00'), 'PIX', 7.50, 'APROVADO', '2025-01-08 12:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-09 17:20:00'), 'DINHEIRO', 6.00, 'APROVADO', '2025-01-09 17:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-10 14:35:00'), 'CARTAO_CREDITO', 18.00, 'APROVADO', '2025-01-10 14:35:00'),

-- Pagamentos para vendas de bolões
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:30:00'), 'DINHEIRO', 45.00, 'APROVADO', '2025-01-02 09:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-03 10:45:00'), 'PIX', 25.00, 'APROVADO', '2025-01-03 10:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-04 11:20:00'), 'CARTAO_DEBITO', 20.00, 'APROVADO', '2025-01-04 11:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-05 13:15:00'), 'DINHEIRO', 30.00, 'APROVADO', '2025-01-05 13:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-01-06 15:30:00'), 'PIX', 25.00, 'APROVADO', '2025-01-06 15:30:00'),

-- Mais pagamentos representativos
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-02-01 09:15:00'), 'DINHEIRO', 4.50, 'APROVADO', '2025-02-01 09:15:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-03-01 13:30:00'), 'CARTAO_CREDITO', 4.00, 'APROVADO', '2025-03-01 13:30:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-04-01 11:20:00'), 'PIX', 10.00, 'APROVADO', '2025-04-01 11:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-05-01 10:25:00'), 'DINHEIRO', 27.00, 'APROVADO', '2025-05-01 10:25:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-06-01 12:45:00'), 'CARTAO_DEBITO', 14.00, 'APROVADO', '2025-06-01 12:45:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-07-01 13:40:00'), 'PIX', 22.50, 'APROVADO', '2025-07-01 13:40:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-08-01 10:20:00'), 'DINHEIRO', 49.50, 'APROVADO', '2025-08-01 10:20:00'),
(UUID(), (SELECT id FROM venda WHERE data_venda = '2025-09-01 14:15:00'), 'CARTAO_CREDITO', 24.00, 'APROVADO', '2025-09-01 14:15:00');

-- Movimentos de caixa (amostra representativa)
INSERT INTO movimento_caixa (id, turno_id, tipo_movimento, valor, descricao, data_movimento) VALUES
-- Movimentos de janeiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 4.50, 'Venda Mega-Sena', '2025-01-02 09:15:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 2.50, 'Venda Lotofácil', '2025-01-02 10:30:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-01-02 08:00:00'), 'ENTRADA', 45.00, 'Venda Bolão Mega-Sena', '2025-01-02 09:30:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 2.00, 'Venda Quina', '2025-01-03 11:45:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-01-03 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Lotofácil', '2025-01-03 10:45:00'),

-- Movimentos de fevereiro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-001' AND data_abertura = '2025-02-01 08:00:00'), 'ENTRADA', 4.50, 'Venda Mega-Sena', '2025-02-01 09:15:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-02-02 08:00:00'), 'ENTRADA', 2.50, 'Venda Lotofácil', '2025-02-02 10:30:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-002' AND data_abertura = '2025-02-02 08:00:00'), 'ENTRADA', 45.00, 'Venda Bolão Mega-Sena', '2025-02-02 14:10:00'),

-- Movimentos de março
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-03-01 08:00:00'), 'ENTRADA', 4.00, 'Venda Quina', '2025-03-01 13:30:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-03-02 08:00:00'), 'ENTRADA', 13.50, 'Venda Mega-Sena', '2025-03-02 15:45:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-03-02 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Super Sete', '2025-03-02 16:45:00'),

-- Movimentos de abril
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-04-01 08:00:00'), 'ENTRADA', 10.00, 'Venda Lotofácil', '2025-04-01 11:20:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-04-01 08:00:00'), 'ENTRADA', 20.00, 'Venda Bolão Quina', '2025-04-01 13:40:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-04-02 08:00:00'), 'ENTRADA', 8.00, 'Venda Quina', '2025-04-02 15:30:00'),

-- Movimentos de maio
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-01 08:00:00'), 'ENTRADA', 27.00, 'Venda Mega-Sena', '2025-05-01 10:25:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-05-01 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Lotofácil', '2025-05-01 17:25:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-05-02 08:00:00'), 'ENTRADA', 15.00, 'Venda Lotofácil', '2025-05-02 14:50:00'),

-- Movimentos de junho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-06-01 08:00:00'), 'ENTRADA', 14.00, 'Venda Quina', '2025-06-01 12:45:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-06-01 08:00:00'), 'ENTRADA', 35.00, 'Venda Bolão Timemania', '2025-06-01 12:20:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-06-02 08:00:00'), 'ENTRADA', 36.00, 'Venda Mega-Sena', '2025-06-02 09:10:00'),

-- Movimentos de julho
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-01 08:00:00'), 'ENTRADA', 22.50, 'Venda Lotofácil', '2025-07-01 13:40:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-07-01 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Lotofácil', '2025-07-01 12:30:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-07-02 08:00:00'), 'ENTRADA', 18.00, 'Venda Quina', '2025-07-02 15:25:00'),

-- Movimentos de agosto
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-08-01 08:00:00'), 'ENTRADA', 49.50, 'Venda Mega-Sena', '2025-08-01 10:20:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-08-01 08:00:00'), 'ENTRADA', 20.00, 'Venda Bolão Quina', '2025-08-01 13:40:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-08-02 08:00:00'), 'ENTRADA', 27.50, 'Venda Lotofácil', '2025-08-02 16:35:00'),

-- Movimentos de setembro
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-01 08:00:00'), 'ENTRADA', 24.00, 'Venda Quina', '2025-09-01 14:15:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-02' AND data_abertura = '2025-09-01 08:00:00'), 'ENTRADA', 25.00, 'Venda Bolão Lotofácil', '2025-09-01 17:25:00'),
(UUID(), (SELECT id FROM turno WHERE caixa_id = 'CAIXA-01' AND data_abertura = '2025-09-02 08:00:00'), 'ENTRADA', 58.50, 'Venda Mega-Sena', '2025-09-02 11:40:00');

-- Regras de comissão
INSERT INTO comissao_regra (id, jogo_id, tipo_venda, percentual_comissao, valor_minimo, valor_maximo, ativo) VALUES
(UUID(), (SELECT id FROM jogo WHERE codigo = 'MEGA'), 'JOGO_INDIVIDUAL', 5.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'LOTO'), 'JOGO_INDIVIDUAL', 4.0, 0.00, 999999.99, TRUE),
(UUID(), (SELECT id FROM jogo WHERE codigo = 'QUINA'), 'JOGO_INDIVIDUAL', 3.5, 0.00, 999999.99, TRUE),
(UUID(), NULL, 'BOLAO', 6.0, 0.00, 999999.99, TRUE);

-- Auditoria de algumas vendas importantes
INSERT INTO auditoria (id, tabela_afetada, registro_id, operacao, dados_anteriores, dados_novos, usuario_id, data_operacao) VALUES
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:15:00'), 'INSERT', NULL, '{"valor_total": 4.50, "status": "CONCLUIDA", "tipo_venda": "JOGO_INDIVIDUAL"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:15:00'),
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-01-02 09:30:00'), 'INSERT', NULL, '{"valor_total": 45.00, "status": "CONCLUIDA", "tipo_venda": "BOLAO"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:30:00'),
(UUID(), 'pagamento', (SELECT id FROM pagamento WHERE data_pagamento = '2025-01-02 09:15:00'), 'INSERT', NULL, '{"metodo_pagamento": "DINHEIRO", "valor": 4.50, "status": "APROVADO"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-01-02 09:15:00'),
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-06-05 16:55:00'), 'INSERT', NULL, '{"valor_total": 40.50, "status": "CONCLUIDA", "tipo_venda": "JOGO_INDIVIDUAL"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-06-05 16:55:00'),
(UUID(), 'venda', (SELECT id FROM venda WHERE data_venda = '2025-09-05 15:30:00'), 'INSERT', NULL, '{"valor_total": 63.00, "status": "CONCLUIDA", "tipo_venda": "JOGO_INDIVIDUAL"}', (SELECT id FROM usuario WHERE email = 'admin@loteria360.local'), '2025-09-05 15:30:00');
