package br.com.empresa.emprestimos.proposta.domain.entity;

import br.com.empresa.emprestimos.shared.enums.SituacaoProposta;
import br.com.empresa.emprestimos.shared.exception.BusinessRuleViolationException;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "propostas_emprestimo")
public class PropostaEmprestimo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "valor_solicitado", precision = 19, scale = 2, nullable = false)
    private BigDecimal valorSolicitado;

    @Column(name = "quantidade_parcelas", nullable = false)
    private Integer quantidadeParcelas;

    @Column(name = "taxa_juros_mensal", precision = 8, scale = 4, nullable = false)
    private BigDecimal taxaJurosMensal;

    @Column(name = "valor_parcela_calculado", precision = 19, scale = 2, nullable = false)
    private BigDecimal valorParcelaCalculado;

    @Column(name = "valor_total_calculado", precision = 19, scale = 2, nullable = false)
    private BigDecimal valorTotalCalculado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SituacaoProposta situacao;

    @Column(name = "criada_em", nullable = false, updatable = false)
    private OffsetDateTime criadaEm;

    @Column(name = "analisada_em")
    private OffsetDateTime analisadaEm;

    @Column(name = "analisada_por_usuario_id")
    private UUID analisadaPorUsuarioId;

    @Column(name = "motivo_recusa", length = 500)
    private String motivoRecusa;

    protected PropostaEmprestimo() {
    }

    public PropostaEmprestimo(UUID clienteId, BigDecimal valorSolicitado, Integer quantidadeParcelas,
                              BigDecimal taxaJurosMensal, BigDecimal valorParcelaCalculado,
                              BigDecimal valorTotalCalculado) {
        this.clienteId = clienteId;
        this.valorSolicitado = valorSolicitado;
        this.quantidadeParcelas = quantidadeParcelas;
        this.taxaJurosMensal = taxaJurosMensal;
        this.valorParcelaCalculado = valorParcelaCalculado;
        this.valorTotalCalculado = valorTotalCalculado;
        this.situacao = SituacaoProposta.PENDENTE_ANALISE;
        this.criadaEm = OffsetDateTime.now();
    }

    public void aprovar(UUID usuarioId) {
        if (situacao != SituacaoProposta.PENDENTE_ANALISE) {
            throw new BusinessRuleViolationException(
                    "Proposta só pode ser aprovada quando está PENDENTE_ANALISE. Situação atual: " + situacao);
        }
        this.situacao = SituacaoProposta.APROVADA;
        this.analisadaPorUsuarioId = usuarioId;
        this.analisadaEm = OffsetDateTime.now();
    }

    public void recusar(UUID usuarioId, String motivo) {
        if (situacao != SituacaoProposta.PENDENTE_ANALISE) {
            throw new BusinessRuleViolationException(
                    "Proposta só pode ser recusada quando está PENDENTE_ANALISE. Situação atual: " + situacao);
        }
        this.situacao = SituacaoProposta.RECUSADA;
        this.analisadaPorUsuarioId = usuarioId;
        this.analisadaEm = OffsetDateTime.now();
        this.motivoRecusa = motivo;
    }

    public boolean estaAprovada() {
        return this.situacao == SituacaoProposta.APROVADA;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public BigDecimal getTaxaJurosMensal() {
        return taxaJurosMensal;
    }

    public BigDecimal getValorParcelaCalculado() {
        return valorParcelaCalculado;
    }

    public BigDecimal getValorTotalCalculado() {
        return valorTotalCalculado;
    }

    public SituacaoProposta getSituacao() {
        return situacao;
    }

    public OffsetDateTime getCriadaEm() {
        return criadaEm;
    }

    public OffsetDateTime getAnalisadaEm() {
        return analisadaEm;
    }

    public UUID getAnalisadaPorUsuarioId() {
        return analisadaPorUsuarioId;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }
}
