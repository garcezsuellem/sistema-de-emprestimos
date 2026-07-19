CREATE TABLE parcelas (
    id UUID PRIMARY KEY,
    emprestimo_id UUID NOT NULL REFERENCES emprestimos(id),
    numero INTEGER NOT NULL,
    data_vencimento DATE NOT NULL,
    valor_principal NUMERIC(19,2) NOT NULL,
    valor_juros NUMERIC(19,2) NOT NULL,
    valor_original NUMERIC(19,2) NOT NULL,
    valor_pago NUMERIC(19,2) NOT NULL DEFAULT 0,
    valor_multa NUMERIC(19,2) NOT NULL DEFAULT 0,
    valor_juros_atraso NUMERIC(19,2) NOT NULL DEFAULT 0,
    situacao VARCHAR(30) NOT NULL DEFAULT 'ABERTA',
    data_pagamento DATE,
    versao BIGINT DEFAULT 0,
    CONSTRAINT ck_parcela_situacao CHECK (situacao IN ('ABERTA', 'PARCIALMENTE_PAGA', 'PAGA', 'ATRASADA', 'RENEGOCIADA', 'CANCELADA')),
    CONSTRAINT uk_parcela_emprestimo_numero UNIQUE(emprestimo_id, numero)
);

CREATE INDEX idx_parcela_emprestimo_id ON parcelas(emprestimo_id);
CREATE INDEX idx_parcela_data_vencimento ON parcelas(data_vencimento);