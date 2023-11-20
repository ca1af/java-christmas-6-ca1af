package console;

import benefit.DiscountCalculator;
import date.OrderDay;
import gift.FreeGift;

import static console.OutputConstants.*;

public class DiscountDetailOutputView {
    private static final int NO_DISCOUNT = 0;
    public void printDiscountDetails(DiscountCalculator discountCalculator, OrderDay orderDay){
        System.out.println(DISCOUNT_DETAILS);
        if (isDiscountNotApplicable(discountCalculator, orderDay)) {
            System.out.println(NONE);
            return;
        }
        printDDayDiscount(discountCalculator, orderDay);
        printDayOfWeekDiscount(discountCalculator, orderDay);
        printStarDayDiscount(discountCalculator, orderDay);
        printFreeGift(discountCalculator);
    }

    private boolean isDiscountNotApplicable(DiscountCalculator discountCalculator, OrderDay orderDay) {
        return discountCalculator.getTotalDiscount(orderDay) == NO_DISCOUNT;
    }

    private void printDDayDiscount(DiscountCalculator discountCalculator, OrderDay orderDay) {
        int discountByDDay = discountCalculator.getDDayDiscount(orderDay);
        if (discountByDDay != NO_DISCOUNT){
            String discountMessage = this.getDiscountMessage(D_DAY_DISCOUNT, discountByDDay);
            System.out.println(discountMessage);
        }
    }

    private void printDayOfWeekDiscount(DiscountCalculator discountCalculator, OrderDay orderDay) {
        int dayOfWeekDiscount = discountCalculator.getDayOfWeekDiscount(orderDay);
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

    private void printStarDayDiscount(DiscountCalculator discountCalculator, OrderDay orderDay) {
        if (discountCalculator.getStarDayDiscount(orderDay) != NO_DISCOUNT){
            String discountMessage = this.getDiscountMessage(STAR_DISCOUNT, discountCalculator.getStarDayDiscount(orderDay));
            System.out.println(discountMessage);
        }
    }

    private void printFreeGift(DiscountCalculator orders) {
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