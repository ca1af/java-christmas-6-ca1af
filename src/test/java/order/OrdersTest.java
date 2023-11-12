package order;

import date.OrderDay;
import menu.category.Beverage;
import menu.category.Dessert;
import menu.category.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrdersTest {
    private Order beverage;
    private Order main;
    private Orders orders;
    @BeforeEach
    void init() {
        beverage = new Order(Beverage.CHAMPAGNE, 5);
        main = new Order(Main.BARBEQUE_RIB, 5);
        orders = new Orders(List.of(beverage, main));
    }
    @Test
    @DisplayName("총 주문 금액(할인 전) 을 구한다.")
    void getOrderAmount() {
        int beverageOrderAmount = beverage.getOrderAmount();
        int mainOrderAmount = main.getOrderAmount();
        assertThat(orders.getOrderAmount()).isEqualTo(beverageOrderAmount + mainOrderAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10})
    @DisplayName("날짜와 관계없이 order 들의 할인 총액은 orders 의 할인총액과 같다.")
    void getTotalDiscountAmount(int day) {
        OrderDay orderDay = new OrderDay(day);
        int beverageTotalDiscount = beverage.getTotalDiscount(orderDay);
        int mainTotalDiscount = main.getTotalDiscount(orderDay);
        int totalDiscount = orders.getTotalDiscount(orderDay);
        assertThat(beverageTotalDiscount + mainTotalDiscount).isEqualTo(totalDiscount);
    }

    @Test
    @DisplayName("음료만 주문하면 금액이 얼마든 할인하지 않는다.")
    void NO_DISCOUNT_ON_ONLY_BEVERAGES() {
        OrderDay orderDay = new OrderDay(25); // D-Day, Star 할인이 적용된다 ( total 4400 )
        Order order = new Order(Beverage.CHAMPAGNE, 5); // 5개이므로 22000 할인이어야 하지만, 음료만주문이므로 0
        orders = new Orders(List.of(order));
        assertThat(orders.getTotalDiscount(orderDay)).isZero();
    }

    @Test
    @DisplayName("금액이 10000원 이하인 주문은 할인하지 않는다.")
    void NO_DISCOUNT_ORDER_AMOUNT_UDER_10000() {
        OrderDay orderDay = new OrderDay(25); // D-Day, Star 할인이 적용된다 ( total 4400 )
        Order order = new Order(Dessert.ICE_CREAM, 1);
        orders = new Orders(List.of(order));
        assertThat(orders.getTotalDiscount(orderDay)).isZero();
    }

    @Test
    @DisplayName("할인 후 예상 결제 금액(final price)은 총 금액 - 총 할인액이다.")
    void getFinalPrice() {
        OrderDay orderDay = new OrderDay(25); // D-Day, Star 할인이 적용된다 ( total 4400 )
        int finalPrice = orders.getFinalPrice(orderDay);
        int orderAmount = orders.getOrderAmount(); // 원래 총 금액
        int totalDiscount = orders.getTotalDiscount(orderDay); // 총 할인액
        assertThat(finalPrice).isEqualTo(orderAmount - totalDiscount);
    }
}