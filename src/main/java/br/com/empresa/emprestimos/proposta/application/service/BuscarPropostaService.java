package br.com.empresa.emprestimos.proposta.application.service;

import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BuscarPropostaService {

    private final PropostaRepository propostaRepository;
    private final PropostaMapper propostaMapper;

    public BuscarPropostaService(PropostaRepository propostaRepository, PropostaMapper propostaMapper) {
        this.propostaRepository = propostaRepository;
        this.propostaMapper = propostaMapper;
    }

    public PropostaDTO buscarPorId(UUID id) {
        PropostaEmprestimo proposta = propostaRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Proposta", id));

        return propostaMapper.toDTO(proposta);
    }

    public List<PropostaDTO> buscarPorClienteId(UUID clienteId) {
        return propostaRepository.buscarPorClienteId(clienteId).stream()
                .map(propostaMapper::toDTO)
                .toList();
    }
}
