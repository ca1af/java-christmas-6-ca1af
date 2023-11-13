package gift;

import menu.Menu;
import menu.category.Beverage;

public enum FreeGift {
    FOR_CHRISTMAS_EVENT(Beverage.CHAMPAGNE);
    private final String name;
    private final int price;
    FreeGift(Menu menu) {
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}
