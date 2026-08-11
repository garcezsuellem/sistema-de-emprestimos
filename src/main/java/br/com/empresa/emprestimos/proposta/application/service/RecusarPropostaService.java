package br.com.empresa.emprestimos.proposta.application.service;

import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.event.PropostaRecusadaEvent;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class RecusarPropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper propostaMapper;
    private final ApplicationEventPublisher eventPublisher;

    public RecusarPropostaService(PropostaRepository propostaRepository, PropostaMapper propostaMapper,
                                  ApplicationEventPublisher eventPublisher) {
        this.propostaRepository = propostaRepository;
        this.propostaMapper = propostaMapper;
        this.eventPublisher = eventPublisher;
    }

    public PropostaDTO executar(UUID propostaId, UUID usuarioId, String motivo) {
        PropostaEmprestimo proposta = propostaRepository.buscarPorId(propostaId)
                .orElseThrow(() -> new NotFoundException("Proposta", propostaId));

        proposta.recusar(usuarioId, motivo);

        PropostaEmprestimo propostaAtualizada = propostaRepository.salvar(proposta);

        eventPublisher.publishEvent(new PropostaRecusadaEvent(
                propostaAtualizada.getId(),
                propostaAtualizada.getClienteId(),
                motivo));

        return propostaMapper.toDTO(propostaAtualizada);
    }
}
