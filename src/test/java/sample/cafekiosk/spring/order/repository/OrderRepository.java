package sample.cafekiosk.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
