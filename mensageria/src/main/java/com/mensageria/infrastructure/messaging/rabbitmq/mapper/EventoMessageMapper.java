package com.mensageria.infrastructure.messaging.rabbitmq.mapper;

import com.mensageria.domain.model.Evento;
import com.mensageria.infrastructure.messaging.rabbitmq.dto.EventoMessageDTO;

public final class EventoMessageMapper {

    private EventoMessageMapper() {
    }

    public static <T> EventoMessageDTO toDTO(Evento<T> evento) {
        return new EventoMessageDTO(
                evento.eventoId(),
                evento.tipo(),
                evento.dataCriacao(),
                evento.payload()
        );
    }

    public static Evento<Object> toDomain(EventoMessageDTO dto) {
        return Evento.reconstituir(
                dto.getEventoId(),
                dto.getTipo(),
                dto.getDataCriacao(),
                dto.getPayload()
        );
    }
}
