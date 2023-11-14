package console;

import date.OrderDay;
import order.Order;
import order.Orders;

import java.util.List;

import static console.OutputConstants.*;

public class OutputView {
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

    public void printOrderAmountBeforeBenefit(Orders orders){
        System.out.println(ORDER_AMOUNT);
        int orderAmount = orders.getOrderAmount();
        String formattedOrderAmount = Formatter.formatPrice(orderAmount);
        System.out.println(formattedOrderAmount + WON);
    }

    public void printFreeGift(Orders orders){
        System.out.println(FREE_GIFT);
        String freeGift = getFreeGift(orders);
        System.out.println(freeGift);
    }

    public void printTotalBenefit(Orders orders, OrderDay orderDay){
        System.out.println(TOTAL_BENEFIT);
        int totalBenefit = orders.getTotalBenefit(orderDay);
        String formattedTotalBenefit = Formatter.formatPrice(totalBenefit);
        System.out.println(MINUS + formattedTotalBenefit + WON);
    }

    public void printActualTotalPrice(Orders orders, OrderDay orderDay){
        System.out.println(ACTUAL_TOTAL_PRICE);
        int actualTotalPrice = orders.getOrderAmount() - orders.getTotalDiscount(orderDay);
        String formatPrice = Formatter.formatPrice(actualTotalPrice);
        System.out.println(formatPrice + WON);
    }

    public void printEventBadge(Orders orders, OrderDay orderDay){
        System.out.println(EVENT_BADGE);
        System.out.println(orders.getBadge(orderDay));
    }

    private void printOrderMenu(Order order){
        String menuName = order.getMenuName();
        int menuQuantity = order.getMenuQuantity();
        System.out.println(menuName + SPACE + menuQuantity + UNIT_SUFFIX);
    }

    private String getFreeGift(Orders orders) {
        if (orders.isFreeGiftApplicable()){
            return FREE_GIFT_CHAMPAIGN;
        }
        return NONE;
    }
}
