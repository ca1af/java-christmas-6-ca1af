package benefit;

public class DiscountConstant {
    public static final int STAR_DAY_DISCOUNT = 1_000;
    public static final int FREE_GIFT_STANDARD = 120_000;
    public static final int DAY_OF_WEEK_DISCOUNT = 2_023;
    public static final int NO_DISCOUNT = 0;
    public static final int MINIMUM_ORDER_AMOUNT_FOR_DISCOUNT = 10000;
    public static final int D_DAY_DISCOUNT_START_PRICE = 1000;
    public static final int D_DAY_DISCOUNT_AMOUNT_PER_DAY = 100;

    private DiscountConstant() {
        throw new UnsupportedOperationException();
    }
}
