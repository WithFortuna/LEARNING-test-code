package sample.cafekiosk.spring.stock.domain;

import jakarta.persistence.*;
import lombok.*;
import sample.cafekiosk.spring.product.domain.Product;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE)
@Getter
@Table(
        name = "stocks",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id"}))
@Entity
public class Stock {
    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private int quantity;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    private Product product;

    public static Stock create(Product product, int quantity) {
        return Stock.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }

    public void decreaseQuantity(int toDecrease) {
        if (toDecrease > this.quantity) {
            throw new IllegalArgumentException("재고가 부족합니다");
        }
        this.quantity -= toDecrease;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }
}
