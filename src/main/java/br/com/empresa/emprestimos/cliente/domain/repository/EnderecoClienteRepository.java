package br.com.empresa.emprestimos.cliente.domain.repository;

import br.com.empresa.emprestimos.cliente.domain.entity.EnderecoCliente;

import java.util.Optional;
import java.util.UUID;

public interface EnderecoClienteRepository {

    EnderecoCliente salvar(EnderecoCliente endereco);

    Optional<EnderecoCliente> buscarPorClienteId(UUID clienteId);
}
