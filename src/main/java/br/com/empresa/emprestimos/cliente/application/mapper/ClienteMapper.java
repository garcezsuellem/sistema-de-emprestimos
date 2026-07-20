package br.com.empresa.emprestimos.cliente.application.mapper;

import br.com.empresa.emprestimos.cliente.application.dto.ClienteDTO;
import br.com.empresa.emprestimos.cliente.application.dto.DadosFinanceirosClienteDTO;
import br.com.empresa.emprestimos.cliente.application.dto.EnderecoClienteDTO;
import br.com.empresa.emprestimos.cliente.domain.entity.Cliente;
import br.com.empresa.emprestimos.cliente.domain.entity.DadosFinanceirosCliente;
import br.com.empresa.emprestimos.cliente.domain.entity.EnderecoCliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteDTO toDTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .dataNascimento(cliente.getDataNascimento())
                .email(cliente.getEmail())
                .telefone(cliente.getTelefone())
                .situacao(cliente.getSituacao())
                .cadastradoEm(cliente.getCadastradoEm())
                .build();
    }

    public DadosFinanceirosClienteDTO toDTO(DadosFinanceirosCliente dados) {
        return DadosFinanceirosClienteDTO.builder()
                .id(dados.getId())
                .clienteId(dados.getClienteId())
                .rendaMensal(dados.getRendaMensal())
                .outrasRendas(dados.getOutrasRendas())
                .ocupacao(dados.getOcupacao())
                .empregador(dados.getEmpregador())
                .atualizadoEm(dados.getAtualizadoEm())
                .build();
    }

    public EnderecoClienteDTO toDTO(EnderecoCliente endereco) {
        return EnderecoClienteDTO.builder()
                .id(endereco.getId())
                .clienteId(endereco.getClienteId())
                .cep(endereco.getCep())
                .logradouro(endereco.getLogradouro())
                .numero(endereco.getNumero())
                .complemento(endereco.getComplemento())
                .bairro(endereco.getBairro())
                .cidade(endereco.getCidade())
                .uf(endereco.getUf())
                .build();
    }
    public DadosFinanceirosCliente toEntity(DadosFinanceirosClienteDTO dto) {
        return new DadosFinanceirosCliente(
                dto.getClienteId(),
                dto.getRendaMensal(),
                dto.getOutrasRendas(),
                dto.getOcupacao(),
                dto.getEmpregador()
        );
    }

    public EnderecoCliente toEntity(EnderecoClienteDTO dto) {
        return new EnderecoCliente(
                dto.getClienteId(),
                dto.getCep(),
                dto.getLogradouro(),
                dto.getNumero(),
                dto.getComplemento(),
                dto.getBairro(),
                dto.getCidade(),
                dto.getUf()
        );
    }
}
