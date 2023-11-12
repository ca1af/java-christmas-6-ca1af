package menu;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuFactoryTest {
    @Test
    void getFromFactory_APPETIZER() {
        for (Appetizer appetizer : Appetizer.values()) {
            System.out.println(appetizer.getName());
            Menu menu = MenuFactory.getMenu(appetizer.getName());
            assertThat(menu).isInstanceOf(Appetizer.class);
        }
    }
    @Test
    void getFromFactory_MAIN() {
        for (Main main : Main.values()) {
            System.out.println(main.getName());
            Menu menu = MenuFactory.getMenu(main.getName());
            assertThat(menu).isInstanceOf(Main.class);
        }
    }
    @Test
    void getFromFactory_DESSERT() {
        for (Dessert dessert : Dessert.values()) {
            System.out.println(dessert.getName());
            Menu menu = MenuFactory.getMenu(dessert.getName());
            assertThat(menu).isInstanceOf(Dessert.class);
        }
    }
    @Test
    void getFromFactory_BEVERAGE() {
        for (Beverage beverage : Beverage.values()) {
            System.out.println(beverage.getName());
            Menu menu = MenuFactory.getMenu(beverage.getName());
            assertThat(menu).isInstanceOf(Beverage.class);
        }
    }
}