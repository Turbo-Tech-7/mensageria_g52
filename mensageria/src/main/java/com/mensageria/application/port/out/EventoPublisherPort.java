package com.mensageria.application.port.out;

import com.mensageria.domain.model.Evento;

public interface EventoPublisherPort {

    <T> void publicar(Evento<T> evento);
}
