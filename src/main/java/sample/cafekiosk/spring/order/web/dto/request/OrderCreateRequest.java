package sample.cafekiosk.spring.order.web.dto.request;

import jakarta.validation.constraints.NotEmpty;
import sample.cafekiosk.spring.order.application.dto.request.OrderCreateServiceRequest;

import java.util.List;

public record OrderCreateRequest(
        @NotEmpty(message = "상품 번호는 필수입니다.")
        List<String> productNumbers
) {
    public OrderCreateServiceRequest toServiceRequest() {
        return new OrderCreateServiceRequest(productNumbers);
    }
}
