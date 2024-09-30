package br.com.fiap.epictaskk.mail;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    public void sendMail(String message) throws MessagingException {
        var email = mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(email);

        helper.setTo("joao@fiap.com.br");
        helper.setSubject("Tarefa Criada");
        helper.setText("""
                    <h2>Nova Tarefa</h2>
                    <p>%s</p>
                """.formatted(message), true);

        mailSender.send(email);
    }
}
