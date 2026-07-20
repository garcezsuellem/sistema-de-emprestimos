package br.com.empresa.emprestimos.cliente.domain.entity;

import br.com.empresa.emprestimos.shared.enums.SituacaoCliente;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(unique = true, length = 14, nullable = false)
    private String cpf;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SituacaoCliente situacao;

    @Column(name = "cadastrado_em", nullable = false, updatable = false)
    private OffsetDateTime cadastradoEm;

    protected Cliente() {
    }

    public Cliente(String nome, String cpf, LocalDate dataNascimento, String email, String telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.telefone = telefone;
        this.situacao = SituacaoCliente.ATIVO;
        this.cadastradoEm = OffsetDateTime.now();
    }

    public void bloquear() {
        this.situacao = SituacaoCliente.BLOQUEADO;
    }

    public void ativar() {
        this.situacao = SituacaoCliente.ATIVO;
    }

    public boolean podeContratarEmprestimo() {
        return this.situacao == SituacaoCliente.ATIVO;
    }
    public void atualizarDados(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public SituacaoCliente getSituacao() {
        return situacao;
    }

    public OffsetDateTime getCadastradoEm() {
        return cadastradoEm;
    }
}