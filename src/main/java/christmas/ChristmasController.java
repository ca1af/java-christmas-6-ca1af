package christmas;

import benefit.DiscountCalculator;
import console.DiscountDetailOutputView;
import console.InputView;
import console.OutputView;
import date.OrderDay;
import order.Orders;

public class ChristmasController {
    private final InputView inputView;
    private final OutputView outputView;
    private final DiscountDetailOutputView discountDetailOutputView;

    public ChristmasController(InputView inputView, OutputView outputView, DiscountDetailOutputView discountDetailOutputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.discountDetailOutputView = discountDetailOutputView;
    }

    public void game(){
        UserOrderDetail userOrderDetail = getUserOrderDetail();

        printOrderDetail(userOrderDetail);
    }

    private void printOrderDetail(UserOrderDetail userOrderDetail) {
        outputView.printOrderMenus(userOrderDetail.userOrders());
        outputView.printOrderAmountBeforeBenefit(userOrderDetail.userOrders());
        outputView.printFreeGift(userOrderDetail.discountCalculator());
        discountDetailOutputView.printDiscountDetails(userOrderDetail.discountCalculator(), userOrderDetail.orderDay());
        outputView.printTotalBenefit(userOrderDetail.discountCalculator(), userOrderDetail.orderDay());
        outputView.printActualTotalPrice(userOrderDetail.discountCalculator(), userOrderDetail.orderDay());
        outputView.printEventBadge(userOrderDetail.discountCalculator(), userOrderDetail.orderDay());
    }

    private UserOrderDetail getUserOrderDetail() {
        outputView.printGreetings();
        OrderDay orderDay = inputView.readDate();
        Orders userOrders = inputView.readOrders();
        DiscountCalculator discountCalculator = new DiscountCalculator(userOrders);
        outputView.printBenefitPreview();
        return new UserOrderDetail(orderDay, discountCalculator, userOrders);
    }

    private record UserOrderDetail(OrderDay orderDay, DiscountCalculator discountCalculator, Orders userOrders) {
    }
}