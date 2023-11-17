package console;

import date.OrderDay;
import gift.FreeGift;
import order.Orders;

import static console.OutputConstants.*;

public class DiscountDetailOutputView {
    private static final int NO_DISCOUNT = 0;
    public void printDiscountDetails(Orders orders, OrderDay orderDay){
        System.out.println(DISCOUNT_DETAILS);
        if (isDiscountNotApplicable(orders, orderDay)) {
            System.out.println(NONE);
            return;
        }
        printDDayDiscount(orders, orderDay);
        printDayOfWeekDiscount(orders, orderDay);
        printStarDayDiscount(orders, orderDay);
        printFreeGift(orders);
    }

    private boolean isDiscountNotApplicable(Orders orders, OrderDay orderDay) {
        return orders.getTotalDiscount(orderDay) == NO_DISCOUNT;
    }

    private void printDDayDiscount(Orders orders, OrderDay orderDay) {
        int discountByDDay = orders.getDDayDiscount(orderDay);
        if (discountByDDay != NO_DISCOUNT){
            String discountMessage = this.getDiscountMessage(D_DAY_DISCOUNT, discountByDDay);
            System.out.println(discountMessage);
        }
    }

    private void printDayOfWeekDiscount(Orders orders, OrderDay orderDay) {
        int dayOfWeekDiscount = orders.getDayOfWeekDiscount(orderDay);
        if (dayOfWeekDiscount == NO_DISCOUNT){
            return;
        }
        String dayOfWeek = getDayOfWeek(orderDay);
        String discountMessage = this.getDiscountMessage(dayOfWeek, dayOfWeekDiscount);
        System.out.println(discountMessage);
    }

    private String getDayOfWeek(OrderDay orderDay) {
        if (orderDay.isWeekend()){
            return WEEKEND_DISCOUNT;
        }
        return WEEK_DAY_DISCOUNT;
    }

    private void printStarDayDiscount(Orders orders, OrderDay orderDay) {
        if (orders.getStarDayDiscount(orderDay) != NO_DISCOUNT){
            String discountMessage = this.getDiscountMessage(STAR_DISCOUNT, orders.getStarDayDiscount(orderDay));
            System.out.println(discountMessage);
        }
    }

    private void printFreeGift(Orders orders) {
        if (orders.isFreeGiftApplicable()){
            int freeGiftPrice = FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
            String discountMessage = this.getDiscountMessage(FREE_GIFT_EVENT, freeGiftPrice);
            System.out.println(discountMessage);
        }
    }

    private String getDiscountMessage(String message, int discount) {
        String formattedPrice = Formatter.formatPrice(discount);
        return message + MINUS + formattedPrice + WON;
    }
}