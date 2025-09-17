-- Inserir usuários de teste
INSERT INTO usuario (id, nome, email, senha_hash, papel, ativo) VALUES
(UUID(), 'João Silva', 'joao@loteria360.local', '$2a$10$NtkWbhXOJI4d7M6WBXm3xucJQZhSxEMvxkJgTT0HF0UVqVZwwLhH2', 'VENDEDOR', TRUE),
(UUID(), 'Maria Santos', 'maria@loteria360.local', '$2a$10$NtkWbhXOJI4d7M6WBXm3xucJQZhSxEMvxkJgTT0HF0UVqVZwwLhH2', 'VENDEDOR', TRUE),
(UUID(), 'Pedro Costa', 'pedro@loteria360.local', '$2a$10$NtkWbhXOJI4d7M6WBXm3xucJQZhSxEMvxkJgTT0HF0UVqVZwwLhH2', 'VENDEDOR', TRUE),
(UUID(), 'Ana Oliveira', 'ana@loteria360.local', '$2a$10$NtkWbhXOJI4d7M6WBXm3xucJQZhSxEMvxkJgTT0HF0UVqVZwwLhH2', 'GERENTE', TRUE),
(UUID(), 'Carlos Lima', 'carlos@loteria360.local', '$2a$10$NtkWbhXOJI4d7M6WBXm3xucJQZhSxEMvxkJgTT0HF0UVqVZwwLhH2', 'VENDEDOR', TRUE);

-- Inserir clientes de teste
INSERT INTO cliente (id, nome, cpf, telefone, email, consentimento_lgpd) VALUES
(UUID(), 'Cliente Teste 1', '123.456.789-01', '11999999999', 'cliente1@teste.com', TRUE),
(UUID(), 'Cliente Teste 2', '987.654.321-00', '11888888888', 'cliente2@teste.com', FALSE),
(UUID(), 'Cliente Teste 3', '111.222.333-44', '11777777777', 'cliente3@teste.com', TRUE);