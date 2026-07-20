package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.application.dto.AtualizarClienteRequest;
import br.com.empresa.emprestimos.cliente.application.dto.AtualizarClienteRequest;
import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class AtualizarClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public AtualizarClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO executar(UUID id, AtualizarClienteRequest request) {
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new NotFoundException("Cliente", id));

        cliente.atualizarDados(request.getNome(), request.getEmail(), request.getTelefone());

        Cliente clienteAtualizado = clienteRepository.salvar(cliente);

        return clienteMapper.toDTO(clienteAtualizado);
    }
}
