CREATE TABLE usuarios (
    id UUID PRIMARY KEY,
    keycloak_id VARCHAR(255) UNIQUE NOT NULL,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    login VARCHAR(100) UNIQUE NOT NULL,
    situacao VARCHAR(30) NOT NULL DEFAULT 'ATIVO',
    cadastrado_em TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT ck_usuario_situacao CHECK (situacao IN ('ATIVO', 'BLOQUEADO', 'INATIVO'))
);

CREATE INDEX idx_usuario_keycloak_id ON usuarios(keycloak_id);
CREATE INDEX idx_usuario_email ON usuarios(email);

CREATE TABLE usuario_roles (
    id UUID PRIMARY KEY,
    usuario_id UUID NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    role VARCHAR(100) NOT NULL
);

CREATE INDEX idx_usuario_roles_usuario_id ON usuario_roles(usuario_id);