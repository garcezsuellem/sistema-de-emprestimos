package br.com.empresa.emprestimos.cliente.domain.repository;

import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    Optional<Cliente> buscarPorCpf(String cpf);

    boolean existePorEmail(String email);
}