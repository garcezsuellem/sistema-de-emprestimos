CREATE TABLE cobrancas (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    emprestimo_id UUID NOT NULL REFERENCES emprestimos(id),
    parcela_id UUID REFERENCES parcelas(id),
    tipo VARCHAR(30) NOT NULL,
    situacao VARCHAR(30) NOT NULL DEFAULT 'ABERTA',
    iniciada_em TIMESTAMP WITH TIME ZONE NOT NULL,
    encerrada_em TIMESTAMP WITH TIME ZONE,
    observacao VARCHAR(1000),
    CONSTRAINT ck_tipo_cobranca CHECK (tipo IN ('LEMBRETE', 'COBRANCA_AMIGAVEL', 'NOTIFICACAO_FORMAL', 'NEGOCIACAO', 'COBRANCA_JUDICIAL')),
    CONSTRAINT ck_cobranca_situacao CHECK (situacao IN ('ABERTA', 'EM_NEGOCIACAO', 'ACORDO_REALIZADO', 'ENCERRADA', 'SEM_CONTATO'))
);

CREATE INDEX idx_cobranca_cliente_id ON cobrancas(cliente_id);
CREATE INDEX idx_cobranca_emprestimo_id ON cobrancas(emprestimo_id);

CREATE TABLE renegociacoes (
    id UUID PRIMARY KEY,
    emprestimo_original_id UUID NOT NULL REFERENCES emprestimos(id),
    novo_emprestimo_id UUID REFERENCES emprestimos(id),
    cliente_id UUID NOT NULL,
    saldo_renegociado NUMERIC(19,2) NOT NULL,
    nova_quantidade_parcelas INTEGER NOT NULL,
    nova_taxa_juros NUMERIC(8,4) NOT NULL,
    situacao VARCHAR(30) NOT NULL DEFAULT 'PROPOSTA',
    criada_em TIMESTAMP WITH TIME ZONE NOT NULL,
    aceita_em TIMESTAMP WITH TIME ZONE,
    CONSTRAINT ck_renegociacao_situacao CHECK (situacao IN ('PROPOSTA', 'ACEITA', 'RECUSADA', 'CANCELADA', 'FORMALIZADA'))
);

CREATE INDEX idx_renegociacao_emprestimo_original_id ON renegociacoes(emprestimo_original_id);
CREATE INDEX idx_renegociacao_cliente_id ON renegociacoes(cliente_id);