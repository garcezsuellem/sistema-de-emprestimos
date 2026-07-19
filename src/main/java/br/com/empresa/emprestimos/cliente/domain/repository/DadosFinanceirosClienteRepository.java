package br.com.empresa.emprestimos.cliente.domain.repository;

import br.com.empresa.emprestimos.cliente.domain.entity.DadosFinanceirosCliente;

import java.util.Optional;
import java.util.UUID;

public interface DadosFinanceirosClienteRepository {

    DadosFinanceirosCliente salvar(DadosFinanceirosCliente dadosFinanceiros);

    Optional<DadosFinanceirosCliente> buscarPorClienteId(UUID clienteId);
}
