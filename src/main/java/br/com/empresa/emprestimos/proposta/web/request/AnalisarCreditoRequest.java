package br.com.empresa.emprestimos.proposta.web.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalisarCreditoRequest {

    @NotNull(message = "Score de crédito é obrigatório")
    private Integer scoreCredito;

    @NotNull(message = "Renda considerada é obrigatória")
    @PositiveOrZero(message = "Renda considerada não pode ser negativa")
    private BigDecimal rendaConsiderada;

    private String observacao;
}
