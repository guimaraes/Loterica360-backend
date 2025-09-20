-- Adicionar coluna atualizado_em na tabela usuario
ALTER TABLE usuario ADD COLUMN atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

-- Atualizar registros existentes para ter a mesma data de criação e atualização
UPDATE usuario SET atualizado_em = criado_em WHERE atualizado_em IS NULL;
