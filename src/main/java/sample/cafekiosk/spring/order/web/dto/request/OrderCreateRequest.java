package sample.cafekiosk.spring.order.web.dto.request;

import java.util.List;

public record OrderCreateRequest(
        List<String> productNumbers
) {
}
