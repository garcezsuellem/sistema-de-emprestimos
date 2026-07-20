package br.com.empresa.emprestimos.cliente.application.service;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.dto.CriarClienteRequest;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import br.com.empresa.emprestimos.shared.exception.BusinessRuleViolationException;
import br.com.empresa.emprestimos.shared.exception.DuplicateResourceException;
import br.com.empresa.emprestimos.shared.util.CpfValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CriarClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public CriarClienteService(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public ClienteDTO executar(CriarClienteRequest request) {
        if (!CpfValidator.isValid(request.getCpf())) {
            throw new BusinessRuleViolationException("CPF inválido: " + request.getCpf());
        }

        if (clienteRepository.buscarPorCpf(request.getCpf()).isPresent()) {
            throw new DuplicateResourceException("Cliente", "cpf", request.getCpf());
        }

        if (request.getEmail() != null && clienteRepository.existePorEmail(request.getEmail())) {
            throw new DuplicateResourceException("Cliente", "email", request.getEmail());
        }

        Cliente cliente = new Cliente(
                request.getNome(),
                request.getCpf(),
                request.getDataNascimento(),
                request.getEmail(),
                request.getTelefone()
        );

        Cliente clienteSalvo = clienteRepository.salvar(cliente);

        return clienteMapper.toDTO(clienteSalvo);
    }
}
