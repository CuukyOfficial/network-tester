package de.cuuky.networktester.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class Test {

    private static final String TEST_FAILED = " Passed %s/%s tests\n";
    private static final String FAILED_LIST = " Test results: \n%s";

    private final TestType type;
    private final List<TestResult> results;

    Test(TestType type) {
        this.type = type;
        this.results = new LinkedList<>();
    }

    private long countPassed() {
        return this.results.stream().filter(TestResult::wasSuccessful).count();
    }

    protected void addResult(String name, boolean success) {
        this.results.add(new TestResult(name, success));
    }

    protected <T> void verify(String name, T test, T expected) {
        this.results.add(new TestResult(name, Objects.equals(test, expected), test, expected));
    }

    public abstract void test();

    @Override
    public String toString() {
        return this.type.getName();
    }

    public String parseResult() {
        String[] printResults = this.results.stream().map(TestResult::getMessage).toArray(String[]::new);
        String failedList = String.join("\n", printResults);
        return String.format(TEST_FAILED, this.countPassed(), this.results.size())
            + String.format(FAILED_LIST, failedList);
    }
}