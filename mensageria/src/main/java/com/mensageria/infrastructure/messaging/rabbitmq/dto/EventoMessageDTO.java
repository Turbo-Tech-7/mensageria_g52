package com.mensageria.infrastructure.messaging.rabbitmq.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EventoMessageDTO implements Serializable {

    private String eventoId;
    private String tipo;
    private LocalDateTime dataCriacao;
    private Object payload;

    public EventoMessageDTO() {
    }

    public EventoMessageDTO(String eventoId, String tipo, LocalDateTime dataCriacao, Object payload) {
        this.eventoId = eventoId;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
        this.payload = payload;
    }

    public String getEventoId() {
        return eventoId;
    }

    public void setEventoId(String eventoId) {
        this.eventoId = eventoId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
