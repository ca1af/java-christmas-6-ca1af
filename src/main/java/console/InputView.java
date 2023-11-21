package console;

import camp.nextstep.edu.missionutils.Console;
import date.OrderDay;
import exeptions.ExceptionMessage;
import menu.Menu;
import menu.MenuFactory;
import order.Order;
import order.Orders;

import java.util.Arrays;
import java.util.List;

import static console.OutputConstants.*;

public class InputView {
    private static final int MINIMUM_AMOUNT_OF_ORDER = 1;
    private static final int STANDARD_LENGTH_OF_ORDER = 2;

    public OrderDay readDate() {
        System.out.println(INPUT_ESTIMATED_DATE_OF_ARRIVAL);
        String userInput = Console.readLine();
        try {
            int orderDate = getIntegerValue(userInput);
            return new OrderDay(orderDate);
        } catch (IllegalArgumentException e) {
            System.out.println(ExceptionMessage.INVALID_DAY.getMessage());
            return readDate();
        }
    }

    public Orders readOrders() {
        try {
            String[] menuAndQuantities = this.getOrdersInput();
            List<Order> orderList = Arrays.stream(menuAndQuantities)
                    .map(String::trim)
                    .map(this::readOrder)
                    .toList();
            return new Orders(orderList);
        } catch (IllegalArgumentException e) {
            System.out.println(ExceptionMessage.INVALID_ORDER.getMessage());
            return readOrders();
        }
    }

    public String[] getOrdersInput() {
        System.out.println(INPUT_MENU_NAME_AND_QUANTITY);
        String userInput = Console.readLine();
        String[] ordersInput = userInput.split(ORDER_SPLIT_DELIMITER);
        if (ordersInput.length < MINIMUM_AMOUNT_OF_ORDER) {
            throw new IllegalArgumentException();
        }
        return ordersInput;
    }

    public Order readOrder(String userInput) {
        String[] menuNameAndQuantity = userInput.split(NAME_QUANTITY_SPLIT_DELIMITER);
        if (menuNameAndQuantity.length != STANDARD_LENGTH_OF_ORDER) {
            throw new IllegalArgumentException();
        }
        String menuName = menuNameAndQuantity[0].replaceAll(SPACE_REGULAR_EXPRESSION, EMPTY_LINE);
        String menuQuantity = menuNameAndQuantity[1];
        int quantity = getIntegerValue(menuQuantity);
        Menu menu = MenuFactory.getMenu(menuName);
        return new Order(menu, quantity);
    }

    private int getIntegerValue(String userInput) {
        try {
            String userInputWithNoSpace = userInput.replaceAll(SPACE_REGULAR_EXPRESSION, EMPTY_LINE);
            return Integer.parseInt(userInputWithNoSpace);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }
}