package br.com.empresa.emprestimos.proposta.domain.entity;

import br.com.empresa.emprestimos.shared.enums.ResultadoAnalise;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "analises_credito")
public class AnaliseCredito {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "proposta_id", nullable = false)
    private UUID propostaId;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "analisada_por_usuario_id")
    private UUID analisadaPorUsuarioId;

    @Column(name = "score_credito")
    private Integer scoreCredito;

    @Column(name = "renda_considerada", precision = 19, scale = 2)
    private BigDecimal rendaConsiderada;

    @Column(name = "comprometimento_mensal", precision = 19, scale = 2)
    private BigDecimal comprometimentoMensal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ResultadoAnalise resultado;

    @Column(length = 1000)
    private String observacao;

    @Column(name = "realizada_em", nullable = false, updatable = false)
    private OffsetDateTime realizadaEm;

    protected AnaliseCredito() {
    }

    public AnaliseCredito(UUID propostaId, UUID clienteId, UUID analisadaPorUsuarioId, Integer scoreCredito,
                          BigDecimal rendaConsiderada, BigDecimal comprometimentoMensal,
                          ResultadoAnalise resultado, String observacao) {
        this.propostaId = propostaId;
        this.clienteId = clienteId;
        this.analisadaPorUsuarioId = analisadaPorUsuarioId;
        this.scoreCredito = scoreCredito;
        this.rendaConsiderada = rendaConsiderada;
        this.comprometimentoMensal = comprometimentoMensal;
        this.resultado = resultado;
        this.observacao = observacao;
        this.realizadaEm = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getPropostaId() {
        return propostaId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public UUID getAnalisadaPorUsuarioId() {
        return analisadaPorUsuarioId;
    }

    public Integer getScoreCredito() {
        return scoreCredito;
    }

    public BigDecimal getRendaConsiderada() {
        return rendaConsiderada;
    }

    public BigDecimal getComprometimentoMensal() {
        return comprometimentoMensal;
    }

    public ResultadoAnalise getResultado() {
        return resultado;
    }

    public String getObservacao() {
        return observacao;
    }

    public OffsetDateTime getRealizadaEm() {
        return realizadaEm;
    }
}