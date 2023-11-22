package gift;

import menu.Menu;
import menu.category.Beverage;

public enum FreeGift {
    FOR_CHRISTMAS_EVENT(Beverage.CHAMPAGNE);
    private final int price;
    FreeGift(Menu menu) {
        this.price = menu.getPrice();
    }

    public int getPrice() {
        return price;
    }
}
