package menu;

import date.OrderDay;

public enum Dessert implements Menu {
    CHOCOLATE_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000);
    private final String name;
    private final int price;

    Dessert(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean isDayOfWeekApplicable(OrderDay orderDay) {
        return !orderDay.isWeekend();
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
