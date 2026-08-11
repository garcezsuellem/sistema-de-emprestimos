package br.com.empresa.emprestimos.proposta.domain.event;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PropostaAprovadaEvent(
        UUID propostaId,
        UUID clienteId,
        BigDecimal valor,
        OffsetDateTime ocorridoEm
) {
    public PropostaAprovadaEvent(UUID propostaId, UUID clienteId, BigDecimal valor) {
        this(propostaId, clienteId, valor, OffsetDateTime.now());
    }
}
