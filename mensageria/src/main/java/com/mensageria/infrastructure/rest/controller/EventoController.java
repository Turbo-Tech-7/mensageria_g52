package com.mensageria.infrastructure.rest.controller;

import com.mensageria.application.port.in.PublicarEventoUseCase;
import com.mensageria.infrastructure.rest.dto.PublicarEventoRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Adapter de entrada (driving adapter) via HTTP. Só traduz a requisição pro
 * contrato do use case — nenhuma regra de negócio mora aqui.
 */
@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final PublicarEventoUseCase publicarEventoUseCase;

    public EventoController(PublicarEventoUseCase publicarEventoUseCase) {
        this.publicarEventoUseCase = publicarEventoUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> publicar(@RequestBody @Valid PublicarEventoRequest request) {
        publicarEventoUseCase.executar(request.tipo(), request.payload());
        return ResponseEntity.accepted().build();
    }
}
