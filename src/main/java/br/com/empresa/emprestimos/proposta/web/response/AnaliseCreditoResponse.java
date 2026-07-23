package br.com.empresa.emprestimos.proposta.web.response;

import br.com.empresa.emprestimos.shared.enums.ResultadoAnalise;
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
public class AnaliseCreditoResponse {

    private UUID id;
    private UUID propostaId;
    private UUID clienteId;
    private UUID analisadaPorUsuarioId;
    private Integer scoreCredito;
    private BigDecimal rendaConsiderada;
    private BigDecimal comprometimentoMensal;
    private ResultadoAnalise resultado;
    private String observacao;
    private OffsetDateTime realizadaEm;
}
