package br.com.empresa.emprestimos.cliente.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "enderecos_clientes")
public class EnderecoCliente {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(length = 9, nullable = false)
    private String cep;

    @Column(length = 150, nullable = false)
    private String logradouro;

    @Column(length = 20, nullable = false)
    private String numero;

    @Column(length = 100)
    private String complemento;

    @Column(length = 100, nullable = false)
    private String bairro;

    @Column(length = 100, nullable = false)
    private String cidade;

    @Column(length = 2, nullable = false)
    private String uf;

    protected EnderecoCliente() {
    }

    public EnderecoCliente(UUID clienteId, String cep, String logradouro, String numero,
                           String complemento, String bairro, String cidade, String uf) {
        this.clienteId = clienteId;
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }
}
