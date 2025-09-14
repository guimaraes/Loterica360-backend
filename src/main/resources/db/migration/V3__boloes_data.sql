-- Migração V3: Inserção de 20 bolões para os jogos cadastrados

-- Bolões para Mega-Sena (4 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), 'b00aa71a-9107-11f0-aae2-0242ac150002', '2654', 'Bolão Mega-Sena Concurso 2654 - Final de Semana', 100, 0, 4.50, '2025-09-15', 'ABERTO'),
(UUID(), 'b00aa71a-9107-11f0-aae2-0242ac150002', '2655', 'Bolão Mega-Sena Concurso 2655 - Meio de Semana', 150, 25, 4.50, '2025-09-18', 'ABERTO'),
(UUID(), 'b00aa71a-9107-11f0-aae2-0242ac150002', '2656', 'Bolão Mega-Sena Concurso 2656 - Acumulou!', 200, 45, 4.50, '2025-09-20', 'ABERTO'),
(UUID(), 'b00aa71a-9107-11f0-aae2-0242ac150002', '2657', 'Bolão Mega-Sena Concurso 2657 - Super Prêmio', 120, 0, 4.50, '2025-09-22', 'ABERTO');

-- Bolões para Lotofácil (4 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), 'b00aa9e4-9107-11f0-aae2-0242ac150002', '2876', 'Bolão Lotofácil Concurso 2876 - Segunda-feira', 80, 12, 2.50, '2025-09-16', 'ABERTO'),
(UUID(), 'b00aa9e4-9107-11f0-aae2-0242ac150002', '2877', 'Bolão Lotofácil Concurso 2877 - Quarta-feira', 100, 30, 2.50, '2025-09-18', 'ABERTO'),
(UUID(), 'b00aa9e4-9107-11f0-aae2-0242ac150002', '2878', 'Bolão Lotofácil Concurso 2878 - Sexta-feira', 90, 8, 2.50, '2025-09-20', 'ABERTO'),
(UUID(), 'b00aa9e4-9107-11f0-aae2-0242ac150002', '2879', 'Bolão Lotofácil Concurso 2879 - Domingo', 75, 0, 2.50, '2025-09-22', 'ABERTO');

-- Bolões para Quina (3 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), 'b00aab36-9107-11f0-aae2-0242ac150002', '6354', 'Bolão Quina Concurso 6354 - Terça-feira', 60, 15, 2.00, '2025-09-17', 'ABERTO'),
(UUID(), 'b00aab36-9107-11f0-aae2-0242ac150002', '6355', 'Bolão Quina Concurso 6355 - Quinta-feira', 85, 22, 2.00, '2025-09-19', 'ABERTO'),
(UUID(), 'b00aab36-9107-11f0-aae2-0242ac150002', '6356', 'Bolão Quina Concurso 6356 - Sábado', 70, 5, 2.00, '2025-09-21', 'ABERTO');

-- Bolões para Lotomania (3 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), '3fc2cce2-df05-4c28-9557-744bf079b479', '2345', 'Bolão Lotomania Concurso 2345 - Segunda-feira', 110, 18, 3.00, '2025-09-16', 'ABERTO'),
(UUID(), '3fc2cce2-df05-4c28-9557-744bf079b479', '2346', 'Bolão Lotomania Concurso 2346 - Quarta-feira', 95, 35, 3.00, '2025-09-18', 'ABERTO'),
(UUID(), '3fc2cce2-df05-4c28-9557-744bf079b479', '2347', 'Bolão Lotomania Concurso 2347 - Sexta-feira', 125, 0, 3.00, '2025-09-20', 'ABERTO');

-- Bolões para Dia de Sorte (3 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), '4c42e074-07fc-4ee6-90e5-24c5fa5f5802', '1876', 'Bolão Dia de Sorte Concurso 1876 - Terça-feira', 65, 10, 2.50, '2025-09-17', 'ABERTO'),
(UUID(), '4c42e074-07fc-4ee6-90e5-24c5fa5f5802', '1877', 'Bolão Dia de Sorte Concurso 1877 - Quinta-feira', 80, 28, 2.50, '2025-09-19', 'ABERTO'),
(UUID(), '4c42e074-07fc-4ee6-90e5-24c5fa5f5802', '1878', 'Bolão Dia de Sorte Concurso 1878 - Sábado', 55, 0, 2.50, '2025-09-21', 'ABERTO');

-- Bolões para Super Sete (2 bolões)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), '70c8740c-9c3a-4c72-9a35-03d0cd0c444e', '9876', 'Bolão Super Sete Concurso 9876 - Quarta-feira', 40, 12, 2.50, '2025-09-18', 'ABERTO'),
(UUID(), '70c8740c-9c3a-4c72-9a35-03d0cd0c444e', '9877', 'Bolão Super Sete Concurso 9877 - Domingo', 50, 8, 2.50, '2025-09-22', 'ABERTO');

-- Bolões para Timemania (1 bolão)
INSERT INTO bolao (id, jogo_id, concurso, descricao, cotas_totais, cotas_vendidas, valor_cota, data_sorteio, status) VALUES
(UUID(), 'f4935dd6-ce09-4877-9ea1-cefd74bdf786', '4567', 'Bolão Timemania Concurso 4567 - Sexta-feira', 90, 20, 3.50, '2025-09-20', 'ABERTO');
