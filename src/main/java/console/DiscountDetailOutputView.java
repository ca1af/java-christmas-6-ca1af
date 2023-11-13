package console;

import date.OrderDay;
import menu.category.Beverage;
import order.Orders;

public class DiscountDetailOutputView {
    private static final String DISCOUNT_DETAILS = "<혜택 내역>";
    private static final String WON = "원";
    private static final String D_DAY_DISCOUNT = "크리스마스 디데이 할인: ";
    private static final String WEEK_DAY_DISCOUNT = "평일 할인: ";
    private static final String WEEKEND_DISCOUNT = "주말 할인: ";
    private static final String STAR_DISCOUNT = "특별 할인: ";
    private static final String FREE_GIFT_EVENT = "증정 이벤트: ";
    private static final String NONE = "없음";
    private static final String MINUS = "-";
    private static final int NO_DISCOUNT = 0;
    public void printDiscountDetails(Orders orders, OrderDay orderDay){
        System.out.println(DISCOUNT_DETAILS);
        if (isDiscountNotApplicable(orders, orderDay)) {
            return;
        }
        printDDayDiscount(orders, orderDay);
        printDayOfWeekDiscount(orders, orderDay);
        printStarDayDiscount(orders, orderDay);
        printFreeGift(orders);
    }

    private boolean isDiscountNotApplicable(Orders orders, OrderDay orderDay) {
        if (orders.getTotalDiscount(orderDay) == NO_DISCOUNT){
            System.out.println(NONE);
            return true;
        }
        return false;
    }

    private void printDDayDiscount(Orders orders, OrderDay orderDay) {
        int discountByDDay = orders.getDiscountByDDay(orderDay);
        if (discountByDDay != NO_DISCOUNT){
            System.out.println(getDiscountMessage(D_DAY_DISCOUNT, discountByDDay));
        }
    }

    private void printDayOfWeekDiscount(Orders orders, OrderDay orderDay) {
        int dayOfWeekDiscount = orders.getDayOfWeekDiscount(orderDay);
        if (dayOfWeekDiscount == NO_DISCOUNT){
            return;
        }
        String dayOfWeek = getDayOfWeek(orderDay);
        System.out.println(getDiscountMessage(dayOfWeek, dayOfWeekDiscount));
    }

    private void printStarDayDiscount(Orders orders, OrderDay orderDay) {
        if (orders.getStarDayDiscount(orderDay) != NO_DISCOUNT){
            System.out.println(getDiscountMessage(STAR_DISCOUNT, orders.getStarDayDiscount(orderDay)));
        }
    }

    private void printFreeGift(Orders orders) {
        if (orders.isFreeGiftApplicable()){
            System.out.println(getDiscountMessage(FREE_GIFT_EVENT, Beverage.CHAMPAGNE.getPrice()));
        }
    }

    private String getDiscountMessage(String message, int discount) {
        return message + MINUS + discount + WON;
    }

    private String getDayOfWeek(OrderDay orderDay) {
        if (orderDay.isWeekend()){
            return WEEKEND_DISCOUNT;
        }
        return WEEK_DAY_DISCOUNT;
    }
}