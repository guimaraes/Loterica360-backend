-- Adicionar campos NSU, TID e Referência à tabela pagamento
ALTER TABLE pagamento 
ADD COLUMN nsu VARCHAR(60) NULL,
ADD COLUMN tid VARCHAR(60) NULL,
ADD COLUMN referencia VARCHAR(100) NULL;

-- Adicionar índices para melhor performance
CREATE INDEX idx_pagamento_nsu ON pagamento(nsu);
CREATE INDEX idx_pagamento_tid ON pagamento(tid);
