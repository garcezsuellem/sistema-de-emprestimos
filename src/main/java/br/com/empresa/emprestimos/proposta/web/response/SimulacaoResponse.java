package br.com.empresa.emprestimos.proposta.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimulacaoResponse {

    private BigDecimal valorTotal;
    private List<ItemSimulacao> parcelas;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemSimulacao {
        private Integer numero;
        private LocalDate vencimento;
        private BigDecimal valorPrincipal;
        private BigDecimal valorJuros;
        private BigDecimal valorTotal;
    }
}
