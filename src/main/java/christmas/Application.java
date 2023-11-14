package christmas;

import console.DiscountDetailOutputView;
import console.InputView;
import console.OutputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        DiscountDetailOutputView discountDetailOutputView = new DiscountDetailOutputView();
        ChristmasController christmasController = new ChristmasController(inputView, outputView, discountDetailOutputView);
        christmasController.game();
    }
}
