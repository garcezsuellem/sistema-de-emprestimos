package br.com.empresa.emprestimos.proposta.domain.event;

import java.time.OffsetDateTime;
import java.util.UUID;

public record PropostaRecusadaEvent(
        UUID propostaId,
        UUID clienteId,
        String motivo,
        OffsetDateTime ocorridoEm
) {
    public PropostaRecusadaEvent(UUID propostaId, UUID clienteId, String motivo) {
        this(propostaId, clienteId, motivo, OffsetDateTime.now());
    }
}