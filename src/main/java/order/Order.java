package order;

import date.OrderDay;
import menu.Menu;

import static benefit.DiscountConstant.DAY_OF_WEEK_DISCOUNT;
import static benefit.DiscountConstant.NO_DISCOUNT;

public record Order(Menu menu, int menuQuantity) {

    public int getDayOfWeekDiscount(OrderDay orderDay) {
        int dayOfWeekDiscount = calculateDayOfWeekDiscount(orderDay);
        return dayOfWeekDiscount * menuQuantity;
    }

    private int calculateDayOfWeekDiscount(OrderDay orderDay) {
        int dayOfWeekDiscount = NO_DISCOUNT;
        if (menu.isDayOfWeekApplicable(orderDay)) {
            dayOfWeekDiscount = DAY_OF_WEEK_DISCOUNT;
        }
        return dayOfWeekDiscount;
    }

    public String getMenuName() {
        return this.menu.getName();
    }

    public int getOrderAmount() {
        return this.menu.getPrice() * menuQuantity;
    }
}