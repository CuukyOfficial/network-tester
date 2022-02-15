package de.cuuky.networktester.tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Test {

    private static final String TEST_FAILED = "Tests failed (%s/%s): %s";

    private final TestType type;
    private final Map<String, Boolean> results;

    Test(TestType type) {
        this.type = type;
        this.results = new HashMap<>();
    }

    protected void addResult(String name, boolean success) {
        this.results.put(name, success);
    }

    protected <T> void verify(String name, T test1, T test2) {
        this.addResult(name, Objects.equals(test1, test2));
    }

    public abstract void test() throws Exception;

    @Override
    public String toString() {
        return this.type.getName();
    }

    public String parseResult() {
        Collection<String> failed = results.keySet().stream().filter(k -> !this.results.get(k)).collect(Collectors.toSet());
        String failedList = String.join(", ", failed);
        return String.format(TEST_FAILED, failed.size(), results.size(), failedList);
    }
}