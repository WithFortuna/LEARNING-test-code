package sample.cafekiosk.spring.product.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.domain.ProductType;

public record ProductCreateServiceRequest(
        ProductType productType,
        ProductSellingType productSellingType,
        String name,
        int price
) {
}
