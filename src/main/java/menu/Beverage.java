package menu;

import date.OrderDay;

public enum Beverage implements DateDiscountPolicy{
    ZERO_COLA("제로콜라", 3_000),
    RED_WINE("레드와인",60_000),
    CHAMPAGNE("샴페인",25_000)
    ;

    private final String name;
    private final int price;

    Beverage(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean isDayOfWeekApplicable(OrderDay orderDay) {
        return false;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
