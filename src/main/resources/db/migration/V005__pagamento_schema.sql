CREATE TABLE pagamentos (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    emprestimo_id UUID NOT NULL REFERENCES emprestimos(id),
    valor NUMERIC(19,2) NOT NULL,
    data_pagamento DATE NOT NULL,
    registrado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    forma_pagamento VARCHAR(30) NOT NULL,
    situacao VARCHAR(30) NOT NULL DEFAULT 'RECEBIDO',
    identificador_externo VARCHAR(100) UNIQUE,
    CONSTRAINT ck_forma_pagamento CHECK (forma_pagamento IN ('PIX', 'BOLETO', 'DEBITO_AUTOMATICO', 'TRANSFERENCIA', 'DINHEIRO')),
    CONSTRAINT ck_pagamento_situacao CHECK (situacao IN ('RECEBIDO', 'CONFIRMADO', 'REJEITADO', 'ESTORNADO'))
);

CREATE INDEX idx_pagamento_cliente_id ON pagamentos(cliente_id);
CREATE INDEX idx_pagamento_emprestimo_id ON pagamentos(emprestimo_id);

CREATE TABLE alocacoes_pagamento (
    id UUID PRIMARY KEY,
    pagamento_id UUID NOT NULL REFERENCES pagamentos(id),
    parcela_id UUID NOT NULL REFERENCES parcelas(id),
    emprestimo_id UUID NOT NULL REFERENCES emprestimos(id),
    valor_alocado NUMERIC(19,2) NOT NULL,
    valor_principal NUMERIC(19,2) NOT NULL,
    valor_juros NUMERIC(19,2) NOT NULL,
    valor_multa NUMERIC(19,2) NOT NULL,
    alocado_em TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_alocacao_pagamento_id ON alocacoes_pagamento(pagamento_id);
CREATE INDEX idx_alocacao_parcela_id ON alocacoes_pagamento(parcela_id);