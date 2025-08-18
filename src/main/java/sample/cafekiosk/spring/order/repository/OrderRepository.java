package sample.cafekiosk.spring.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sample.cafekiosk.spring.order.domain.Order;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    @Query("select sum(o.totalPrice) " +
            "from orders o " +
            "where o.registeredAt >= :startDateTime and o.registeredAt< :endDateTime")
    int getTotalPriceOfOrdersAt(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime")LocalDateTime endDateTime);
}
