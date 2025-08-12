package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
class KioskTest {

    @Test
    void should_hasSize_2_when_addBeveragesByCount_called() {
        Kiosk kiosk = new Kiosk();
        int count = 2;
        Americano beverage = new Americano();
        kiosk.addBeveragesByCount(beverage, count);

        assertThat(kiosk.getBeverages()).hasSize(count);
        assertThat(kiosk.getBeverages().get(0)).isEqualTo(beverage);
        assertThat(kiosk.getBeverages().get(1)).isEqualTo(beverage);
    }
    @Test
    void should_hasSize_1_when_addBeveragesByCount_called() {
        Kiosk kiosk = new Kiosk();
        int count = 1;
        Americano beverage = new Americano();
        kiosk.addBeveragesByCount(beverage, count);

        assertThat(kiosk.getBeverages()).hasSize(count);
        assertThat(kiosk.getBeverages().get(0)).isEqualTo(beverage);
    }

    @Test
    void should_throw_illegalArgumentException_when_addBeveragesByCount_called_with_count_0() {
        Kiosk kiosk = new Kiosk();
        int count = 0;
        Americano beverage = new Americano();
        assertThatThrownBy(() -> kiosk.addBeveragesByCount(beverage, count))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void should_throw_illegalArgumentException_when_ordered_at_outside_open_time() {
        Kiosk kiosk = new Kiosk();
        kiosk.addBeverage(new Americano());

        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2025, 1, 1, 8, 59)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2025, 1, 1, 18, 1)));
    }
}