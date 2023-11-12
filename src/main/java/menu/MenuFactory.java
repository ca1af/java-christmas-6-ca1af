package menu;

import menu.category.Appetizer;
import menu.category.Beverage;
import menu.category.Dessert;
import menu.category.Main;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuFactory {
    private static final Map<String, Menu> menuNameMap = new LinkedHashMap<>();

    static {
        for (Appetizer appetizer : Appetizer.values()) {
            menuNameMap.put(appetizer.getName(), appetizer);
        }
        for (Beverage beverage : Beverage.values()) {
            menuNameMap.put(beverage.getName(), beverage);
        }
        for (Dessert dessert : Dessert.values()) {
            menuNameMap.put(dessert.getName(), dessert);
        }
        for (Main main : Main.values()) {
            menuNameMap.put(main.getName(), main);
        }
    }

    private MenuFactory() {
        throw new UnsupportedOperationException();
    }

    public static Menu getMenu(String menuName){
        validateName(menuName);
        return menuNameMap.get(menuName);
    }

    private static void validateName(String menuName) {
        if (!menuNameMap.containsKey(menuName)) {
            throw new IllegalArgumentException();
        }
    }
}