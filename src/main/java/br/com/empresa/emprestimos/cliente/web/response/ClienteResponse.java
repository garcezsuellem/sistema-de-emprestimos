package br.com.empresa.emprestimos.cliente.web.response;

import br.com.empresa.emprestimos.shared.enums.SituacaoCliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {

    private UUID id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private SituacaoCliente situacao;
    private OffsetDateTime cadastradoEm;
}
