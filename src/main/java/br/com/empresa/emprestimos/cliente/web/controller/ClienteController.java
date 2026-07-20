package br.com.empresa.emprestimos.cliente.web.controller;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.dto.DadosFinanceirosClienteDTO;
import br.com.empresa.emprestimos.cliente.application.service.AtualizarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.BloqueioClienteService;
import br.com.empresa.emprestimos.cliente.application.service.BuscarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.CriarClienteService;
import br.com.empresa.emprestimos.cliente.application.service.DadosFinanceirosClienteService;
import br.com.empresa.emprestimos.cliente.web.request.AtualizarClienteRequest;
import br.com.empresa.emprestimos.cliente.web.request.CriarClienteRequest;
import br.com.empresa.emprestimos.cliente.web.response.ClienteResponse;
import br.com.empresa.emprestimos.cliente.web.response.DadosFinanceirosClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final CriarClienteService criarClienteService;
    private final BuscarClienteService buscarClienteService;
    private final AtualizarClienteService atualizarClienteService;
    private final BloqueioClienteService bloqueioClienteService;
    private final DadosFinanceirosClienteService dadosFinanceirosClienteService;

    public ClienteController(CriarClienteService criarClienteService,
                             BuscarClienteService buscarClienteService,
                             AtualizarClienteService atualizarClienteService,
                             BloqueioClienteService bloqueioClienteService,
                             DadosFinanceirosClienteService dadosFinanceirosClienteService) {
        this.criarClienteService = criarClienteService;
        this.buscarClienteService = buscarClienteService;
        this.atualizarClienteService = atualizarClienteService;
        this.bloqueioClienteService = bloqueioClienteService;
        this.dadosFinanceirosClienteService = dadosFinanceirosClienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> criar(@Valid @RequestBody CriarClienteRequest request) {
        var dto = new br.com.empresa.emprestimos.cliente.application.dto.CriarClienteRequest(
                request.getNome(), request.getCpf(), request.getDataNascimento(),
                request.getEmail(), request.getTelefone()
        );

        ClienteDTO clienteDTO = criarClienteService.executar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(clienteDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponse> buscarPorId(@PathVariable UUID id) {
        ClienteDTO clienteDTO = buscarClienteService.buscarPorId(id);
        return ResponseEntity.ok(toResponse(clienteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponse> atualizar(@PathVariable UUID id,
                                                     @Valid @RequestBody AtualizarClienteRequest request) {
        var dto = new br.com.empresa.emprestimos.cliente.application.dto.AtualizarClienteRequest(
                request.getNome(), request.getEmail(), request.getTelefone()
        );

        ClienteDTO clienteDTO = atualizarClienteService.executar(id, dto);

        return ResponseEntity.ok(toResponse(clienteDTO));
    }

    @PostMapping("/{id}/bloqueio")
    public ResponseEntity<Void> bloquear(@PathVariable UUID id) {
        bloqueioClienteService.bloquear(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/dados-financeiros")
    public ResponseEntity<DadosFinanceirosClienteResponse> buscarDadosFinanceiros(@PathVariable UUID id) {
        DadosFinanceirosClienteDTO dto = dadosFinanceirosClienteService.buscarPorClienteId(id);

        DadosFinanceirosClienteResponse response = DadosFinanceirosClienteResponse.builder()
                .id(dto.getId())
                .clienteId(dto.getClienteId())
                .rendaMensal(dto.getRendaMensal())
                .outrasRendas(dto.getOutrasRendas())
                .ocupacao(dto.getOcupacao())
                .empregador(dto.getEmpregador())
                .atualizadoEm(dto.getAtualizadoEm())
                .build();

        return ResponseEntity.ok(response);
    }

    private ClienteResponse toResponse(ClienteDTO dto) {
        return ClienteResponse.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .cpf(dto.getCpf())
                .dataNascimento(dto.getDataNascimento())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .situacao(dto.getSituacao())
                .cadastradoEm(dto.getCadastradoEm())
                .build();
    }
}
