package homework.tastus;

/**
 * Class with static assert-methods
 */
public class TastusAssert {
    private final static String VALUE_DIFFERS_TEMPLATE = "Actual value differs from expected. Expected: %s Actual: %s";

    private TastusAssert() {
    }

    public static void assertEquals(Object expected, Object actual) {
        if (expected == null && actual == null)
            return;

        if (expected == null || !expected.equals(actual))
            throw new RuntimeException(String.format(VALUE_DIFFERS_TEMPLATE, expected, actual));
    }
}
