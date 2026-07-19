package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.EnderecoCliente;
import br.com.empresa.emprestimos.cliente.domain.repository.EnderecoClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class EnderecoClienteRepositoryAdapter implements EnderecoClienteRepository {

    private final EnderecoClienteJpaRepository jpaRepository;

    public EnderecoClienteRepositoryAdapter(EnderecoClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public EnderecoCliente salvar(EnderecoCliente endereco) {
        return jpaRepository.save(endereco);
    }

    @Override
    public Optional<EnderecoCliente> buscarPorClienteId(UUID clienteId) {
        return jpaRepository.findByClienteId(clienteId);
    }
}
