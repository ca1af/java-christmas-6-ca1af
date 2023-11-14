package order;

import date.OrderDay;
import menu.Menu;
import menu.category.Beverage;
import menu.category.Dessert;
import menu.category.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("상위 추상 Menu 타입으로 넣어도 하위 구상 Enum 으로 잘 들어오는지 확인")
    void getMenuTest() {
        order = new Order(Beverage.CHAMPAGNE, 1);
        assertThat(order.getMenu()).isEqualTo(Beverage.CHAMPAGNE);
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
        assertThat(order.getOrderAmount()).isEqualTo(price * MENU_QUANTITY);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 8, 9, 15, 16, 22, 23, 29, 30})
    @DisplayName("주말 할인이 적용된 경우의 총 할인액 - 메인메뉴에 적용된다.")
    void totalDiscount_WEEKEND(int day) {
        OrderDay orderDay = new OrderDay(day);

        for (Main mainMenu : Main.values()) {
            order = new Order(mainMenu, MENU_QUANTITY);

            int totalDiscount = order.getDayOfWeekDiscount(orderDay);
            int discountByDayOfWeek = 2_023; // 명세에 요구된 금액.

            assertThat(totalDiscount).isEqualTo(discountByDayOfWeek * MENU_QUANTITY);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31})
    @DisplayName("평일 할인이 적용된 경우의 총 할인액 - 디저트에 적용된다.")
    void totalDiscount_WEEK_DAY(int day) {
        OrderDay orderDay = new OrderDay(day);

        for (Dessert dessert : Dessert.values()) {
            order = new Order(dessert, MENU_QUANTITY);

            int totalDiscount = order.getDayOfWeekDiscount(orderDay);
            int discountByDayOfWeek = 2_023; // 명세에 요구된 금액.

            assertThat(totalDiscount).isEqualTo(discountByDayOfWeek * MENU_QUANTITY);
        }
    }

}