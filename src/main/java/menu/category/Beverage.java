package menu.category;

import date.OrderDay;
import menu.Menu;

public enum Beverage implements Menu {
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
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getPrice() {
        return price;
    }

}
