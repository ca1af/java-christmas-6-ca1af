package date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderDayTest {

    @ParameterizedTest
    @ValueSource(ints = {-10, -1, 0, 32, 35, 100, 1000})
    @DisplayName("1~31 사이의 숫자만 입력 가능하다.")
    void validate(int day) {
        assertThatThrownBy(() -> new OrderDay(day)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("자바 Calender 객체를 이용해서 요일을 계산 : 2023.12.3 은 일요일(1) 이다.")
    void java_dayOfWeek_test() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 3);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        assertThat(dayOfWeek).isEqualTo(Calendar.SUNDAY);
    }

    @ParameterizedTest
    @DisplayName("요일수(dayOfWeek) 테스트 - 기준일(standardDay) 이후")
    @ValueSource(ints = {5, 7, 9, 12, 31})
    void getDayOfWeek_AFTER_STANDARD_DAY(int date) {
        int standardDay = 3;
        OrderDay standardOrderDay = new OrderDay(standardDay);
        OrderDay orderDay = new OrderDay(date);

        int standardDayOfWeek = standardOrderDay.getDayOfWeek();
        int dayOfWeek = orderDay.getDayOfWeek();

        int dayOfWeekDifference = dayOfWeek - standardDayOfWeek;
        int dayDifference = (date - standardDay) % 7; // 요일 차이 계산식

        assertThat(dayDifference).isEqualTo(dayOfWeekDifference);
    }

    @ParameterizedTest
    @DisplayName("요일수(dayOfWeek) 테스트 - 기준일(standardDay) 이전")
    @ValueSource(ints = {1, 5, 7, 9, 12, 15, 21, 27})
    void getDayOfWeek_BEFORE_STANDARD_DAY(int date) {
        int standardDay = 31;
        OrderDay standardOrderDay = new OrderDay(standardDay);
        OrderDay orderDay = new OrderDay(date);

        int standardDayOfWeek = standardOrderDay.getDayOfWeek();
        int dayOfWeek = orderDay.getDayOfWeek();

        int dayOfWeekDifference = dayOfWeek - standardDayOfWeek;
        int dayDifference = 7 - (standardDay - date) % 7; // 요일 차이 계산식

        assertThat(dayDifference).isEqualTo(dayOfWeekDifference);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25})
    @DisplayName("달력에 별이 찍힌 날을 계산하는 로직을 테스트.")
    void isStarDay(int starDay) {
        OrderDay orderDay = new OrderDay(starDay);
        assertThat(orderDay.isStarDay()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("주말 (금,토) 임을 테스트")
    void isWeekend(int day) {
        OrderDay orderDay = new OrderDay(day);
        assertThat(orderDay.isWeekend()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("D-DAY 할인 가능 여부 테스트")
    void isDDayApplicable(int day){
        OrderDay orderDay = new OrderDay(day);
        boolean dDayApplicable = orderDay.isDDayApplicable();
        if (day > 25){
            assertThat(dDayApplicable).isFalse();
            return;
        }
        assertThat(dDayApplicable).isTrue();
    }

    @Test
    @DisplayName("추가 할인량은 오늘날짜 - 1이다. ex : 25일 == 24 * 100원 추가")
    void getDDayApplicableDays() {
        int day = 21;
        OrderDay orderDay = new OrderDay(day);
        int dDayApplicableDays = orderDay.getDDayApplicableDays();
        assertThat(dDayApplicableDays).isEqualTo(day - 1);
    }
}