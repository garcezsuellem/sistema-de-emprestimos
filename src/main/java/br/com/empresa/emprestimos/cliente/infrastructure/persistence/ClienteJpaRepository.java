package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteJpaRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByCpf(String cpf);

    boolean existsByEmail(String email);
}
