package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.application.dto.DadosFinanceirosClienteDTO;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.domain.entity.DadosFinanceirosCliente;
import br.com.empresa.emprestimos.cliente.domain.repository.DadosFinanceirosClienteRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class DadosFinanceirosClienteService {

    private final DadosFinanceirosClienteRepository repository;
    private final ClienteMapper mapper;

    public DadosFinanceirosClienteService(DadosFinanceirosClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public DadosFinanceirosClienteDTO salvar(DadosFinanceirosClienteDTO dto) {
        DadosFinanceirosCliente entidade = mapper.toEntity(dto);
        DadosFinanceirosCliente salvo = repository.salvar(entidade);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public DadosFinanceirosClienteDTO buscarPorClienteId(UUID clienteId) {
        DadosFinanceirosCliente entidade = repository.buscarPorClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("DadosFinanceirosCliente", clienteId));
        return mapper.toDTO(entidade);
    }
}
