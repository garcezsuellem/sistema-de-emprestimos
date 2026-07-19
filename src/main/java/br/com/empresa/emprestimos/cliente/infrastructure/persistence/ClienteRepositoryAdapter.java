package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ClienteRepositoryAdapter implements ClienteRepository {

    private final ClienteJpaRepository jpaRepository;

    public ClienteRepositoryAdapter(ClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        return jpaRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> buscarPorId(UUID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return jpaRepository.findByCpf(cpf);
    }

    @Override
    public boolean existePorEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
