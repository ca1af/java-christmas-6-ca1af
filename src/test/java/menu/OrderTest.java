package menu;

import date.OrderDay;
import menu.category.Main;
import order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {
    private Order order;
    private static final int MENU_QUANTITY = 5;
    @BeforeEach
    @DisplayName("요일별 할인을 false 로 기본 초기화")
    void init() {
        Menu menu = new Menu() {
            @Override
            public boolean isDayOfWeekApplicable(OrderDay orderDay) {
                return false;
            }

            @Override
            public String getName() {
                return "Test";
            }

            @Override
            public int getPrice() {
                return 1000;
            }
        };

        order = new Order(menu, MENU_QUANTITY);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000, 2000, 3000, 4000, 5000, 6000})
    @DisplayName("메뉴의 원래 총 가격을 계산한다.")
    void getTotalMoney(int price) {
        Menu menu = new Menu() {
            @Override
            public boolean isDayOfWeekApplicable(OrderDay orderDay) {
                return false;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public int getPrice() {
                return price;
            }
        };
        order = new Order(menu, MENU_QUANTITY);
        assertThat(order.getTotalMoneyAmount()).isEqualTo(price * MENU_QUANTITY);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25, 31})
    @DisplayName("달력에 별이 찍힌 날(starDay) 할인이 정상 적용됨을 확인")
    void discountByStarDay(int day) {
        OrderDay orderDay = new OrderDay(day);
        
        int discountByStarDay = order.discountByStarDay(orderDay);
        assertThat(discountByStarDay).isEqualTo(1000);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 26, 29})
    @DisplayName("달력에 별이 찍힌 날(starDay) 할인이 적용되지 않아야함")
    void discountByStarDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);
        
        int discountByStarDay = order.discountByStarDay(orderDay);
        assertThat(discountByStarDay).isZero();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25})
    @DisplayName("D-DAY 할인의 적용률 테스트")
    void discountByDDay(int day) {
        OrderDay orderDay = new OrderDay(day);
        
        int discountByDDay = order.discountByDDay(orderDay);
        int estimatedDiscount = 1000 + (day - 1) * 100; // 할인 계산식
        assertThat(discountByDDay).isEqualTo(estimatedDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    @DisplayName("25일 이후 날짜는 D-DAY 할인의 적용을 받지 않음을 확인")
    void discountByDDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);
        
        int discountByDDay = order.discountByDDay(orderDay);
        assertThat(discountByDDay).isZero();
    }
    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25, 30})
    @DisplayName("요일 할인이 적용되지 않았을 경우의 총 할인액")
    void totalDiscount_NO_DAY_OF_WEEKEND_DISCOUNT(int day) {
        OrderDay orderDay = new OrderDay(day);
        
        int discountByDDay = order.discountByDDay(orderDay);
        int discountByStarDay = order.discountByStarDay(orderDay); // beforeEach 로 false 설정
        int totalDiscount = order.getTotalDiscount(orderDay);

        int estimatedTotal = discountByDDay + discountByStarDay;
        assertThat(estimatedTotal * MENU_QUANTITY).isEqualTo(totalDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25, 30})
    @DisplayName("요일 할인이 적용되지 않은 경우의 총 할인액 (적용될 Enum 별로 테스트 필요)")
    void totalDiscount_NO_DAY_OF_WEEK_DISCOUNT(int day) {
        OrderDay orderDay = new OrderDay(day);

        int discountByDDay = order.discountByDDay(orderDay);
        int discountByStarDay = order.discountByStarDay(orderDay);

        int totalDiscount = order.getTotalDiscount(orderDay);
        int estimatedTotalDiscount = discountByDDay + discountByStarDay;
        assertThat(estimatedTotalDiscount * MENU_QUANTITY).isEqualTo(totalDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("주말 할인이 적용된 경우의 총 할인액 (적용될 Enum 별로 테스트 필요)")
    void totalDiscount(int day) {
        OrderDay orderDay = new OrderDay(day);

        for (Main mainMenu : Main.values()) {
            order = new Order(mainMenu, MENU_QUANTITY);

            int discountByDDay = order.discountByDDay(orderDay);
            int discountByStarDay = order.discountByStarDay(orderDay);
            int discountByDayOfWeek = 2_023; // 명세에 요구된 금액.

            int totalDiscount = order.getTotalDiscount(orderDay);
            int estimatedTotalDiscount = discountByDDay + discountByStarDay + discountByDayOfWeek;

            assertThat(estimatedTotalDiscount * MENU_QUANTITY).isEqualTo(totalDiscount);
        }
    }
}