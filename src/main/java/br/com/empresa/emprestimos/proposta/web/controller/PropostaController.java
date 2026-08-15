package br.com.empresa.emprestimos.proposta.web.controller;

import br.com.empresa.emprestimos.proposta.application.SimularPropostaService;
import br.com.empresa.emprestimos.proposta.application.dto.AnaliseCreditoDTO;
import br.com.empresa.emprestimos.proposta.application.dto.PropostaDTO;
import br.com.empresa.emprestimos.proposta.application.service.AnalisarCreditoService;
import br.com.empresa.emprestimos.proposta.application.service.AprovarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.BuscarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.CriarPropostaService;
import br.com.empresa.emprestimos.proposta.application.service.RecusarPropostaService;
import br.com.empresa.emprestimos.proposta.domain.service.CalculadoraEmprestimo;
import br.com.empresa.emprestimos.proposta.domain.service.PlanoPagamento;
import br.com.empresa.emprestimos.proposta.web.request.AnalisarCreditoRequest;
import br.com.empresa.emprestimos.proposta.web.request.CriarPropostaRequest;
import br.com.empresa.emprestimos.proposta.web.request.RecusarPropostaRequest;
import br.com.empresa.emprestimos.proposta.web.request.SimularPropostaRequest;
import br.com.empresa.emprestimos.proposta.web.response.AnaliseCreditoResponse;
import br.com.empresa.emprestimos.proposta.web.response.PropostaResponse;
import br.com.empresa.emprestimos.proposta.web.response.SimulacaoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    private final CriarPropostaService criarPropostaService;
    private final BuscarPropostaService buscarPropostaService;
    private final AprovarPropostaService aprovarPropostaService;
    private final RecusarPropostaService recusarPropostaService;
    private final AnalisarCreditoService analisarCreditoService;
    private final SimularPropostaService simularPropostaService;

    public PropostaController(CriarPropostaService criarPropostaService,
                              BuscarPropostaService buscarPropostaService,
                              AprovarPropostaService aprovarPropostaService,
                              RecusarPropostaService recusarPropostaService,
                              AnalisarCreditoService analisarCreditoService,
                              SimularPropostaService simularPropostaService) {
        this.criarPropostaService = criarPropostaService;
        this.buscarPropostaService = buscarPropostaService;
        this.aprovarPropostaService = aprovarPropostaService;
        this.recusarPropostaService = recusarPropostaService;
        this.analisarCreditoService = analisarCreditoService;
        this.simularPropostaService = simularPropostaService;
    }

    @PostMapping
    public ResponseEntity<PropostaResponse> criar(@Valid @RequestBody CriarPropostaRequest request) {
        var dto = new br.com.empresa.emprestimos.proposta.application.dto.CriarPropostaRequest(
                request.getClienteId(), request.getValorSolicitado(),
                request.getQuantidadeParcelas(), request.getTaxaJurosMensal()
        );

        PropostaDTO propostaDTO = criarPropostaService.executar(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(propostaDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PropostaResponse> buscarPorId(@PathVariable UUID id) {
        PropostaDTO propostaDTO = buscarPropostaService.buscarPorId(id);
        return ResponseEntity.ok(toResponse(propostaDTO));
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponse>> listar(@RequestParam UUID clienteId) {
        List<PropostaResponse> propostas = buscarPropostaService.buscarPorClienteId(clienteId).stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(propostas);
    }

    @PostMapping("/{id}/aprovar")
    public ResponseEntity<PropostaResponse> aprovar(@PathVariable UUID id, @RequestParam UUID usuarioId) {
        PropostaDTO propostaDTO = aprovarPropostaService.executar(id, usuarioId);
        return ResponseEntity.ok(toResponse(propostaDTO));
    }

    @PostMapping("/{id}/recusar")
    public ResponseEntity<PropostaResponse> recusar(@PathVariable UUID id, @RequestParam UUID usuarioId,
                                                    @Valid @RequestBody RecusarPropostaRequest request) {
        PropostaDTO propostaDTO = recusarPropostaService.executar(id, usuarioId, request.getMotivo());
        return ResponseEntity.ok(toResponse(propostaDTO));
    }

    @PostMapping("/simular")
    public ResponseEntity<SimulacaoResponse> simular(@Valid @RequestBody SimularPropostaRequest request) {
        var dto = new br.com.empresa.emprestimos.proposta.application.dto.SimularPropostaRequest(
                request.getValorSolicitado(), request.getQuantidadeParcelas(), request.getTaxaJurosMensal()
        );

        PlanoPagamento plano = simularPropostaService.executar(dto);

        List<SimulacaoResponse.ItemSimulacao> itens = plano.parcelas().stream()
                .map(item -> SimulacaoResponse.ItemSimulacao.builder()
                        .numero(item.numero())
                        .vencimento(item.vencimento())
                        .valorPrincipal(item.valorPrincipal())
                        .valorJuros(item.valorJuros())
                        .valorTotal(item.valorTotal())
                        .build())
                .toList();

        SimulacaoResponse response = SimulacaoResponse.builder()
                .valorTotal(plano.valorTotal())
                .parcelas(itens)
                .build();

        return ResponseEntity.ok(response);
    }
    @PostMapping("/{id}/analise")
    public ResponseEntity<AnaliseCreditoResponse> analisar(@PathVariable UUID id, @RequestParam UUID usuarioId,
                                                           @Valid @RequestBody AnalisarCreditoRequest request) {
        var dto = new br.com.empresa.emprestimos.proposta.application.dto.AnalisarCreditoRequest(
                request.getScoreCredito(), request.getRendaConsiderada(), request.getObservacao()
        );

        AnaliseCreditoDTO analiseDTO = analisarCreditoService.executar(id, usuarioId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(analiseDTO));
    }

    @GetMapping("/{id}/analise")
    public ResponseEntity<AnaliseCreditoResponse> buscarAnalise(@PathVariable UUID id) {
        AnaliseCreditoDTO analiseDTO = analisarCreditoService.buscarPorPropostaId(id);
        return ResponseEntity.ok(toResponse(analiseDTO));
    }

    private PropostaResponse toResponse(PropostaDTO dto) {
        return PropostaResponse.builder()
                .id(dto.getId())
                .clienteId(dto.getClienteId())
                .valorSolicitado(dto.getValorSolicitado())
                .quantidadeParcelas(dto.getQuantidadeParcelas())
                .taxaJurosMensal(dto.getTaxaJurosMensal())
                .valorParcelaCalculado(dto.getValorParcelaCalculado())
                .valorTotalCalculado(dto.getValorTotalCalculado())
                .situacao(dto.getSituacao())
                .criadaEm(dto.getCriadaEm())
                .analisadaEm(dto.getAnalisadaEm())
                .analisadaPorUsuarioId(dto.getAnalisadaPorUsuarioId())
                .motivoRecusa(dto.getMotivoRecusa())
                .build();
    }

    private AnaliseCreditoResponse toResponse(AnaliseCreditoDTO dto) {
        return AnaliseCreditoResponse.builder()
                .id(dto.getId())
                .propostaId(dto.getPropostaId())
                .clienteId(dto.getClienteId())
                .analisadaPorUsuarioId(dto.getAnalisadaPorUsuarioId())
                .scoreCredito(dto.getScoreCredito())
                .rendaConsiderada(dto.getRendaConsiderada())
                .comprometimentoMensal(dto.getComprometimentoMensal())
                .resultado(dto.getResultado())
                .observacao(dto.getObservacao())
                .realizadaEm(dto.getRealizadaEm())
                .build();
    }
}
