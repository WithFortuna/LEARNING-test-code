package sample.cafekiosk.spring.api.mail.application;

import org.springframework.stereotype.Component;

@Component
public class MailSender {
    public boolean sendMail(String to, String subject, String content) {
        return true;
    }
}
