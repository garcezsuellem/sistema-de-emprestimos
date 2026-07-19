package br.com.empresa.emprestimos.cliente.domain.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "dados_financeiros_clientes")
public class DadosFinanceirosCliente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(name = "renda_mensal", precision = 19, scale = 2, nullable = false)
    private BigDecimal rendaMensal;

    @Column(name = "outras_rendas", precision = 19, scale = 2)
    private BigDecimal outrasRendas;

    @Column(nullable = false, length = 100)
    private String ocupacao;

    @Column(length = 150)
    private String empregador;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    protected DadosFinanceirosCliente() {
    }

    public DadosFinanceirosCliente(UUID clienteId, BigDecimal rendaMensal, BigDecimal outrasRendas,
                                   String ocupacao, String empregador) {
        this.clienteId = clienteId;
        this.rendaMensal = rendaMensal;
        this.outrasRendas = outrasRendas;
        this.ocupacao = ocupacao;
        this.empregador = empregador;
        this.atualizadoEm = OffsetDateTime.now();
    }

    public BigDecimal rendaTotal() {
        BigDecimal outras = outrasRendas != null ? outrasRendas : BigDecimal.ZERO;
        return rendaMensal.add(outras);
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public BigDecimal getRendaMensal() {
        return rendaMensal;
    }

    public BigDecimal getOutrasRendas() {
        return outrasRendas;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public String getEmpregador() {
        return empregador;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }
}
