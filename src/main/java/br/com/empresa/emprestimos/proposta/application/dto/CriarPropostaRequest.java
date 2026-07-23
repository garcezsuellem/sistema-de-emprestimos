package br.com.empresa.emprestimos.proposta.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriarPropostaRequest {

    @NotNull(message = "Cliente é obrigatório")
    private UUID clienteId;

    @NotNull(message = "Valor solicitado é obrigatório")
    @Positive(message = "Valor solicitado deve ser maior que zero")
    private BigDecimal valorSolicitado;

    @NotNull(message = "Quantidade de parcelas é obrigatória")
    @Positive(message = "Quantidade de parcelas deve ser maior que zero")
    private Integer quantidadeParcelas;

    @NotNull(message = "Taxa de juros mensal é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Taxa de juros não pode ser negativa")
    private BigDecimal taxaJurosMensal;
}
