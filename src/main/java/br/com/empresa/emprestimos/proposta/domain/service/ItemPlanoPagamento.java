package br.com.empresa.emprestimos.proposta.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemPlanoPagamento(
        Integer numero,
        LocalDate vencimento,
        BigDecimal valorPrincipal,
        BigDecimal valorJuros,
        BigDecimal valorTotal){

}




