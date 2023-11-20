package order;

import date.OrderDay;
import menu.category.Beverage;

import java.util.ArrayList;
import java.util.List;

import static benefit.DiscountConstant.*;

public record Orders(List<Order> orders) {
    private static final int MAX_MENU_QUANTITY = 20;

    public Orders {
        validate(orders);
    }

    public void validate(List<Order> orders) {
        if (containsOnlyBeverages(orders)) {
            throw new IllegalArgumentException();
        }
        if (isQuantityExceeded(orders)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isQuantityExceeded(List<Order> orders) {
        return orders.stream()
                .mapToInt(Order::getMenuQuantity)
                .sum() > MAX_MENU_QUANTITY;
    }

    private boolean containsOnlyBeverages(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getMenu() instanceof Beverage)
                .count() == orders.size();
    }

    public int getOrderAmount() {
        return orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum();
    }

    public boolean isOrderBelowMinimumOrderAmountOfDiscount() {
        return this.orders.stream()
                .mapToInt(Order::getOrderAmount)
                .sum() < MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT;
    }

    public int getDayOfWeekDiscount(OrderDay orderDay) {
        return orders.stream()
                .mapToInt(order -> order.getDayOfWeekDiscount(orderDay))
                .sum();
    }

    @Override
    public List<Order> orders() {
        return new ArrayList<>(orders);
    }
}
