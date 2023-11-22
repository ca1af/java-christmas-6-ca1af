package console;

import java.text.DecimalFormat;

public class Formatter {
    private static final String FORMAT_PATTERN = "#,###";

    private Formatter() {
        throw new UnsupportedOperationException();
    }

    public static String formatPrice(int amount) {
        DecimalFormat decimalFormat = new DecimalFormat(FORMAT_PATTERN);
        return decimalFormat.format(amount);
    }

}
