package sample.cafekiosk.spring.product.web.dto;

import sample.cafekiosk.spring.product.domain.Product;
import sample.cafekiosk.spring.product.domain.ProductSellingType;
import sample.cafekiosk.spring.product.domain.ProductType;

public record ProductResponse(
        Long id,
        String productNumber,
        ProductType productType,
        ProductSellingType productSellingType,
        String name,
        int price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductNumber(),
                product.getProductType(),
                product.getProductSellingType(),
                product.getName(),
                product.getPrice()
        );
    }
}
