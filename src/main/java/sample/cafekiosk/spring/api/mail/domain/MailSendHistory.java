package sample.cafekiosk.spring.api.mail.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MailSendHistory {
    @Id @GeneratedValue
    private Long id;
    private String targetEmail;
    private String title;
    private String content;

    public static MailSendHistory create(String targetEmail, String title, String content) {
        return MailSendHistory.builder()
                .targetEmail(targetEmail)
                .title(title)
                .content(content)
                .build();
    }
}
