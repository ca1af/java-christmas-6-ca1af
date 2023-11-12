package menu;

import menu.category.Appetizer;
import menu.category.Beverage;
import menu.category.Dessert;
import menu.category.Main;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MenuFactoryTest {
    @Test
    @DisplayName("팩토리에서 에피타이저의 [이름] 으로 에피타이저 객체를 가져온다.")
    void getFromFactory_APPETIZER() {
        for (Appetizer appetizer : Appetizer.values()) {
            Menu menu = MenuFactory.getMenu(appetizer.getName());
            assertThat(menu).isInstanceOf(Appetizer.class);
        }
    }
    @Test
    @DisplayName("팩토리에서 메인 메뉴의 [이름] 으로 메인 객체를 가져온다.")
    void getFromFactory_MAIN() {
        for (Main main : Main.values()) {
            Menu menu = MenuFactory.getMenu(main.getName());
            assertThat(menu).isInstanceOf(Main.class);
        }
    }
    @Test
    @DisplayName("팩토리에서 디저트의 [이름] 으로 디저트 객체를 가져온다.")
    void getFromFactory_DESSERT() {
        for (Dessert dessert : Dessert.values()) {
            Menu menu = MenuFactory.getMenu(dessert.getName());
            assertThat(menu).isInstanceOf(Dessert.class);
        }
    }
    @Test
    @DisplayName("팩토리에서 음료의 [이름] 으로 음료 객체를 가져온다.")
    void getFromFactory_BEVERAGE() {
        for (Beverage beverage : Beverage.values()) {
            Menu menu = MenuFactory.getMenu(beverage.getName());
            assertThat(menu).isInstanceOf(Beverage.class);
        }
    }

    @Test
    @DisplayName("사용자가 잘못된 메뉴이름을 입력했을 시 예외가 발생한다.")
    void getFromFactory_INVALID() {
        assertThatThrownBy(() -> MenuFactory.getMenu("foo"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}