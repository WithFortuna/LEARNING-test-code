package sample.cafekiosk.unit;

import org.assertj.core.api.Assertions;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
class KioskTest {

    @DisplayName("음료 2개를 추가하면 주문목록에 추가된다")
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
    @DisplayName("음료 1개 추가하면 주문목록에 추가된다")
    @Test
    void should_hasSize_1_when_addBeveragesByCount_called() {
        // given
        Kiosk kiosk = new Kiosk();
        int count = 1;
        Americano beverage = new Americano();

        // when
        kiosk.addBeveragesByCount(beverage, count);

        // then
        assertThat(kiosk.getBeverages()).hasSize(count);
        assertThat(kiosk.getBeverages().get(0)).isEqualTo(beverage);
    }

    @DisplayName("음료를 0개 추가하면 예외가 발생한다.")
    @Test
    void should_throw_illegalArgumentException_when_addBeveragesByCount_called_with_count_0() {
        // given
        Kiosk kiosk = new Kiosk();
        int count = 0;
        Americano beverage = new Americano();

        // when & then
        assertThatThrownBy(() -> kiosk.addBeveragesByCount(beverage, count))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("영업시간 외에 주문하면 예외가 발생한다.")
    @Test
    void should_throw_illegalArgumentException_when_ordered_at_outside_open_time() {
        // given
        Kiosk kiosk = new Kiosk();
        kiosk.addBeverage(new Americano());

        // when & then
        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2025, 1, 1, 8, 59)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> kiosk.createOrder(LocalDateTime.of(2025, 1, 1, 18, 1)));
    }


}