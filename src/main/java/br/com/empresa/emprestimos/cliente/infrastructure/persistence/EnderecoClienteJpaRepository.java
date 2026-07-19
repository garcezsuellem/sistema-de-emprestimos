package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.EnderecoCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnderecoClienteJpaRepository extends JpaRepository<EnderecoCliente, UUID> {

    Optional<EnderecoCliente> findByClienteId(UUID clienteId);
}
