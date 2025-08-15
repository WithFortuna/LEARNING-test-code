package sample.cafekiosk.spring.order.domain;

import jakarta.persistence.*;
import lombok.*;
import sample.cafekiosk.spring.global.domain.BaseEntity;
import sample.cafekiosk.spring.product.domain.Product;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class OrderProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    private Order order;

    public static OrderProduct create(Product product) {
        return OrderProduct.builder()
                .product(product)
                .build();
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
