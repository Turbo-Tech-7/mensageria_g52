package com.mensageria.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio que representa um evento a ser propagado.
 * Não conhece RabbitMQ, JSON, HTTP ou qualquer detalhe de infraestrutura —
 * essa é a regra de ouro da Clean Architecture: o domínio não depende de nada.
 */
public record Evento<T>(
        String eventoId,
        String tipo,
        LocalDateTime dataCriacao,
        T payload
) {

    public static <T> Evento<T> criar(String tipo, T payload) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O tipo do evento não pode ser vazio");
        }
        return new Evento<>(UUID.randomUUID().toString(), tipo, LocalDateTime.now(), payload);
    }

    public static <T> Evento<T> reconstituir(String eventoId, String tipo, LocalDateTime dataCriacao, T payload) {
        return new Evento<>(eventoId, tipo, dataCriacao, payload);
    }
}
