package br.com.fiap.epictaskk.mail;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailConsumer {

    private final MailService mailService;

    public EmailConsumer(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = "email-queue")
    public void sendMail(String message) throws MessagingException {
        log.info("Recebendo mensagem: " + message);
        mailService.sendMail(message);
    }

}
