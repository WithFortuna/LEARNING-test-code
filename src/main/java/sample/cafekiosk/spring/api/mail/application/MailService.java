package sample.cafekiosk.spring.api.mail.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.mail.domain.MailSendHistory;
import sample.cafekiosk.spring.api.mail.repository.MailSendHistoryRepository;

@RequiredArgsConstructor
@Service
public class MailService {
    private final MailSender mailSender;
    private final MailSendHistoryRepository mailSendHistoryRepository;

    public boolean sendMail(String targetEmail, String title, String content) {
        try {
            boolean result = mailSender.sendMail(targetEmail, title, content);
            if (result) {
                saveMailHistory(targetEmail, title, content);
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // @Transactional 안붙여도 SimpleJpaRepository에 해당 어노테이션 붙어있음
    private void saveMailHistory(String targetEmail, String title, String content) {
        MailSendHistory mailSendHistory = MailSendHistory.create(targetEmail, title, content);
        mailSendHistoryRepository.save(mailSendHistory);
    }
}
