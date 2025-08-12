package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmericanoTest {

    @Test
    void should_return_4000_when_getPrice_called_for_Americano() {
        Americano americano = new Americano();
        assertEquals(4000, americano.getPrice());
    }

    @Test
    void should_not_return_4500_when_getPrice_called_for_Americano() {
        Americano americano = new Americano();
        org.assertj.core.api.Assertions.assertThat(americano.getPrice()).isNotEqualTo(4500);
    }

}