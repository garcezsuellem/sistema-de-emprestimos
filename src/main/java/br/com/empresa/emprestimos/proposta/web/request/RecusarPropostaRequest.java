package br.com.empresa.emprestimos.proposta.web.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecusarPropostaRequest {

    @NotBlank(message = "Motivo da recusa é obrigatório")
    private String motivo;
}
