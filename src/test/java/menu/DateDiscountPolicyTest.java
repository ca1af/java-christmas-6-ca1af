package menu;

import date.OrderDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DateDiscountPolicyTest {
    private DateDiscountPolicy dateDiscountPolicy;

    @BeforeEach
    @DisplayName("요일별 할인을 false 로 기본 초기화")
    void init() {
        dateDiscountPolicy = order -> false; // 요일별 할인은 따로 테스트할 것이기 때문에, 위와 같이.
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    @DisplayName("달력에 별이 찍힌 날(starDay) 할인이 정상 적용됨을 확인")
    void discountByStarDay(int day) {
        OrderDay orderDay = new OrderDay(day);
        int discountByStarDay = dateDiscountPolicy.discountByStarDay(orderDay);
        assertThat(discountByStarDay).isEqualTo(1000);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 26, 29})
    @DisplayName("달력에 별이 찍힌 날(starDay) 할인이 적용되지 않아야함")
    void discountByStarDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);
        int discountByStarDay = dateDiscountPolicy.discountByStarDay(orderDay);
        assertThat(discountByStarDay).isZero();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25})
    @DisplayName("D-DAY 할인의 적용률 테스트")
    void discountByDDay(int day) {
        OrderDay orderDay = new OrderDay(day);
        int discountByDDay = dateDiscountPolicy.discountByDDay(orderDay);
        int estimatedDiscount = 1000 + (day - 1) * 100; // 할인 계산식
        assertThat(discountByDDay).isEqualTo(estimatedDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    @DisplayName("25일 이후 날짜는 D-DAY 할인의 적용을 받지 않음을 확인")
    void discountByDDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);
        int discountByDDay = dateDiscountPolicy.discountByDDay(orderDay);
        assertThat(discountByDDay).isZero();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("주말 할인의 적용 여부(boolean) 테스트")
    void isDDayApplicable_WEEKEND(int day) {
        OrderDay orderDay = new OrderDay(day);
        dateDiscountPolicy = OrderDay::isWeekend; // 주말일 경우 [메인]할인
        boolean dayOfWeekApplicable = dateDiscountPolicy.isDayOfWeekApplicable(orderDay);
        assertThat(dayOfWeekApplicable).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("평일 할인의 적용 여부(boolean) 테스트")
    void isDDayApplicable(int day) {
        OrderDay orderDay = new OrderDay(day);
        dateDiscountPolicy = weekOrderDay -> !weekOrderDay.isWeekend(); // 평일일 경우 [디저트]할인
        boolean dayOfWeekApplicable = dateDiscountPolicy.isDayOfWeekApplicable(orderDay);
        assertThat(dayOfWeekApplicable).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25, 30})
    @DisplayName("요일 할인이 적용되지 않았을 경우의 총 할인액")
    void totalDiscount_NO_DAY_OF_WEEKEND_DISCOUNT() {
        OrderDay orderDay = new OrderDay(25);
        int discountByDDay = dateDiscountPolicy.discountByDDay(orderDay);
        int discountByStarDay = dateDiscountPolicy.discountByStarDay(orderDay);
        dateDiscountPolicy = isDiscountOfDayOfWeekApplicable -> false;

        int totalDiscount = dateDiscountPolicy.getTotalDiscount(orderDay);
        assertThat(discountByDDay + discountByStarDay).isEqualTo(totalDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25, 30})
    @DisplayName("요일 할인이 적용될 경우의 할인액 (적용될 Enum 별로 테스트 필요)")
    void totalDiscount() {
        OrderDay orderDay = new OrderDay(25);
        int discountByDDay = dateDiscountPolicy.discountByDDay(orderDay);
        int discountByStarDay = dateDiscountPolicy.discountByStarDay(orderDay);
        dateDiscountPolicy = isDiscountOfDayOfWeekApplicable -> true;

        int discountByDayOfWeek = 2_023; // 명세에 요구된 금액. (밖으로 빼서 변수화할지?)

        int totalDiscount = dateDiscountPolicy.getTotalDiscount(orderDay);
        assertThat(discountByDDay + discountByStarDay + discountByDayOfWeek).isEqualTo(totalDiscount);
    }
}