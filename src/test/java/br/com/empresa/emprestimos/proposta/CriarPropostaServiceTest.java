package br.com.empresa.emprestimos.proposta;

import br.com.empresa.emprestimos.proposta.application.dto.CriarPropostaRequest;
import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.mapper.PropostaMapper;
import br.com.empresa.emprestimos.proposta.application.service.CriarPropostaService;
import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.repository.PropostaRepository;
import br.com.empresa.emprestimos.proposta.domain.service.CalculadoraEmprestimo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CriarPropostaServiceTest {

    @Mock
    private PropostaRepository propostaRepository;

    @Spy
    private CalculadoraEmprestimo calculadoraEmprestimo = new CalculadoraEmprestimo();

    @Mock
    private PropostaMapper propostaMapper;

    @InjectMocks
    private CriarPropostaService criarPropostaService;

    @Test
    void deveCriarPropostaComValoresCalculadosCorretamente() {
        CriarPropostaRequest request = new CriarPropostaRequest(
                UUID.randomUUID(), new BigDecimal("10000.00"), 12, new BigDecimal("0.02"));


        PropostaEmprestimo propostaSalva = new PropostaEmprestimo(
                request.getClienteId(), request.getValorSolicitado(), request.getQuantidadeParcelas(),
                request.getTaxaJurosMensal(), new BigDecimal("945.60"), new BigDecimal("11347.20"));


        when(propostaRepository.salvar(any(PropostaEmprestimo.class))).thenReturn(propostaSalva);
        when(propostaMapper.toDTO(propostaSalva)).thenReturn(
                PropostaDTO.builder().clienteId(request.getClienteId()).build());


        PropostaDTO resultado = criarPropostaService.executar(request);

        assertThat(resultado.getClienteId()).isEqualTo(request.getClienteId());
    }
}
