package br.com.empresa.emprestimos.proposta.application.service;

import br.com.empresa.emprestimos.proposta.application.dto.AnaliseCreditoDTO;
import br.com.empresa.emprestimos.proposta.application.dto.AnalisarCreditoRequest;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.domain.entity.AnaliseCredito;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.AnaliseCreditoRepository;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.shared.enums.ResultadoAnalise;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
@Transactional
public class AnalisarCreditoService {

    private static final BigDecimal LIMITE_APROVACAO = new BigDecimal("0.30");
    private static final BigDecimal LIMITE_RESTRICAO = new BigDecimal("0.50");

    private final PropostaRepository propostaRepository;
    private final AnaliseCreditoRepository analiseCreditoRepository;
    private final PropostaMapper propostaMapper;

    public AnalisarCreditoService(PropostaRepository propostaRepository,
                                  AnaliseCreditoRepository analiseCreditoRepository,
                                  PropostaMapper propostaMapper) {
        this.propostaRepository = propostaRepository;
        this.analiseCreditoRepository = analiseCreditoRepository;
        this.propostaMapper = propostaMapper;
    }

    public AnaliseCreditoDTO executar(UUID propostaId, UUID usuarioId, AnalisarCreditoRequest request) {
        PropostaEmprestimo proposta = propostaRepository.buscarPorId(propostaId)
                .orElseThrow(() -> new NotFoundException("Proposta", propostaId));

        BigDecimal comprometimentoMensal = proposta.getValorParcelaCalculado()
                .divide(request.getRendaConsiderada(), 4, RoundingMode.HALF_UP);

        ResultadoAnalise resultado = calcularResultado(comprometimentoMensal);

        AnaliseCredito analise = new AnaliseCredito(
                propostaId,
                proposta.getClienteId(),
                usuarioId,
                request.getScoreCredito(),
                request.getRendaConsiderada(),
                comprometimentoMensal,
                resultado,
                request.getObservacao());


        AnaliseCredito analiseSalva = analiseCreditoRepository.salvar(analise);

        return propostaMapper.toDTO(analiseSalva);
    }

    private ResultadoAnalise calcularResultado(BigDecimal comprometimentoMensal) {
        if (comprometimentoMensal.compareTo(LIMITE_APROVACAO) <= 0) {
            return ResultadoAnalise.APROVADO;
        }
        if (comprometimentoMensal.compareTo(LIMITE_RESTRICAO) <= 0) {
            return ResultadoAnalise.ANALISE_MANUAL;
        }
        return ResultadoAnalise.RECUSADO;
    }
}
