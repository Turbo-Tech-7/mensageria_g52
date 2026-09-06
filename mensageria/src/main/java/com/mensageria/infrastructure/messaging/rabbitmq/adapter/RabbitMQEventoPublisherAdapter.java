package com.mensageria.infrastructure.messaging.rabbitmq.adapter;

import com.mensageria.application.port.out.EventoPublisherPort;
import com.mensageria.domain.model.Evento;
import com.mensageria.infrastructure.messaging.rabbitmq.dto.EventoMessageDTO;
import com.mensageria.infrastructure.messaging.rabbitmq.mapper.EventoMessageMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Adapter de saída (driven adapter): implementação concreta do EventoPublisherPort
 * usando RabbitMQ. A aplicação depende apenas da interface — trocar RabbitMQ por
 * Kafka no futuro significa criar outro adapter aqui, sem tocar em domain/application.
 */
@Component
public class RabbitMQEventoPublisherAdapter implements EventoPublisherPort {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.routing-key}")
    private String routingKey;

    public RabbitMQEventoPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public <T> void publicar(Evento<T> evento) {
        EventoMessageDTO dto = EventoMessageMapper.toDTO(evento);
        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
    }
}
