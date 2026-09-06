package com.mensageria.infrastructure.messaging.rabbitmq.adapter;

import com.mensageria.application.port.in.ProcessarEventoUseCase;
import com.mensageria.domain.model.Evento;
import com.mensageria.infrastructure.messaging.rabbitmq.dto.EventoMessageDTO;
import com.mensageria.infrastructure.messaging.rabbitmq.mapper.EventoMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Adapter de entrada (driving adapter): traduz a mensagem crua do RabbitMQ
 * (EventoMessageDTO) para o EventoDominio e aciona o use case. Se o use case lançar
 * exceção, ela sobe e aciona o retry/DLQ configurado no listener container.
 */
@Component
public class RabbitMQEventoConsumerAdapter {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQEventoConsumerAdapter.class);

    private final ProcessarEventoUseCase processarEventoUseCase;

    public RabbitMQEventoConsumerAdapter(ProcessarEventoUseCase processarEventoUseCase) {
        this.processarEventoUseCase = processarEventoUseCase;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void consumir(EventoMessageDTO mensagem) {
        try {
            Evento<?> evento = EventoMessageMapper.toDomain(mensagem);
            processarEventoUseCase.executar(evento);
        } catch (Exception e) {
            log.error("Erro ao processar evento {}", mensagem.getEventoId(), e);
            throw e;
        }
    }
}
