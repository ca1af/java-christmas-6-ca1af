package benefit;

import date.OrderDay;
import gift.Badge;
import gift.FreeGift;
import order.Orders;

import static benefit.DiscountConstant.*;

public class DiscountCalculator {
    private final Orders orders;

    public DiscountCalculator(Orders orders) {
        this.orders = orders;
    }

    public int getTotalBenefit(OrderDay orderDay){
        return getTotalDiscount(orderDay) + getFreeGiftBenefit();
    }

    public int getFreeGiftBenefit(){
        if (this.getOrderAmount() >= FREE_GIFT_STANDARD){
            return FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
        }
        return NO_DISCOUNT;
    }

    public int getOrderAmount() {
        return orders.getOrderAmount();
    }

    public int getFinalPrice(OrderDay orderDay) {
        return this.getOrderAmount() - this.getTotalDiscount(orderDay);
    }

    public int getTotalDiscount(OrderDay orderDay) {
        if (isOrderBelowMinimumOrderAmountOfDiscount()){
            return NO_DISCOUNT;
        }
        int starDayDiscount = getStarDayDiscount(orderDay);
        int dayOfWeekDiscount = getDayOfWeekDiscount(orderDay);
        int dDayDiscount = getDDayDiscount(orderDay);
        return dDayDiscount + starDayDiscount + dayOfWeekDiscount;
    }

    public int getActualTotalPrice(OrderDay orderDay){
        return orders.getOrderAmount() - getTotalDiscount(orderDay);
    }

    private boolean isOrderBelowMinimumOrderAmountOfDiscount() {
        return orders.isOrderBelowMinimumOrderAmountOfDiscount();
    }

    public int getStarDayDiscount(OrderDay orderDay){
        if (orderDay.isStarDay()){
            return STAR_DAY_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    public int getDayOfWeekDiscount(OrderDay orderDay) {
        return orders.getDayOfWeekDiscount(orderDay);
    }

    public int getDDayDiscount(OrderDay orderDay){
        if (orderDay.isDDayApplicable()){
            return orderDay.getDDayApplicableDays() * D_DAY_DISCOUNT_AMOUNT_PER_DAY + D_DAY_DISCOUNT_START_PRICE;
        }
        return NO_DISCOUNT;
    }

    public String getBadge(OrderDay orderDay){
        int totalBenefit = this.getTotalBenefit(orderDay);
        return Badge.getBadgeNameByPrice(totalBenefit);
    }
}
