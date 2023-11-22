package order;

import date.OrderDay;
import menu.category.Beverage;
import menu.category.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    @DisplayName("방어적 복사를 테스트")
    void defensive_copy_test() {
        List<Order> orders1 = orders.orders();
        orders1.add(new Order(Beverage.CHAMPAGNE, 3));
        // 외부에서 값을 넣거나 빼도, 다시 가져올 때엔 원래 상태인 녀석으로 가져온다

        List<Order> orders2 = orders.orders();
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    @DisplayName("음료만 주문하는 것은 불가능하다.")
    void validate_containsOnlyBeverages() {
        Order order = new Order(Beverage.CHAMPAGNE, 5);
        List<Order> orders = List.of(order);
        assertThatThrownBy(() -> new Orders(orders)).isInstanceOf(IllegalArgumentException.class);
    }
    @Test
    @DisplayName("20개 이상 주문은 불가능하다.")
    void validate_isQuantityExceeded(){
        Order order = new Order(Main.BARBEQUE_RIB, 21);
        List<Order> orders = List.of(order);
        assertThatThrownBy(() -> new Orders(orders)).isInstanceOf(IllegalArgumentException.class);
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
    @DisplayName("요일 할인의 총합을 계산한다.")
    void getDayOfWeekDiscount(int day) {
        OrderDay orderDay = new OrderDay(day);
        int beverageTotalDiscount = beverage.getDayOfWeekDiscount(orderDay);
        int mainTotalDiscount = main.getDayOfWeekDiscount(orderDay);
        int orderDiscountAmount = beverageTotalDiscount + mainTotalDiscount;

        int totalDiscount = orders.getDayOfWeekDiscount(orderDay);
        assertThat(orderDiscountAmount).isEqualTo(totalDiscount);
    }
}