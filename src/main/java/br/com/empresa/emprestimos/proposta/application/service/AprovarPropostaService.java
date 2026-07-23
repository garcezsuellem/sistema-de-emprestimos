package br.com.empresa.emprestimos.proposta.application.service;

import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AprovarPropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper propostaMapper;

    public AprovarPropostaService(PropostaRepository propostaRepository, PropostaMapper propostaMapper) {
        this.propostaRepository = propostaRepository;
        this.propostaMapper = propostaMapper;
    }

    public PropostaDTO executar(UUID propostaId, UUID usuarioId) {
        PropostaEmprestimo proposta = propostaRepository.buscarPorId(propostaId)
                .orElseThrow(() -> new NotFoundException("Proposta", propostaId));

        proposta.aprovar(usuarioId);

        PropostaEmprestimo propostaAtualizada = propostaRepository.salvar(proposta);

        return propostaMapper.toDTO(propostaAtualizada);
    }
}
