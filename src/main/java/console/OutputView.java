package console;

import benefit.DiscountCalculator;
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
        List<Order> userOrders = orders.orders();
        userOrders.forEach(this::printOrderMenu);
    }

    public void printOrderAmountBeforeBenefit(Orders orders){
        System.out.println(ORDER_AMOUNT);
        int orderAmount = orders.getOrderAmount();
        String formattedOrderAmount = Formatter.formatPrice(orderAmount);
        System.out.println(formattedOrderAmount + WON);
    }

    public void printFreeGift(DiscountCalculator discountCalculator){
        System.out.println(FREE_GIFT);
        String freeGift = getFreeGift(discountCalculator);
        System.out.println(freeGift);
    }

    public void printTotalBenefit(DiscountCalculator discountCalculator, OrderDay orderDay){
        System.out.println(TOTAL_BENEFIT);
        int totalBenefit = discountCalculator.getTotalBenefit(orderDay);
        String formattedTotalBenefit = Formatter.formatPrice(totalBenefit);
        System.out.println(MINUS + formattedTotalBenefit + WON);
    }

    public void printActualTotalPrice(DiscountCalculator discountCalculator, OrderDay orderDay){
        System.out.println(ACTUAL_TOTAL_PRICE);
        int actualTotalPrice = discountCalculator.getActualTotalPrice(orderDay);
        String formatPrice = Formatter.formatPrice(actualTotalPrice);
        System.out.println(formatPrice + WON);
    }

    public void printEventBadge(DiscountCalculator discountCalculator, OrderDay orderDay){
        System.out.println(EVENT_BADGE);
        System.out.println(discountCalculator.getBadge(orderDay));
    }

    private void printOrderMenu(Order order){
        String menuName = order.getMenuName();
        int menuQuantity = order.getMenuQuantity();
        System.out.println(menuName + SPACE + menuQuantity + UNIT_SUFFIX);
    }

    private String getFreeGift(DiscountCalculator discountCalculator) {
        if (discountCalculator.isFreeGiftApplicable()){
            return FREE_GIFT_CHAMPAIGN;
        }
        return NONE;
    }
}
