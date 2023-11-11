package menu;

import date.OrderDay;

public interface DateDiscountPolicy { //날짜에 의한 할인 정책
    int DAY_OF_WEEK_DISCOUNT = 2_023;
    int STAR_DAY_DISCOUNT = 1_000;
    int D_DAY_DISCOUNT_START_PRICE = 1000;
    int D_DAY_DISCOUNT_AMOUNT_PER_DAY = 100;
    int NOT_APPLICABLE = 0;

    boolean isDayOfWeekApplicable(OrderDay orderDay);

    default int discountByStarDay(OrderDay orderDay){
        if (orderDay.isStarDay()){
            return STAR_DAY_DISCOUNT;
        }
        return NOT_APPLICABLE;
    }

    default int discountByDDay(OrderDay orderDay){
        if (orderDay.isDDayApplicable()){
            return orderDay.getDDayApplicableDays() * D_DAY_DISCOUNT_AMOUNT_PER_DAY + D_DAY_DISCOUNT_START_PRICE;
        }
        return NOT_APPLICABLE;
    }

    default int getTotalDiscount(OrderDay orderDay){
        int dayOfWeekDiscount = NOT_APPLICABLE;
        if (isDayOfWeekApplicable(orderDay)) {
            dayOfWeekDiscount = DAY_OF_WEEK_DISCOUNT;
        }
        int discountForDDay = discountByDDay(orderDay);
        int discountForStarDay = discountByStarDay(orderDay);
        return discountForStarDay + discountForDDay + dayOfWeekDiscount;
    }
}
