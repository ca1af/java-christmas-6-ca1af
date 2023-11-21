package gift;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BadgeTest {
    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -1000, -10000})
    @DisplayName("음수의 값을 판별할 수 없다.")
    void name(int price) {
        assertThatThrownBy(() -> Badge.getBadgeNameByPrice(price)).isInstanceOf(IllegalArgumentException.class);
    }


    @ParameterizedTest
    @ValueSource(ints = {0,4999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - NONE(없음)")
    void getBadgeByPrice_NONE(int price) {
        String none = Badge.getBadgeNameByPrice(price);
        assertThat(none).isEqualTo(Badge.NONE.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {5000, 9999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - STAR(별)")
    void getBadgeByPrice_STAR(int price) {
        String star = Badge.getBadgeNameByPrice(price);
        assertThat(star).isEqualTo(Badge.STAR.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {10001, 19999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - TREE(트리)")
    void getBadgeByPrice_TREE(int price) {
        String tree = Badge.getBadgeNameByPrice(price);
        assertThat(tree).isEqualTo(Badge.TREE.getName());
    }

    @ParameterizedTest
    @ValueSource(ints = {20000, 99999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - SANTA(산타)")
    void getBadgeByPrice_SANTA(int price) {
        String santa = Badge.getBadgeNameByPrice(price);
        assertThat(santa).isEqualTo(Badge.SANTA.getName());
    }
}