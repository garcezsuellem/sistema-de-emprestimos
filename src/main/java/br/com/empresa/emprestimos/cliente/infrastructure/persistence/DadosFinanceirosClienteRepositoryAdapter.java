package br.com.empresa.emprestimos.cliente.infrastructure.persistence;

import br.com.empresa.emprestimos.cliente.domain.entity.DadosFinanceirosCliente;
import br.com.empresa.emprestimos.cliente.domain.repository.DadosFinanceirosClienteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DadosFinanceirosClienteRepositoryAdapter implements DadosFinanceirosClienteRepository {

    private final DadosFinanceirosClienteJpaRepository jpaRepository;

    public DadosFinanceirosClienteRepositoryAdapter(DadosFinanceirosClienteJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public DadosFinanceirosCliente salvar(DadosFinanceirosCliente dadosFinanceiros) {
        return jpaRepository.save(dadosFinanceiros);
    }

    @Override
    public Optional<DadosFinanceirosCliente> buscarPorClienteId(UUID clienteId) {
        return jpaRepository.findByClienteId(clienteId);
    }
}
