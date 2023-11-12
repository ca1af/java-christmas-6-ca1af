package order;

import date.OrderDay;
import menu.category.Beverage;

import java.util.List;

public class Orders {
    private static final int NO_DISCOUNT = 0;
    private static final int MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT = 10000;
    private final List<Order> orders;
    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public int getOrderAmount() {
        return orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum();
    }

    public int getTotalDiscount(OrderDay orderDay) {
        if (containsOnlyBeverages()){
            return NO_DISCOUNT;
        }
        if (isOrderBelowMinimumOrderAmountOfDiscount()){
            return NO_DISCOUNT;
        }
        return orders.stream()
                .mapToInt(order -> order.getTotalDiscount(orderDay))
                .sum();
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

    public int getFinalPrice(OrderDay orderDay) {
        return this.getOrderAmount() - this.getTotalDiscount(orderDay);
    }
}