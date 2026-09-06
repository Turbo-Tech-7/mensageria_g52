package com.mensageria.application.usecase;

import com.mensageria.application.port.in.ProcessarEventoUseCase;
import com.mensageria.application.port.out.EmailSenderPort;
import com.mensageria.domain.model.Evento;
import com.mensageria.domain.model.NotificacaoStatusServico;
import com.mensageria.domain.model.StatusServico;
import com.mensageria.infrastructure.email.template.StatusServicoEmailTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessarEventoService implements ProcessarEventoUseCase {

    private static final Logger log = LoggerFactory.getLogger(ProcessarEventoService.class);

    private final EmailSenderPort emailSenderPort;

    public ProcessarEventoService(EmailSenderPort emailSenderPort) {
        this.emailSenderPort = emailSenderPort;
    }

    @Override
    public void executar(Evento<?> evento) {
        log.info("Processando evento {} - tipo: {}", evento.eventoId(), evento.tipo());

        switch (evento.tipo()) {
            case "SERVICO_STATUS_ATUALIZADO" -> processarStatusServicoAtualizado(evento);
            default -> log.warn("Tipo de evento não tratado: {}", evento.tipo());
        }
    }

    private void processarStatusServicoAtualizado(Evento<?> evento) {
        NotificacaoStatusServico notificacao = converterPayload(evento.payload());

        if (notificacao.emailCliente() == null || notificacao.emailCliente().isBlank()) {
            log.warn("Evento {} sem e-mail de cliente — notificação ignorada", evento.eventoId());
            return;
        }

        String assunto = "Atualização do seu serviço — " + notificacao.status().getDescricao();
        String corpoHtml = StatusServicoEmailTemplate.montar(notificacao);

        emailSenderPort.enviar(notificacao.emailCliente(), assunto, corpoHtml);
        log.info("E-mail de status ({}) enviado para {}", notificacao.status(), notificacao.emailCliente());
    }

    @SuppressWarnings("unchecked")
    private NotificacaoStatusServico converterPayload(Object payload) {
        Map<String, Object> payloadMap = (Map<String, Object>) payload;
        return new NotificacaoStatusServico(
                (String) payloadMap.get("servicoId"),
                (String) payloadMap.get("orcamentoId"),
                (String) payloadMap.get("nomeCliente"),
                (String) payloadMap.get("emailCliente"),
                (String) payloadMap.get("veiculo"),
                StatusServico.valueOf((String) payloadMap.get("status"))
        );
    }
}
