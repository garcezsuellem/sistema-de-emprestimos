package br.com.empresa.emprestimos.proposta.application;

import br.com.empresa.emprestimos.proposta.application.dto.SimularPropostaRequest;
import br.com.empresa.emprestimos.proposta.domain.service.CalculadoraEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.service.PlanoPagamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
public class SimularPropostaService {

    private final CalculadoraEmprestimo calculadoraEmprestimo;

    public SimularPropostaService(CalculadoraEmprestimo calculadoraEmprestimo) {
        this.calculadoraEmprestimo = calculadoraEmprestimo;
    }

    public PlanoPagamento executar(SimularPropostaRequest request) {
        return calculadoraEmprestimo.calcular(
                request.getValorSolicitado(),
                request.getTaxaJurosMensal(),
                request.getQuantidadeParcelas(),
                LocalDate.now().plusMonths(1)
        );
    }
}
