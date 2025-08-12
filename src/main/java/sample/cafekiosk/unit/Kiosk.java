package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Kiosk {
    private final List<Beverage> beverages = new ArrayList<>();
    private static final LocalTime OPEN_TIME = LocalTime.of(9, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(18, 0);

    public void addBeverage(Beverage beverage) {
        beverages.add(beverage);
    }
    public void addBeveragesByCount(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("개수를 잘보시오");
        }
        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    public void removeBeverage(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void removeAllBeverages() {
        beverages.clear();
    }

    public int getTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }

    public Order createOrder() {
        return createOrder(LocalDateTime.now());
    }

    public Order createOrder(LocalDateTime orderedAt) {
        LocalTime orderedTime = orderedAt.toLocalTime();
        if (orderedTime.isBefore(OPEN_TIME) || orderedTime.isAfter(CLOSE_TIME)) {
            throw new IllegalArgumentException("주문 불가능 시각");
        }
        return new Order(beverages, orderedAt);
    }

}
