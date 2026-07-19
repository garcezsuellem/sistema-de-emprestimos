CREATE TABLE propostas_emprestimo (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    valor_solicitado NUMERIC(19,2) NOT NULL,
    quantidade_parcelas INTEGER NOT NULL,
    taxa_juros_mensal NUMERIC(8,4) NOT NULL,
    valor_parcela_calculado NUMERIC(19,2) NOT NULL,
    valor_total_calculado NUMERIC(19,2) NOT NULL,
    situacao VARCHAR(30) NOT NULL DEFAULT 'PENDENTE_ANALISE',
    criada_em TIMESTAMP WITH TIME ZONE NOT NULL,
    analisada_em TIMESTAMP WITH TIME ZONE,
    analisada_por_usuario_id UUID,
    motivo_recusa VARCHAR(500),
    CONSTRAINT ck_proposta_situacao CHECK (situacao IN ('RASCUNHO', 'PENDENTE_ANALISE', 'EM_ANALISE', 'APROVADA', 'RECUSADA', 'CANCELADA', 'EXPIRADA', 'CONTRATADA'))
);

CREATE TABLE analises_credito (
    id UUID PRIMARY KEY,
    proposta_id UUID NOT NULL REFERENCES propostas_emprestimo(id),
    cliente_id UUID NOT NULL,
    analisada_por_usuario_id UUID,
    score_credito INTEGER,
    renda_considerada NUMERIC(19,2),
    comprometimento_mensal NUMERIC(19,2),
    resultado VARCHAR(30) NOT NULL,
    observacao VARCHAR(1000),
    realizada_em TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT ck_resultado_analise CHECK (resultado IN ('APROVADO', 'APROVADO_COM_RESTRICAO', 'RECUSADO', 'ANALISE_MANUAL'))
);

CREATE INDEX idx_analise_proposta_id ON analises_credito(proposta_id);
CREATE INDEX idx_analise_cliente_id ON analises_credito(cliente_id);