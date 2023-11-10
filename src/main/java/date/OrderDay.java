package date;

import exeptions.ExceptionMessage;

import java.util.Calendar;

public class OrderDay {
    private static final int START_DAY = 1;
    private static final int END_DAY = 31;
    private static final int THIS_YEAR = 2023;
    private static final int D_DAY_EVENT_END_DAY = 25;
    private static final int D_DAY_DISCOUNT_START_PRICE = 1000;
    private static final int D_DAY_DISCOUNT_AMOUNT_PER_DAY = 100;
    private static final int NOT_APPLICABLE = 0;
    private final Integer day;
    public OrderDay(int day) {
        validateDay(day);
        this.day = day;
    }

    private void validateDay(int day){
        if (day < START_DAY || day > END_DAY){
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DAY.getMessage());
        }
    }

    public int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, THIS_YEAR);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isWeekend() {
        int dayOfWeek = this.getDayOfWeek();
        return dayOfWeek == Calendar.FRIDAY || dayOfWeek == Calendar.SATURDAY;
    }

    public boolean isStarDay(){
        int dayOfWeek = this.getDayOfWeek();
        return dayOfWeek == START_DAY || this.day == D_DAY_EVENT_END_DAY;
    }

    public int getDiscountAmount() {
        int increasingDiscountAmount = (this.day - START_DAY) * D_DAY_DISCOUNT_AMOUNT_PER_DAY;
        if (this.day > D_DAY_EVENT_END_DAY){
            return NOT_APPLICABLE;
        }
        return D_DAY_DISCOUNT_START_PRICE + increasingDiscountAmount;
    }
}