package br.com.empresa.emprestimos.proposta.domain.repository;

import br.com.empresa.emprestimos.proposta.domain.entity.AnaliseCredito;

import java.util.Optional;
import java.util.UUID;

public interface AnaliseCreditoRepository {

    AnaliseCredito salvar(AnaliseCredito analiseCredito);

    Optional<AnaliseCredito> buscarPorPropostaId(UUID propostaId);
}
