package console;

import date.OrderDay;
import order.Order;
import order.Orders;

import java.util.List;

public class OutputView {
    private static final String GREETINGS = "안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.";
    private static final String BENEFIT_PREVIEW = "12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";
    private static final String ORDER_MENU = "<주문 메뉴>";
    private static final String SPACE = " ";
    private static final String ORDER_AMOUNT = "<할인 전 총주문 금액>";
    private static final String WON = "원";
    private static final String FREE_GIFT = "<증정 메뉴>";
    private static final String FREE_GIFT_CHAMPAIGN = "샴페인 1개";
    private static final String NONE = "없음";
    private static final String TOTAL_BENEFIT = "<총혜택 금액>";
    private static final String ACTUAL_TOTAL_PRICE = "<할인 후 예상 결제 금액>";
    private static final String EVENT_BADGE = "<12월 이벤트 배지>";
    public void printGreetings(){
        System.out.println(GREETINGS);
    }
    public void printBenefitPreview(){
        System.out.println(BENEFIT_PREVIEW);
    }

    public void printOrderMenus(Orders orders){
        System.out.println(ORDER_MENU);
        List<Order> userOrders = orders.getOrders();
        userOrders.forEach(this::printOrderMenu);
    }

    public void printGetOrderAmount(Orders orders){
        System.out.println(ORDER_AMOUNT);
        String orderAmount = orders.getOrderAmount() + WON;
        System.out.println(orderAmount);
    }

    public void printFreeGift(Orders orders){
        System.out.println(FREE_GIFT);
        String freeGift = getFreeGift(orders);
        System.out.println(freeGift);
    }

    public void printTotalBenefit(Orders orders, OrderDay orderDay){
        System.out.println(TOTAL_BENEFIT);
        System.out.println(orders.getTotalBenefit(orderDay) + WON);
    }

    public void printActualTotalPrice(Orders orders, OrderDay orderDay){
        System.out.println(ACTUAL_TOTAL_PRICE);
        int actualTotalPrice = orders.getOrderAmount() - orders.getTotalDiscount(orderDay);
        System.out.println(actualTotalPrice + WON);
    }

    public void printEventBadge(Orders orders, OrderDay orderDay){
        System.out.println(EVENT_BADGE);
        System.out.println(orders.getBadge(orderDay));
    }

    private void printOrderMenu(Order order){
        String menuName = order.getMenuName();
        int menuQuantity = order.getMenuQuantity();
        System.out.println(menuName + SPACE + menuQuantity);
    }

    private String getFreeGift(Orders orders) {
        if (orders.isFreeGiftApplicable()){
            return FREE_GIFT_CHAMPAIGN;
        }
        return NONE;
    }
}
