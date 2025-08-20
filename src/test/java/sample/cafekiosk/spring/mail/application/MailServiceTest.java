package sample.cafekiosk.spring.mail.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import sample.cafekiosk.spring.mail.repository.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {
//    @Spy
    @Mock
    private MailSender mailSender;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;
    @InjectMocks
    private MailService mailService;

    @DisplayName("MailSender, MailSendHistoryRepository는 stubbing하고 메일 전송 함수만을 테스트한다.")
    @Test
    public void should_return_true_when_sendMail_called_with_stubbing() {
        // given
/*      // @Spy 일 때 동작정의
        Mockito.doReturn(true)
                .when(mailSender)
                .sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
*/
//        Mockito.when(mailSender.sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
//                .thenReturn(true);
        BDDMockito.given(mailSender.sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .willReturn(true);

        // mock 처리된 mailSendHistoryRepository는 JPA 연결도 안하니지만 Mockito 로직상 exception이 아니라 함수 호출 결과는 null이다.

        // when
        boolean result = mailService.sendMail("<EMAIL>", "test", "test");

        // then
        Mockito.verify(mailSender, Mockito.times(1))
                .sendMail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        assertThat(result).isTrue();
    }
}