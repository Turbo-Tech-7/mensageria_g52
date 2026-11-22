package com.mensageria.application.port.out;

public interface EmailSenderPort {

    void enviar(String destinatario, String assunto, String corpoHtml);
}
