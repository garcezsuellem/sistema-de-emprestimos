package br.com.empresa.emprestimos.cliente;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.dto.CriarClienteRequest;
import br.com.empresa.emprestimos.cliente.application.mapper.ClienteMapper;
import br.com.empresa.emprestimos.cliente.application.service.CriarClienteService;
import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.repository.ClienteRepository;
import br.com.empresa.emprestimos.shared.exception.BusinessRuleViolationException;
import br.com.empresa.emprestimos.shared.exception.DuplicateResourceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private CriarClienteService criarClienteService;

    @Test
    void deveLancarExcecaoQuandoCpfInvalido() {
        CriarClienteRequest request = new CriarClienteRequest(
                "João Silva", "111.111.111-11", LocalDate.of(1990, 1, 1),
                "joao@email.com", "11999998888"
        );

        assertThatThrownBy(() -> criarClienteService.executar(request))
                .isInstanceOf(BusinessRuleViolationException.class)
                .hasMessageContaining("CPF inválido");
    }

    @Test
    void deveLancarExcecaoQuandoCpfDuplicado() {
        CriarClienteRequest request = new CriarClienteRequest(
                "João Silva", "111.444.777-35", LocalDate.of(1990, 1, 1),
                "joao@email.com", "11999998888"
        );

        when(clienteRepository.buscarPorCpf(request.getCpf()))
                .thenReturn(Optional.of(new Cliente("Outro", "111.444.777-35", LocalDate.now(), "a@a.com", "123")));

        assertThatThrownBy(() -> criarClienteService.executar(request))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    void deveLancarExcecaoQuandoEmailDuplicado() {
        CriarClienteRequest request = new CriarClienteRequest(
                "João Silva", "111.444.777-35", LocalDate.of(1990, 1, 1),
                "joao@email.com", "11999998888"
        );

        when(clienteRepository.buscarPorCpf(request.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.existePorEmail(request.getEmail())).thenReturn(true);

        assertThatThrownBy(() -> criarClienteService.executar(request))
                .isInstanceOf(DuplicateResourceException.class);
    }

    @Test
    void deveCriarClienteComSucesso() {
        CriarClienteRequest request = new CriarClienteRequest(
                "João Silva", "111.444.777-35", LocalDate.of(1990, 1, 1),
                "joao@email.com", "11999998888"
        );

        Cliente clienteSalvo = new Cliente("João Silva", "111.444.777-35",
                LocalDate.of(1990, 1, 1), "joao@email.com", "11999998888");

        when(clienteRepository.buscarPorCpf(request.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.existePorEmail(request.getEmail())).thenReturn(false);
        when(clienteRepository.salvar(any(Cliente.class))).thenReturn(clienteSalvo);
        when(clienteMapper.toDTO(clienteSalvo)).thenReturn(
                ClienteDTO.builder().nome("João Silva").cpf("111.444.777-35").build()
        );

        ClienteDTO resultado = criarClienteService.executar(request);

        assertThat(resultado.getNome()).isEqualTo("João Silva");
    }
}
