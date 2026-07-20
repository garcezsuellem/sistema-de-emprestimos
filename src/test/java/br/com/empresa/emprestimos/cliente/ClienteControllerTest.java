package br.com.empresa.emprestimos.cliente;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.service.AtualizarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.BloqueioClienteService;
import br.com.empresa.emprestimos.cliente.application.service.BuscarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.CriarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.DadosFinanceirosClienteService;
import br.com.empresa.emprestimos.cliente.web.controller.ClienteController;
import br.com.empresa.emprestimos.shared.enums.SituacaoCliente;
import br.com.empresa.emprestimos.shared.exception.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private CriarClienteService criarClienteService;

    @MockitoBean
    private BuscarClienteService buscarClienteService;

    @MockitoBean
    private AtualizarClienteService atualizarClienteService;

    @MockitoBean
    private BloqueioClienteService bloqueioClienteService;

    @MockitoBean
    private DadosFinanceirosClienteService dadosFinanceirosClienteService;

    @Test
    void deveCriarClienteComDadosValidos() throws Exception {
        var request = new br.com.empresa.emprestimos.cliente.web.request.CriarClienteRequest(
                "João Silva", "111.444.777-35", LocalDate.of(1990, 1, 1),
                "joao@email.com", "11999998888"
        );

        ClienteDTO dtoRetornado = ClienteDTO.builder()
                .id(UUID.randomUUID())
                .nome("João Silva")
                .cpf("111.444.777-35")
                .situacao(SituacaoCliente.ATIVO)
                .build();

        when(criarClienteService.executar(any())).thenReturn(dtoRetornado);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    void deveRetornar400QuandoDadosInvalidos() throws Exception {
        var request = new br.com.empresa.emprestimos.cliente.web.request.CriarClienteRequest(
                "", "", null, "email-invalido", null
        );

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deveBuscarClientePorId() throws Exception {
        UUID id = UUID.randomUUID();
        ClienteDTO dto = ClienteDTO.builder().id(id).nome("Maria").situacao(SituacaoCliente.ATIVO).build();

        when(buscarClienteService.buscarPorId(id)).thenReturn(dto);

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void deveRetornar404QuandoClienteNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();

        when(buscarClienteService.buscarPorId(id)).thenThrow(new NotFoundException("Cliente", id));

        mockMvc.perform(get("/clientes/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveAtualizarCliente() throws Exception {
        UUID id = UUID.randomUUID();
        var request = new br.com.empresa.emprestimos.cliente.web.request.AtualizarClienteRequest(
                "Novo Nome", "novo@email.com", "11988887777"
        );

        ClienteDTO dtoAtualizado = ClienteDTO.builder().id(id).nome("Novo Nome").situacao(SituacaoCliente.ATIVO).build();

        when(atualizarClienteService.executar(any(UUID.class), any())).thenReturn(dtoAtualizado);

        mockMvc.perform(put("/clientes/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo Nome"));
    }
}