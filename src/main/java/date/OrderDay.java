package date;

import java.util.Calendar;

public class OrderDay {
    private final Integer day;
    public OrderDay(int day) {
        this.day = day;
    }
    public int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, this.day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public boolean isWeekend() {
        int dayOfWeek = this.getDayOfWeek();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }

    public boolean isStarDay(){
        int dayOfWeek = this.getDayOfWeek();
        return dayOfWeek == 1 || this.day == 25;
    }

    public int getDiscountAmount() {
        int startPrice = 1000;
        int increasingDiscountAmount = (day - 1) * 100;
        if (day > 25){
            return 0;
        }
        return startPrice + increasingDiscountAmount;
    }
}
