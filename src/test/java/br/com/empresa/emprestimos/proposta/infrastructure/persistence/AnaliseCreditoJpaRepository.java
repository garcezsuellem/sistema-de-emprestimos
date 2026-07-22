package br.com.empresa.emprestimos.proposta.infrastructure.persistence;

import br.com.empresa.emprestimos.proposta.domain.entity.AnaliseCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnaliseCreditoJpaRepository extends JpaRepository<AnaliseCredito, UUID> {

    Optional<AnaliseCredito> findByPropostaId(UUID propostaId);
}
