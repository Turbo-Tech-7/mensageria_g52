package com.mensageria.application.usecase;

import com.mensageria.application.port.in.PublicarEventoUseCase;
import com.mensageria.application.port.out.EventoPublisherPort;
import com.mensageria.domain.model.Evento;
import org.springframework.stereotype.Service;

@Service
public class PublicarEventoService implements PublicarEventoUseCase {

    private final EventoPublisherPort eventoPublisherPort;

    public PublicarEventoService(EventoPublisherPort eventoPublisherPort) {
        this.eventoPublisherPort = eventoPublisherPort;
    }

    @Override
    public <T> void executar(String tipo, T payload) {
        Evento<T> evento = Evento.criar(tipo, payload);
        eventoPublisherPort.publicar(evento);
    }
}
