package br.com.empresa.emprestimos.proposta.domain.service;

import java.math.BigDecimal;
import java.util.List;

public record PlanoPagamento(BigDecimal valorTotal, List<ItemPlanoPagamento> parcelas){
}

