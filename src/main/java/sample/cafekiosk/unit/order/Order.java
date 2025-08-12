package sample.cafekiosk.unit.order;

import lombok.AllArgsConstructor;
import sample.cafekiosk.unit.beverage.Beverage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class Order {
    private List<Beverage> beverages = new ArrayList<>();
    private LocalDateTime createdAt;

    public int getTotalPrice() {
        return beverages.stream().mapToInt(Beverage::getPrice).sum();
    }
}
