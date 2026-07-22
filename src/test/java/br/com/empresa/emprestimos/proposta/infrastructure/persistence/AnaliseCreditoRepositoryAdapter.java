package br.com.empresa.emprestimos.proposta.infrastructure.persistence;

import br.com.empresa.emprestimos.proposta.domain.entity.AnaliseCredito;
import br.com.empresa.emprestimos.proposta.domain.repository.AnaliseCreditoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AnaliseCreditoRepositoryAdapter implements AnaliseCreditoRepository {

    private final AnaliseCreditoJpaRepository jpaRepository;

    public AnaliseCreditoRepositoryAdapter(AnaliseCreditoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AnaliseCredito salvar(AnaliseCredito analiseCredito) {
        return jpaRepository.save(analiseCredito);
    }

    @Override
    public Optional<AnaliseCredito> buscarPorPropostaId(UUID propostaId) {
        return jpaRepository.findByPropostaId(propostaId);
    }
}
