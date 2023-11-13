package console;

import camp.nextstep.edu.missionutils.Console;
import date.OrderDay;
import exeptions.ExceptionMessage;
import menu.Menu;
import menu.MenuFactory;
import order.Order;

public class InputView {
    private static final String INPUT_ESTIMATED_DATE_OF_ARRIVAL = "12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    private static final String INPUT_MENU_NAME_AND_QUANTITY = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";
    private static final String SPACE = "\\s";
    private static final String EMPTY_LINE = "";
    private static final String SPLIT_DELIMITER = "-";

    public OrderDay readDate(){
        System.out.println(INPUT_ESTIMATED_DATE_OF_ARRIVAL);
        String userInput = Console.readLine();
        try {
            int orderDate = getIntegerValue(userInput);
            return new OrderDay(orderDate);
        } catch (IllegalArgumentException e){
            System.out.println(ExceptionMessage.INVALID_DAY.getMessage());
            return readDate();
        }
    }

    public Order readOrder(){
        System.out.println(INPUT_MENU_NAME_AND_QUANTITY);
        String userInput = Console.readLine();
        String[] menuNameAndQuantity = userInput.split(SPLIT_DELIMITER);
        return getOrderByUserInput(menuNameAndQuantity);
    }

    private int getIntegerValue(String userInput) {
        try {
            return Integer.parseInt(userInput);
        } catch (NumberFormatException e){
            throw new IllegalArgumentException();
        }
    }

    private Order getOrderByUserInput(String[] menuNameAndQuantity) {
        try {
            String menuName = menuNameAndQuantity[0].replaceAll(SPACE, EMPTY_LINE);
            String menuQuantity = menuNameAndQuantity[1];
            int quantity = getIntegerValue(menuQuantity);
            Menu menu = MenuFactory.getMenu(menuName);
            return new Order(menu, quantity);
        } catch (IllegalArgumentException e){
            System.out.println(ExceptionMessage.INVALID_ORDER.getMessage());
            return readOrder();
        }
    }
}