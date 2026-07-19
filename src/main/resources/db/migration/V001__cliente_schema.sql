CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    data_nascimento DATE NOT NULL,
    email VARCHAR(150),
    telefone VARCHAR(20),
    situacao VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    cadastrado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT ck_cliente_situacao CHECK (situacao IN ('ATIVO', 'BLOQUEADO', 'INATIVO'))
);

CREATE TABLE dados_financeiros_clientes (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL REFERENCES clientes(id),
    renda_mensal NUMERIC(19,2) NOT NULL,
    outras_rendas NUMERIC(19,2),
    ocupacao VARCHAR(100) NOT NULL,
    empregador VARCHAR(150),
    atualizado_em TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_dados_financeiros_cliente_id ON dados_financeiros_clientes(cliente_id);

CREATE TABLE enderecos_clientes (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL REFERENCES clientes(id),
    cep VARCHAR(9) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    uf VARCHAR(2) NOT NULL
);

CREATE INDEX idx_enderecos_cliente_id ON enderecos_clientes(cliente_id);