package menu;

import date.OrderDay;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {
    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    @DisplayName("날짜와 상관없이 Appetizer 는 할인하지 않는다.")
    void isDDayApplicable_APPETIZER(int day) {
        OrderDay orderDay = new OrderDay(day);
        for (Appetizer appetizer : Appetizer.values()) {
            boolean dayOfWeekApplicable = appetizer.isDayOfWeekApplicable(orderDay);
            assertThat(dayOfWeekApplicable).isFalse();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    @DisplayName("날짜와 상관없이 Beverage 는 할인하지 않는다.")
    void isDDayApplicable_BEVERAGE(int day) {
        OrderDay orderDay = new OrderDay(day);
        for (Beverage beverage : Beverage.values()) {
            boolean dayOfWeekApplicable = beverage.isDayOfWeekApplicable(orderDay);
            assertThat(dayOfWeekApplicable).isFalse();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("주말 할인의 적용 여부(boolean) 테스트 - 메인만 적용")
    void isDDayApplicable_WEEKEND_MAIN(int day) {
        OrderDay orderDay = new OrderDay(day);
        // [주말 - 메인] 할인
        for (Main value : Main.values()) {
            assertThat(value.isDayOfWeekApplicable(orderDay)).isTrue();
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31})
    @DisplayName("평일 할인의 적용 여부(boolean) 테스트 - 디저트만 적용")
    void isDDayApplicable_WEEKDAY_DESSERT(int day) {
        OrderDay orderDay = new OrderDay(day);
        // [평일 - 디저트] 할인
        for (Dessert dessert : Dessert.values()) {
            assertThat(dessert.isDayOfWeekApplicable(orderDay)).isTrue();
        }
    }
}