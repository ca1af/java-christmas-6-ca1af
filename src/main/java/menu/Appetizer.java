package menu;

import date.OrderDay;

public enum Appetizer implements Menu {
    MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000);

    private final String name;
    private final int price;

    Appetizer(String name, int price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public boolean isDayOfWeekApplicable(OrderDay orderDay) {
        return false;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
