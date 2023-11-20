package benefit;

import date.OrderDay;
import gift.FreeGift;
import menu.category.Appetizer;
import menu.category.Beverage;
import menu.category.Dessert;
import menu.category.Main;
import order.Order;
import order.Orders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCalculatorTest {

    private Order beverage;
    private Order main;
    private Orders orders;
    private DiscountCalculator discountCalculator;
    @BeforeEach
    void init() {
        beverage = new Order(Beverage.CHAMPAGNE, 5);
        main = new Order(Main.BARBEQUE_RIB, 5);
        orders = new Orders(List.of(beverage, main));
        discountCalculator = new DiscountCalculator(orders);
    }

    @ParameterizedTest
    @ValueSource(ints = {8, 9, 15, 16, 22, 23, 29, 30})
    void dayOfWeekDiscount_IS_DUPLICABLE(int day) {
        OrderDay orderDay = new OrderDay(day);
        Order barbequeRib = new Order(Main.BARBEQUE_RIB, 2);
        Order seafoodPasta = new Order(Main.SEAFOOD_PASTA, 2);
        orders = new Orders(List.of(barbequeRib, seafoodPasta));
        discountCalculator = new DiscountCalculator(orders);
        int orderDiscount = barbequeRib.getDayOfWeekDiscount(orderDay) + seafoodPasta.getDayOfWeekDiscount(orderDay);
        int dDayDiscount = discountCalculator.getDDayDiscount(orderDay);
        assertThat(orderDiscount + dDayDiscount).isEqualTo(discountCalculator.getTotalDiscount(orderDay));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6,7,8,9,10,25,31})
    @DisplayName("날짜와 관계없이 order 들의 할인 총액은 discount 의 할인총액과 같다.")
    void getTotalDiscountAmount(int day) {
        OrderDay orderDay = new OrderDay(day);
        int beverageTotalDiscount = beverage.getDayOfWeekDiscount(orderDay);
        int mainTotalDiscount = main.getDayOfWeekDiscount(orderDay);
        int orderDiscountAmount = beverageTotalDiscount + mainTotalDiscount;

        int totalDiscount = discountCalculator.getTotalDiscount(orderDay);
        if (orderDay.isStarDay()){ // 특별 할인은 총 주문 금액 기준이므로 한번만 더한다.
            orderDiscountAmount += discountCalculator.getStarDayDiscount(orderDay);
        }
        if (orderDay.isDDayApplicable()){
            orderDiscountAmount += discountCalculator.getDDayDiscount(orderDay);
        }
        assertThat(orderDiscountAmount).isEqualTo(totalDiscount);
    }

    @Test
    @DisplayName("금액이 10000원 이하인 주문은 할인하지 않는다.")
    void NO_DISCOUNT_ORDER_AMOUNT_UDER_10000() {
        OrderDay orderDay = new OrderDay(25); // D-Day, Star 할인이 적용된다 ( total 4400 )
        Order order = new Order(Dessert.ICE_CREAM, 1);
        orders = new Orders(List.of(order));
        discountCalculator = new DiscountCalculator(orders);
        assertThat(discountCalculator.getTotalDiscount(orderDay)).isZero();
    }

    @Test
    @DisplayName("할인 후 예상 결제 금액(final price)은 총 금액 - 총 할인액이다.")
    void getFinalPrice() {
        OrderDay orderDay = new OrderDay(25); // D-Day, Star 할인이 적용된다 ( total 4400 )
        int finalPrice = discountCalculator.getFinalPrice(orderDay);
        int orderAmount = discountCalculator.getOrderAmount(); // 원래 총 금액
        int totalDiscount = discountCalculator.getTotalDiscount(orderDay); // 총 할인액
        assertThat(finalPrice).isEqualTo(orderAmount - totalDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 10, 17, 24, 25})
    @DisplayName("달력에 별이 찍힌 날을 계산하는 로직을 테스트.")
    void isStarDay(int starDay) {
        OrderDay orderDay = new OrderDay(starDay);
        int starDayDiscount = discountCalculator.getStarDayDiscount(orderDay);
        assertThat(starDayDiscount).isEqualTo(1000);
    }

    @Test
    @DisplayName("12만원 이상 주문하면 증점품 지급이 가능하다")
    void isFreeGiftApplicable() {
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order dessertOrder = new Order(Dessert.ICE_CREAM, 4); // 2만원, 도합 12만원
        orders = new Orders(List.of(mainOrder, dessertOrder));
        discountCalculator = new DiscountCalculator(orders);
        assertThat(discountCalculator.isFreeGiftApplicable()).isTrue();
    }

    @Test
    @DisplayName("12만원 미만으로 주문하면 증점품을 지급하지 않는다.")
    void isFreeGiftApplicable_UNDER_STANDARD() {
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order tapasOrder = new Order(Appetizer.TAPAS, 2); // 1만 1000원
        Order caesarSaladOrder = new Order(Appetizer.CAESAR_SALAD, 1); // 8000 원 -> 도합 199_000 원
        orders = new Orders(List.of(mainOrder, tapasOrder, caesarSaladOrder));
        discountCalculator = new DiscountCalculator(orders);
        assertThat(discountCalculator.isFreeGiftApplicable()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 15, 20, 25})
    @DisplayName("D-DAY 할인의 적용률 테스트")
    void discountByDDay(int day) {
        OrderDay orderDay = new OrderDay(day);

        int discountByDDay = discountCalculator.getDDayDiscount(orderDay);
        int estimatedDiscount = 1000 + (day - 1) * 100; // 할인 계산식
        assertThat(discountByDDay).isEqualTo(estimatedDiscount);
    }

    @ParameterizedTest
    @ValueSource(ints = {26, 27, 28, 29, 30, 31})
    @DisplayName("25일 이후 날짜는 D-DAY 할인의 적용을 받지 않음을 확인")
    void discountByDDay_NONE(int day) {
        OrderDay orderDay = new OrderDay(day);

        int discountByDDay = discountCalculator.getDDayDiscount(orderDay);
        assertThat(discountByDDay).isZero();
    }

    @Test
    @DisplayName("총 혜택금액 (할인액 + 증정품 금액) 을 테스트한다.")
    void getTotalBenefit() {
        OrderDay orderDay = new OrderDay(25);
        Order mainOrder = new Order(Main.CHRISTMAS_PASTA, 4); // 10만원
        Order dessertOrder = new Order(Dessert.ICE_CREAM, 4); // 2만원, 도합 12만원
        orders = new Orders(List.of(mainOrder, dessertOrder));
        discountCalculator = new DiscountCalculator(orders);
        int totalBenefit = discountCalculator.getTotalBenefit(orderDay);
        int totalDiscount = discountCalculator.getTotalDiscount(orderDay);
        int freeGiftPrice = FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
        assertThat(totalBenefit).isEqualTo(totalDiscount + freeGiftPrice);
    }

    @Test
    @DisplayName("총 혜택금액에 따른 뱃지 부여를 테스트한다.")
    void getBadge() {
        OrderDay orderDay = new OrderDay(25);
        String badge = discountCalculator.getBadge(orderDay);
        assertThat(badge).isEqualTo("산타");
    }
}