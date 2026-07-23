package br.com.empresa.emprestimos.proposta.application.service;

import br.com.empresa.emprestimos.proposta.application.dto.CriarPropostaRequest;
import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.proposta.domain.service.CalculadoraEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.service.PlanoPagamento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class CriarPropostaService {

    private final PropostaRepository propostaRepository;
    private final CalculadoraEmprestimo calculadoraEmprestimo;
    private final PropostaMapper propostaMapper;

    public CriarPropostaService(PropostaRepository propostaRepository,
                                CalculadoraEmprestimo calculadoraEmprestimo,
                                PropostaMapper propostaMapper) {
        this.propostaRepository = propostaRepository;
        this.calculadoraEmprestimo = calculadoraEmprestimo;
        this.propostaMapper = propostaMapper;
    }

    public PropostaDTO executar(CriarPropostaRequest request) {
        PlanoPagamento plano = calculadoraEmprestimo.calcular(
                request.getValorSolicitado(),
                request.getTaxaJurosMensal(),
                request.getQuantidadeParcelas(),
                LocalDate.now().plusMonths(1)
        );

        PropostaEmprestimo proposta = new PropostaEmprestimo(
                request.getClienteId(),
                request.getValorSolicitado(),
                request.getQuantidadeParcelas(),
                request.getTaxaJurosMensal(),
                plano.parcelas().get(0).valorTotal(),
                plano.valorTotal()
        );

        PropostaEmprestimo propostaSalva = propostaRepository.salvar(proposta);

        return propostaMapper.toDTO(propostaSalva);
    }
}