package br.com.empresa.emprestimos.cliente.web.response;

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
public class DadosFinanceirosClienteResponse {

    private UUID id;
    private UUID clienteId;
    private BigDecimal rendaMensal;
    private BigDecimal outrasRendas;
    private String ocupacao;
    private String empregador;
    private OffsetDateTime atualizadoEm;
}
