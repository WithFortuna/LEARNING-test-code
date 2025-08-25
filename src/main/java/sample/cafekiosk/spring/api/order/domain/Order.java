package sample.cafekiosk.spring.api.order.domain;

import jakarta.persistence.*;
import lombok.*;
import sample.cafekiosk.spring.global.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder(access = PRIVATE)
@Entity(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredAt;

    @Builder.Default
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public static Order create(LocalDateTime registeredAt) {
        return Order.builder()
                .totalPrice(0)
                .orderStatus(OrderStatus.INIT)
                .registeredAt(registeredAt)
                .build();
    }


    // 연관관계 편의 메서드
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProduct.setOrder(this);
        orderProducts.add(orderProduct);
        addPrice(orderProduct.getProduct().getPrice());
    }

    private void addPrice(int price) {
        totalPrice += price;
    }
}

