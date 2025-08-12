package sample.cafekiosk.unit;

import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.order.Order;

public class KioskRunner {
    public static void main(String[] args) {
        Kiosk kiosk = new Kiosk();
        kiosk.addBeverage(new Americano());
        System.out.println(">>> 아메리카노 추가");
        kiosk.addBeverage(new Americano());
        System.out.println(">>> 아메리카노 추가");

        System.out.println("최종 주문 금액: " + kiosk.getTotalPrice());

        Order order1 = kiosk.createOrder();
        System.out.println(order1.getTotalPrice());
    }
}
