package br.com.empresa.emprestimos.proposta.application.listener;

import br.com.empresa.emprestimos.proposta.domain.event.PropostaAprovadaEvent;
import br.com.empresa.emprestimos.proposta.domain.event.PropostaRecusadaEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaEventListener {

    private static final Logger log = LoggerFactory.getLogger(PropostaEventListener.class);

    @EventListener
    public void aoAprovarProposta(PropostaAprovadaEvent evento) {
        log.info("Proposta {} aprovada para o cliente {} no valor de {}",
                evento.propostaId(), evento.clienteId(), evento.valor());
    }

    @EventListener
    public void aoRecusarProposta(PropostaRecusadaEvent evento) {
        log.info("Proposta {} recusada para o cliente {}. Motivo: {}",
                evento.propostaId(), evento.clienteId(), evento.motivo());
    }
}
