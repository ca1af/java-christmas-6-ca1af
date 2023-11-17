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
    private static final int MAX_MENU_QUANTITY = 20;
    private final List<Order> orders;
    public Orders(List<Order> orders) {
        validate(orders);
        this.orders = orders;
    }

    public void validate(List<Order> orders){
        if (containsOnlyBeverages(orders)){
            throw new IllegalArgumentException();
        }
        if (isQuantityExceeded(orders)){
            throw new IllegalArgumentException();
        }
    }

    private boolean isQuantityExceeded(List<Order> orders){
        return orders.stream()
                .mapToInt(Order::getMenuQuantity)
                .sum() > MAX_MENU_QUANTITY;
    }

    private boolean containsOnlyBeverages(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getMenu() instanceof Beverage)
                .count() == orders.size();
    }

    public int getTotalBenefit(OrderDay orderDay){
        if (this.isFreeGiftApplicable()){
            return getTotalDiscount(orderDay) + FreeGift.FOR_CHRISTMAS_EVENT.getPrice();
        }
        return getTotalDiscount(orderDay);
    }

    public boolean isFreeGiftApplicable(){
        return this.getOrderAmount() >= FREE_GIFT_STANDARD;
    }

    public int getOrderAmount() {
        return orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum();
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

    private boolean isOrderBelowMinimumOrderAmountOfDiscount() {
        return this.orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum() < MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT;
    }

    public int getStarDayDiscount(OrderDay orderDay){
        if (orderDay.isStarDay()){
            return STAR_DAY_DISCOUNT;
        }
        return NO_DISCOUNT;
    }

    public int getDayOfWeekDiscount(OrderDay orderDay) {
        return orders.stream()
                .mapToInt(order -> order.getDayOfWeekDiscount(orderDay))
                .sum();
    }

    public int getDDayDiscount(OrderDay orderDay){
        if (orderDay.isDDayApplicable()){
            return orderDay.getDDayApplicableDays() * D_DAY_DISCOUNT_AMOUNT_PER_DAY + D_DAY_DISCOUNT_START_PRICE;
        }
        return NO_DISCOUNT;
    }

    public String getBadge(OrderDay orderDay){
        int totalBenefit = this.getTotalBenefit(orderDay);
        Badge badge = Badge.getBadgeByPrice(totalBenefit);
        return badge.getName();
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }
}
