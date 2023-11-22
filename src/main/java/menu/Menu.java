package menu;

import date.OrderDay;

public interface Menu { //날짜에 의한 할인 정책
    boolean isDayOfWeekApplicable(OrderDay orderDay);
    String getName();
    int getPrice();
}
