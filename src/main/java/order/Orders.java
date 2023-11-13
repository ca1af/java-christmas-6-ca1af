package order;

import date.OrderDay;
import gift.Badge;
import gift.FreeGift;
import menu.category.Beverage;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private static final int NO_DISCOUNT = 0;
    private static final int MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT = 10000;
    private static final int STAR_DAY_DISCOUNT = 1_000;
    private static final int FREE_GIFT_STANDARD = 120_000;
    private static final int D_DAY_DISCOUNT_START_PRICE = 1000;
    private static final int D_DAY_DISCOUNT_AMOUNT_PER_DAY = 100;
    private final List<Order> orders;
    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public int getOrderAmount() {
        return orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum();
    }

    public int getTotalBenefit(OrderDay orderDay){
        if (this.isFreeGiftApplicable()){
            return getTotalDiscount(orderDay) + FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
        }
        return getTotalDiscount(orderDay);
    }

    public int getTotalDiscount(OrderDay orderDay) {
        if (containsOnlyBeverages()){
            return NO_DISCOUNT;
        }
        if (isOrderBelowMinimumOrderAmountOfDiscount()){
            return NO_DISCOUNT;
        }
        int starDayDiscount = getStarDayDiscount(orderDay);
        int dayOfWeekDiscount = getDayOfWeekDiscount(orderDay);
        return starDayDiscount + dayOfWeekDiscount;
    }

    public int getDayOfWeekDiscount(OrderDay orderDay) {
        return orders.stream()
                .mapToInt(order -> order.getTotalDiscount(orderDay))
                .sum();
    }

    public int getStarDayDiscount(OrderDay orderDay){
        if (orderDay.isStarDay()){
            return STAR_DAY_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    public int getDiscountByDDay(OrderDay orderDay){
        if (orderDay.isDDayApplicable()){
            return orderDay.getDDayApplicableDays() * D_DAY_DISCOUNT_AMOUNT_PER_DAY + D_DAY_DISCOUNT_START_PRICE;
        }
        return NO_DISCOUNT;
    }

    public boolean isFreeGiftApplicable(){
        return !this.containsOnlyBeverages() && this.getOrderAmount() >= FREE_GIFT_STANDARD;
    }

    public int getFinalPrice(OrderDay orderDay) {
        return this.getOrderAmount() - this.getTotalDiscount(orderDay);
    }

    public Badge getBadge(OrderDay orderDay){
        int totalBenefit = this.getTotalBenefit(orderDay);
        return Badge.getBadgeByPrice(totalBenefit);
    }

    private boolean isOrderBelowMinimumOrderAmountOfDiscount() {
        return this.orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum() < MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT;
    }

    private boolean containsOnlyBeverages() {
        return orders.stream()
                .filter(order -> order.getMenu() instanceof Beverage)
                .count() == orders.size();
    }
}
