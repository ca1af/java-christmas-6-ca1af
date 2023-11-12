package order;

import date.OrderDay;
import menu.Menu;

public class Order {
    private static final int DAY_OF_WEEK_DISCOUNT = 2_023;
    private static final int STAR_DAY_DISCOUNT = 1_000;
    private static final int D_DAY_DISCOUNT_START_PRICE = 1000;
    private static final int D_DAY_DISCOUNT_AMOUNT_PER_DAY = 100;
    private static final int NOT_APPLICABLE = 0;
    private final Menu menu;
    private final int menuQuantity;

    public Order(Menu menu, int menuQuantity) {
        this.menu = menu;
        this.menuQuantity = menuQuantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getOrderAmount(){
        return this.menu.getPrice() * menuQuantity;
    }

    public int getTotalDiscount(OrderDay orderDay){
        int dayOfWeekDiscount = getDayOfWeekDiscount(orderDay);
        int discountForDDay = discountByDDay(orderDay);
        int discountForStarDay = discountByStarDay(orderDay);
        int menuDiscount = discountForStarDay + discountForDDay + dayOfWeekDiscount;
        return menuDiscount * menuQuantity;
    }

    private int getDayOfWeekDiscount(OrderDay orderDay) {
        int dayOfWeekDiscount = NOT_APPLICABLE;
        if (menu.isDayOfWeekApplicable(orderDay)) {
            dayOfWeekDiscount = DAY_OF_WEEK_DISCOUNT;
        }
        return dayOfWeekDiscount;
    }

    public int discountByStarDay(OrderDay orderDay){
        if (orderDay.isStarDay()){
            return STAR_DAY_DISCOUNT;
        }
        return NOT_APPLICABLE;
    }

    public int discountByDDay(OrderDay orderDay){
        if (orderDay.isDDayApplicable()){
            return orderDay.getDDayApplicableDays() * D_DAY_DISCOUNT_AMOUNT_PER_DAY + D_DAY_DISCOUNT_START_PRICE;
        }
        return NOT_APPLICABLE;
    }
}