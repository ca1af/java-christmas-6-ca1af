package gift;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class BadgeTest {

    @ParameterizedTest
    @ValueSource(ints = {0,4999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - NONE(없음)")
    void getBadgeByPrice_NONE(int price) {
        Badge none = Badge.getBadgeByPrice(price);
        assertThat(none).isEqualTo(Badge.NONE);
    }

    @ParameterizedTest
    @ValueSource(ints = {5000, 9999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - STAR(별)")
    void getBadgeByPrice_STAR(int price) {
        Badge none = Badge.getBadgeByPrice(price);
        assertThat(none).isEqualTo(Badge.STAR);
    }

    @ParameterizedTest
    @ValueSource(ints = {10001, 19999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - TREE(트리)")
    void getBadgeByPrice_TREE(int price) {
        Badge none = Badge.getBadgeByPrice(price);
        assertThat(none).isEqualTo(Badge.TREE);
    }

    @ParameterizedTest
    @ValueSource(ints = {20000, 99999})
    @DisplayName("가격에 따라 뱃지를 차등지급함을 테스트 한다. - SANTA(산타)")
    void getBadgeByPrice_SANTA(int price) {
        Badge none = Badge.getBadgeByPrice(price);
        assertThat(none).isEqualTo(Badge.SANTA);
    }
}