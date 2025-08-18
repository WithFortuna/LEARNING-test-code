package sample.cafekiosk.spring.order.application.dto.request;

import java.util.List;

public record OrderCreateServiceRequest(
        List<String> productNumbers
) { }
