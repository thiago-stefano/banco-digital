package br.com.cdb.projetobancodigital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviar(String para, String assunto, String corpo) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");
            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpo, true);
            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }
    }

    public void enviarCodigoVerificacao(String email, String codigo) {
        String assunto = "Verificação de e-mail - Banco Digital";
        String corpo = "<p>Olá!</p>"
                     + "<p>Seu código de verificação é: <strong>" + codigo + "</strong></p>"
                     + "<p>Use este código para confirmar seu e-mail no Banco Digital.</p>";
        
        enviar(email, assunto, corpo);
    }
}
