package br.com.empresa.emprestimos.proposta.application.dto;

import br.com.empresa.emprestimos.shared.enums.SituacaoProposta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropostaDTO {

    private UUID id;
    private UUID clienteId;
    private BigDecimal valorSolicitado;
    private Integer quantidadeParcelas;
    private BigDecimal taxaJurosMensal;
    private BigDecimal valorParcelaCalculado;
    private BigDecimal valorTotalCalculado;
    private SituacaoProposta situacao;
    private OffsetDateTime criadaEm;
    private OffsetDateTime analisadaEm;
    private UUID analisadaPorUsuarioId;
    private String motivoRecusa;
}
