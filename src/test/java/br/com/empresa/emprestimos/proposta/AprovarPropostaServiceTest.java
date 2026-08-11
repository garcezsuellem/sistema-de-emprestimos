package br.com.empresa.emprestimos.proposta;

import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.application.service.AprovarPropostaService;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.shared.exception.BusinessRuleViolationException;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AprovarPropostaServiceTest {

    @Mock
    private PropostaRepository propostaRepository;

    @Mock
    private PropostaMapper propostaMapper;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private AprovarPropostaService aprovarPropostaService;

    @Test
    void deveAprovarPropostaPendenteDeAnalise() {
        UUID propostaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        PropostaEmprestimo proposta = new PropostaEmprestimo(
                UUID.randomUUID(), new BigDecimal("5000.00"), 10,
                new BigDecimal("0.02"), new BigDecimal("500.00"), new BigDecimal("5500.00"));

        when(propostaRepository.buscarPorId(propostaId)).thenReturn(Optional.of(proposta));
        when(propostaRepository.salvar(proposta)).thenReturn(proposta);
        when(propostaMapper.toDTO(proposta)).thenReturn(PropostaDTO.builder().id(propostaId).build());

        PropostaDTO resultado = aprovarPropostaService.executar(propostaId, usuarioId);

        assertThat(resultado).isNotNull();
    }

    @Test
    void deveLancarExcecaoQuandoPropostaNaoExiste() {
        UUID propostaId = UUID.randomUUID();

        when(propostaRepository.buscarPorId(propostaId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> aprovarPropostaService.executar(propostaId, UUID.randomUUID()))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void deveLancarExcecaoQuandoPropostaJaFoiAprovada() {
        UUID propostaId = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        PropostaEmprestimo proposta = new PropostaEmprestimo(
                UUID.randomUUID(), new BigDecimal("5000.00"), 10,
                new BigDecimal("0.02"), new BigDecimal("500.00"), new BigDecimal("5500.00"));

        proposta.aprovar(usuarioId);

        when(propostaRepository.buscarPorId(propostaId)).thenReturn(Optional.of(proposta));

        assertThatThrownBy(() -> aprovarPropostaService.executar(propostaId, usuarioId))
                .isInstanceOf(BusinessRuleViolationException.class);
    }
}
