package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BuscarClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public BuscarClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO buscarPorId(UUID id) {
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Cliente", id));

        return clienteMapper.toDTO(cliente);
    }

    public ClienteDTO buscarPorCpf(String cpf) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Cliente", cpf));

        return clienteMapper.toDTO(cliente);
    }
}
