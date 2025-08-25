package sample.cafekiosk.spring.api.product.application.dto;

import sample.cafekiosk.spring.api.product.domain.ProductSellingType;
import sample.cafekiosk.spring.api.product.domain.ProductType;

public record ProductCreateServiceRequest(
        ProductType productType,
        ProductSellingType productSellingType,
        String name,
        int price
) {
}
