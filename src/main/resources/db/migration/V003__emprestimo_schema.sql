CREATE TABLE emprestimos (
    id UUID PRIMARY KEY,
    numero_contrato VARCHAR(50) UNIQUE NOT NULL,
    cliente_id UUID NOT NULL,
    proposta_id UUID NOT NULL,
    valor_principal NUMERIC(19,2) NOT NULL,
    taxa_juros_mensal NUMERIC(8,4) NOT NULL,
    quantidade_parcelas INTEGER NOT NULL,
    valor_total NUMERIC(19,2) NOT NULL,
    saldo_devedor NUMERIC(19,2) NOT NULL,
    data_contratacao DATE NOT NULL,
    data_primeiro_vencimento DATE NOT NULL,
    data_liquidacao DATE,
    situacao VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    versao BIGINT DEFAULT 0,
    CONSTRAINT ck_emprestimo_situacao CHECK (situacao IN ('AGUARDANDO_LIBERACAO', 'ATIVO', 'EM_ATRASO', 'INADIMPLENTE', 'RENEGOCIADO', 'LIQUIDADO', 'CANCELADO'))
);

CREATE INDEX idx_emprestimo_cliente_id ON emprestimos(cliente_id);
CREATE INDEX idx_emprestimo_proposta_id ON emprestimos(proposta_id);
CREATE INDEX idx_emprestimo_numero_contrato ON emprestimos(numero_contrato);

CREATE TABLE liberacoes_emprestimo (
    id UUID PRIMARY KEY,
    emprestimo_id UUID NOT NULL REFERENCES emprestimos(id),
    cliente_id UUID NOT NULL,
    valor_liberado NUMERIC(19,2) NOT NULL,
    solicitada_em TIMESTAMP WITH TIME ZONE NOT NULL,
    processada_em TIMESTAMP WITH TIME ZONE,
    situacao VARCHAR(30) NOT NULL DEFAULT 'PENDENTE',
    transacao_externa_id VARCHAR(100),
    motivo_falha VARCHAR(500),
    CONSTRAINT ck_liberacao_situacao CHECK (situacao IN ('PENDENTE', 'PROCESSANDO', 'CONCLUIDA', 'FALHA', 'CANCELADA'))
);

CREATE INDEX idx_liberacao_emprestimo_id ON liberacoes_emprestimo(emprestimo_id);