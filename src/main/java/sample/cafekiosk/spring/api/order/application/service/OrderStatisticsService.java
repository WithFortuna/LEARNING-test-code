package sample.cafekiosk.spring.api.order.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.mail.application.MailService;
import sample.cafekiosk.spring.api.order.repository.OrderRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderStatisticsService {
    private final OrderRepository orderRepository;
    private final MailService mailService;

    public void sendOrderStatisticsToMail(LocalDate orderedAt, String targetEmail) {
        int totalPriceOfOrdersAt = getTotalPriceOfOrdersAt(orderedAt);

        boolean result = sendToEmail(orderedAt, targetEmail, totalPriceOfOrdersAt);

        if (!result) {
            throw new IllegalArgumentException("매출 통계 메일 전송에 실패");
        }

    }

    private boolean sendToEmail(LocalDate orderedAt, String targetEmail, int totalPriceOfOrdersAt) {
        String title = String.format("%s 총 매출", orderedAt);
        String content = String.format("%d 만큼의 매출을 기록했습니다.", totalPriceOfOrdersAt);
        boolean result = mailService.sendMail(targetEmail, title, content);
        return result;
    }

    private int getTotalPriceOfOrdersAt(LocalDate orderedAt) {
        LocalDateTime startDateTime = orderedAt.atStartOfDay();
        LocalDateTime endDateTime = orderedAt.plusDays(1L).atStartOfDay();
        int totalPriceOfOrdersAt = orderRepository.getTotalPriceOfOrdersAt(startDateTime, endDateTime);
        return totalPriceOfOrdersAt;
    }
}
