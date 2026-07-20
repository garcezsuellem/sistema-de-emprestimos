package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.application.dto.EnderecoClienteDTO;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.domain.entity.EnderecoCliente;
import br.com.empresa.emprestimos.cliente.domain.repository.EnderecoClienteRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class EnderecoClienteService {

    private final EnderecoClienteRepository repository;
    private final ClienteMapper mapper;

    public EnderecoClienteService(EnderecoClienteRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public EnderecoClienteDTO salvar(EnderecoClienteDTO dto) {
        EnderecoCliente entidade = mapper.toEntity(dto);
        EnderecoCliente salvo = repository.salvar(entidade);
        return mapper.toDTO(salvo);
    }

    @Transactional(readOnly = true)
    public EnderecoClienteDTO buscarPorClienteId(UUID clienteId) {
        EnderecoCliente entidade = repository.buscarPorClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException("EnderecoCliente", clienteId));
        return mapper.toDTO(entidade);
    }
}
