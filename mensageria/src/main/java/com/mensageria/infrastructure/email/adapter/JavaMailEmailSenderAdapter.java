package com.mensageria.infrastructure.email.adapter;

import com.mensageria.application.port.out.EmailSenderPort;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class JavaMailEmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender mailSender;

    public JavaMailEmailSenderAdapter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviar(String destinatario, String assunto, String corpoHtml) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
            helper.setFrom("garagem.g.52@gmail.com", "Garagem52");
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true);
            mailSender.send(mensagem);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao montar e-mail para " + destinatario, e);
        }
    }
}
