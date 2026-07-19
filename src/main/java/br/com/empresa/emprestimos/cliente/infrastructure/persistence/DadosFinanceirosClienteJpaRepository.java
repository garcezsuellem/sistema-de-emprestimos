package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.DadosFinanceirosCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DadosFinanceirosClienteJpaRepository extends JpaRepository<DadosFinanceirosCliente, UUID> {

    Optional<DadosFinanceirosCliente> findByClienteId(UUID clienteId);
}
