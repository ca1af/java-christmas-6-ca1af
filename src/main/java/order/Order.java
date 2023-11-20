package order;

import date.OrderDay;
import menu.Menu;

import static benefit.DiscountConstant.DAY_OF_WEEK_DISCOUNT;
import static benefit.DiscountConstant.NO_DISCOUNT;

public class Order {
    private final Menu menu;
    private final int menuQuantity;

    public Order(Menu menu, int menuQuantity) {
        this.menu = menu;
        this.menuQuantity = menuQuantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getDayOfWeekDiscount(OrderDay orderDay){
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

    public String getMenuName(){
        return this.menu.getName();
    }

    public int getMenuQuantity(){
        return this.menuQuantity;
    }

    public int getOrderAmount(){
        return this.menu.getPrice() * menuQuantity;
    }
}