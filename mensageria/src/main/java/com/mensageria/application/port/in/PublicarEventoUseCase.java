package com.mensageria.application.port.in;

public interface PublicarEventoUseCase {

    <T> void executar(String tipo, T payload);
}
