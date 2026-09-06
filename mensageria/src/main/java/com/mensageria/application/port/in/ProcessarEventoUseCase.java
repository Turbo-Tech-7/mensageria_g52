package com.mensageria.application.port.in;

import com.mensageria.domain.model.Evento;

public interface ProcessarEventoUseCase {

    void executar(Evento<?> evento);
}
