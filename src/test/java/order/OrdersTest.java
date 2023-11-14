package order;

import date.OrderDay;
import gift.FreeGift;
import menu.category.Appetizer;
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
        List<Order> orders1 = orders.getOrders();
        orders1.add(new Order(Beverage.CHAMPAGNE, 3));
        // 외부에서 값을 넣거나 빼도, 다시 가져올 때엔 원래 상태인 녀석으로 가져온다

        List<Order> orders2 = orders.getOrders();
        assertThat(orders1).isNotEqualTo(orders2);
    }

    @Test
    @DisplayName("총 주문 금액(할인 전) 을 구한다.")
    void getOrderAmount() {
        int beverageOrderAmount = beverage.getOrderAmount();
        int mainOrderAmount = main.getOrderAmount();
        assertThat(orders.getOrderAmount()).isEqualTo(beverageOrderAmount + mainOrderAmount);
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void dayOfWeekDiscount_IS_DUPLICABLE(int day) {
        OrderDay orderDay = new OrderDay(day);
        Order barbequeRib = new Order(Main.BARBEQUE_RIB, 2);
        Order seafoodPasta = new Order(Main.SEAFOOD_PASTA, 2);
        orders = new Orders(List.of(barbequeRib, seafoodPasta));
        int orderDiscount = barbequeRib.getDayOfWeekDiscount(orderDay) + seafoodPasta.getDayOfWeekDiscount(orderDay);
        int dDayDiscount = orders.getDDayDiscount(orderDay);
        assertThat(orderDiscount + dDayDiscount).isEqualTo(orders.getTotalDiscount(orderDay));
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

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25})
    @DisplayName("달력에 별이 찍힌 날을 계산하는 로직을 테스트.")
    void isStarDay(int starDay) {
        OrderDay orderDay = new OrderDay(starDay);
        int starDayDiscount = orders.getStarDayDiscount(orderDay);
        assertThat(starDayDiscount).isEqualTo(1000);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,25,31})
    @DisplayName("날짜와 관계없이 order 들의 할인 총액은 orders 의 할인총액과 같다.")
    void getTotalDiscountAmount(int day) {
        OrderDay orderDay = new OrderDay(day);
        int beverageTotalDiscount = beverage.getDayOfWeekDiscount(orderDay);
        int mainTotalDiscount = main.getDayOfWeekDiscount(orderDay);
        int orderDiscountAmount = beverageTotalDiscount + mainTotalDiscount;

        int totalDiscount = orders.getTotalDiscount(orderDay);
        if (orderDay.isStarDay()){ // 특별 할인은 총 주문 금액 기준이므로 한번만 더한다.
            orderDiscountAmount += orders.getStarDayDiscount(orderDay);
        }
        if (orderDay.isDDayApplicable()){
            orderDiscountAmount += orders.getDDayDiscount(orderDay);
        }
        assertThat(orderDiscountAmount).isEqualTo(totalDiscount);
    }

    @Test
    @DisplayName("음료만 주문하는 것은 불가능하다.")
    void NO_DISCOUNT_ON_ONLY_BEVERAGES() {
        Order order = new Order(Beverage.CHAMPAGNE, 5); // 5개이므로 22000 할인이어야 하지만, 음료만주문이므로 0
        List<Order> orders = List.of(order);
        assertThatThrownBy(() -> new Orders(orders)).isInstanceOf(IllegalArgumentException.class);
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

    @Test
    @DisplayName("12만원 이상 주문하면 증점품 지급이 가능하다")
    void isFreeGiftApplicable() {
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order dessertOrder = new Order(Dessert.ICE_CREAM, 4); // 2만원, 도합 12만원
        orders = new Orders(List.of(mainOrder, dessertOrder));
        assertThat(orders.isFreeGiftApplicable()).isTrue();
    }

    @Test
    @DisplayName("12만원 미만으로 주문하면 증점품을 지급하지 않는다.")
    void isFreeGiftApplicable_UNDER_STANDARD() {
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order tapasOrder = new Order(Appetizer.TAPAS, 2); // 1만 1000원
        Order caesarSaladOrder = new Order(Appetizer.CAESAR_SALAD, 1); // 8000 원 -> 도합 199_000 원
        orders = new Orders(List.of(mainOrder, tapasOrder, caesarSaladOrder));
        assertThat(orders.isFreeGiftApplicable()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25})
    @DisplayName("D-DAY 할인의 적용률 테스트")
    void discountByDDay(int day) {
        OrderDay orderDay = new OrderDay(day);

        int discountByDDay = orders.getDDayDiscount(orderDay);
        int estimatedDiscount = 1000 + (day - 1) * 100; // 할인 계산식
        assertThat(discountByDDay).isEqualTo(estimatedDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    @DisplayName("25일 이후 날짜는 D-DAY 할인의 적용을 받지 않음을 확인")
    void discountByDDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);

        int discountByDDay = orders.getDDayDiscount(orderDay);
        assertThat(discountByDDay).isZero();
    }

    @Test
    @DisplayName("총 혜택금액 (할인액 + 증정품 금액) 을 테스트한다.")
    void getTotalBenefit() {
        OrderDay orderDay = new OrderDay(25);
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order dessertOrder = new Order(Dessert.ICE_CREAM, 4); // 2만원, 도합 12만원
        orders = new Orders(List.of(mainOrder, dessertOrder));
        int totalBenefit = orders.getTotalBenefit(orderDay);
        int totalDiscount = orders.getTotalDiscount(orderDay);
        int freeGiftPrice = FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
        assertThat(totalBenefit).isEqualTo(totalDiscount + freeGiftPrice);
    }

    @Test
    @DisplayName("총 혜택금액에 따른 뱃지 부여를 테스트한다.")
    void getBadge() {
        OrderDay orderDay = new OrderDay(25);
        String badge = orders.getBadge(orderDay);
        assertThat(badge).isEqualTo("산타");
    }
}