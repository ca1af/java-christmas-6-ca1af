package order;

import date.OrderDay;
import menu.Menu;

public class Order {
    private static final int DAY_OF_WEEK_DISCOUNT = 2_023;
    private static final int NO_DISCOUNT = 0;
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