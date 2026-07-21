package br.com.empresa.emprestimos.proposta.domain.repository;

import br.com.empresa.emprestimos.proposta.domain.entity.PropostaEmprestimo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PropostaRepository {

    PropostaEmprestimo salvar(PropostaEmprestimo proposta);

    Optional<PropostaEmprestimo> buscarPorId(UUID id);

    List<PropostaEmprestimo> buscarPorClienteId(UUID clienteId);
}
