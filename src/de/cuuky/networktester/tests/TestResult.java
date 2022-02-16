package de.cuuky.networktester.tests;

class TestResult {

    protected static final String TEST_FAILED = "  Failed '%s'";
    protected static final String TEST_FAILED_DATA = TEST_FAILED + ": Given '%s', expected '%s'";

    protected final String name;
    protected final Object given;
    protected final Object expected;
    protected final boolean result;

    TestResult(String name, boolean result, Object given, Object expected) {
        this.name = name;
        this.result = result;
        this.given = given;
        this.expected = expected;
    }

    TestResult(String name, boolean result) {
        this(name, result, null, null);
    }

    private String saveToString(Object o) {
        return o == null ? "null" : o.toString();
    }

    boolean hasFailed() {
        return !this.result;
    }

    String getMessage() {
        String format = this.given == null && this.expected == null ? TEST_FAILED : TEST_FAILED_DATA;
        return String.format(format, this.name, this.saveToString(this.given), this.saveToString(this.expected));
    }
}