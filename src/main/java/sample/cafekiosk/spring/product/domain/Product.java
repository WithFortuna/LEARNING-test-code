package sample.cafekiosk.spring.product.domain;

import jakarta.persistence.*;
import lombok.*;
import sample.cafekiosk.spring.global.domain.BaseEntity;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String productNumber;

    @Enumerated(STRING)
    private ProductType productType;

    @Enumerated(STRING)
    private ProductSellingType productSellingType;

    private String name;

    private int price;

    public static Product create(ProductType productType,
                                 ProductSellingType productSellingType,
                                 String name, int price,
                                 String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .productSellingType(productSellingType)
                .name(name)
                .price(price)
                .build();
    }
}
