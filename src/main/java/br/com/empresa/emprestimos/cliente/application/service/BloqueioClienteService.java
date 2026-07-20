package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class BloqueioClienteService {

    private final ClienteRepository clienteRepository;

    public BloqueioClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void bloquear(UUID clienteId) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente", clienteId));

        cliente.bloquear();
        clienteRepository.salvar(cliente);
    }

    public void desbloquear(UUID clienteId) {
        Cliente cliente = clienteRepository.buscarPorId(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente", clienteId));

        cliente.ativar();
        clienteRepository.salvar(cliente);
    }
}
