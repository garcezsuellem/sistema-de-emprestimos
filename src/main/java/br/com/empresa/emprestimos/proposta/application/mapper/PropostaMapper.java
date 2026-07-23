package br.com.empresa.emprestimos.proposta.application.mapper;

import br.com.empresa.emprestimos.proposta.application.dto.AnaliseCreditoDTO;
import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.domain.entity.AnaliseCredito;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import org.springframework.stereotype.Component;

@Component
public class PropostaMapper {

    public PropostaDTO toDTO(PropostaEmprestimo proposta) {
        return PropostaDTO.builder()
                .id(proposta.getId())
                .clienteId(proposta.getClienteId())
                .valorSolicitado(proposta.getValorSolicitado())
                .quantidadeParcelas(proposta.getQuantidadeParcelas())
                .taxaJurosMensal(proposta.getTaxaJurosMensal())
                .valorParcelaCalculado(proposta.getValorParcelaCalculado())
                .valorTotalCalculado(proposta.getValorTotalCalculado())
                .situacao(proposta.getSituacao())
                .criadaEm(proposta.getCriadaEm())
                .analisadaEm(proposta.getAnalisadaEm())
                .analisadaPorUsuarioId(proposta.getAnalisadaPorUsuarioId())
                .motivoRecusa(proposta.getMotivoRecusa())
                .build();
    }

    public AnaliseCreditoDTO toDTO(AnaliseCredito analise) {
        return AnaliseCreditoDTO.builder()
                .id(analise.getId())
                .propostaId(analise.getPropostaId())
                .clienteId(analise.getClienteId())
                .analisadaPorUsuarioId(analise.getAnalisadaPorUsuarioId())
                .scoreCredito(analise.getScoreCredito())
                .rendaConsiderada(analise.getRendaConsiderada())
                .comprometimentoMensal(analise.getComprometimentoMensal())
                .resultado(analise.getResultado())
                .observacao(analise.getObservacao())
                .realizadaEm(analise.getRealizadaEm())
                .build();
    }
}
