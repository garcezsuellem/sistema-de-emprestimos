package br.com.empresa.emprestimos.proposta.infrastructure.persistence;

import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PropostaRepositoryAdapter implements PropostaRepository {

    private final PropostaJpaRepository jpaRepository;

    public PropostaRepositoryAdapter(PropostaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PropostaEmprestimo salvar(PropostaEmprestimo proposta) {
        return jpaRepository.save(proposta);
    }

    @Override
    public Optional<PropostaEmprestimo> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<PropostaEmprestimo> buscarPorClienteId(UUID clienteId) {
        return jpaRepository.findByClienteId(clienteId);
    }
}
