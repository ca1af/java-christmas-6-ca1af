package gift;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public enum Badge {
    NONE(0, "없음"),
    STAR(5000, "별"),
    TREE(10000, "트리"),
    SANTA(20000, "산타");
    private final int standardPrice;
    private final String name;
    private static final List<Badge> SORTED_BY_PRICE_DESCENDING = sortedByPriceDescending();
    Badge(int standardPrice, String name) {
        this.standardPrice = standardPrice;
        this.name = name;
    }

    private static List<Badge> sortedByPriceDescending() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(Badge::getStandardPrice).reversed())
                .toList();
    }

    public static Badge getBadgeByPrice(int benefitAmount) {
        for (Badge badge : SORTED_BY_PRICE_DESCENDING) {
            if (benefitAmount >= badge.getStandardPrice()) {
                return badge;
            }
        }
        throw new IllegalArgumentException(); // 입력된 benefitAmount 는 음수일 수 없다.
    }

    public int getStandardPrice() {
        return standardPrice;
    }

    public String getName() {
        return name;
    }
}