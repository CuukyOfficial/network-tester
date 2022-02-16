package de.cuuky.networktester.tests;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Test {

    private static final String TEST_FAILED = " Passed %s/%s tests\n";
    private static final String FAILED_LIST = " Failed tests: \n%s";

    private final TestType type;
    private final List<TestResult> results;

    Test(TestType type) {
        this.type = type;
        this.results = new LinkedList<>();
    }

    private Collection<String> collectFailed() {
        return this.results.stream().filter(TestResult::hasFailed).map(TestResult::getMessage).collect(Collectors.toList());
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
        Collection<String> failed = this.collectFailed();
        String failedList = String.join("\n", failed);
        return String.format(TEST_FAILED, this.results.size() - failed.size(), results.size())
            + String.format(FAILED_LIST, failedList);
    }
}