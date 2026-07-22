package br.com.empresa.emprestimos.proposta.infrastructure.persistence;

import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PropostaJpaRepository extends JpaRepository<PropostaEmprestimo, UUID> {

    List<PropostaEmprestimo> findByClienteId(UUID clienteId);
}
