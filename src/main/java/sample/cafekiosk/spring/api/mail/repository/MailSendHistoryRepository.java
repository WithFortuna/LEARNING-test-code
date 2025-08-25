package sample.cafekiosk.spring.api.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.api.mail.domain.MailSendHistory;

@Repository
public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
