package br.com.empresa.emprestimos.proposta;

import br.com.empresa.emprestimos.proposta.application.SimularPropostaService;
import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.service.AnalisarCreditoService;
import br.com.empresa.emprestimos.proposta.application.service.AprovarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.BuscarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.CriarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.RecusarPropostaService;
import br.com.empresa.emprestimos.proposta.web.controller.PropostaController;
import br.com.empresa.emprestimos.shared.enums.SituacaoProposta;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import br.com.empresa.emprestimos.proposta.application.SimularPropostaService;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PropostaController.class)
class PropostaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarPropostaService criarPropostaService;

    @MockitoBean
    private BuscarPropostaService buscarPropostaService;

    @MockitoBean
    private AprovarPropostaService aprovarPropostaService;

    @MockitoBean
    private RecusarPropostaService recusarPropostaService;

    @MockitoBean
    private AnalisarCreditoService analisarCreditoService;

    @MockitoBean
    private SimularPropostaService simularPropostaService;

    @Test
    void deveCriarPropostaComDadosValidos() throws Exception {
        var request = new br.com.empresa.emprestimos.proposta.web.request.CriarPropostaRequest(
                UUID.randomUUID(), new BigDecimal("5000.00"), 12, new BigDecimal("0.02"));

        PropostaDTO dtoRetornado = PropostaDTO.builder()
                .id(UUID.randomUUID())
                .clienteId(request.getClienteId())
                .situacao(SituacaoProposta.PENDENTE_ANALISE)
                .build();

        when(criarPropostaService.executar(any())).thenReturn(dtoRetornado);

        mockMvc.perform(post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.situacao").value("PENDENTE_ANALISE"));
    }

    @Test
    void deveRetornar400QuandoValorSolicitadoForNegativo() throws Exception {
        var request = new br.com.empresa.emprestimos.proposta.web.request.CriarPropostaRequest(
                UUID.randomUUID(), new BigDecimal("-100.00"), 12, new BigDecimal("0.02"));

        mockMvc.perform(post("/propostas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarPropostaPorId() throws Exception {
        UUID id = UUID.randomUUID();
        PropostaDTO dto = PropostaDTO.builder().id(id).situacao(SituacaoProposta.APROVADA).build();

        when(buscarPropostaService.buscarPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/propostas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.situacao").value("APROVADA"));
    }

    @Test
    void deveRetornar404QuandoPropostaNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarPropostaService.buscarPorId(id)).thenThrow(new NotFoundException("Proposta", id));

        mockMvc.perform(get("/propostas/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAprovarProposta() throws Exception {
        UUID id = UUID.randomUUID();
        UUID usuarioId = UUID.randomUUID();

        PropostaDTO dtoAprovado = PropostaDTO.builder().id(id).situacao(SituacaoProposta.APROVADA).build();

        when(aprovarPropostaService.executar(id, usuarioId)).thenReturn(dtoAprovado);

        mockMvc.perform(post("/propostas/{id}/aprovar", id).param("usuarioId", usuarioId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.situacao").value("APROVADA"));
    }
}
