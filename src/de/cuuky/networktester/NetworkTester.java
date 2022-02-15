package de.cuuky.networktester;

import de.cuuky.networktester.tests.Test;
import de.cuuky.networktester.tests.TestType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class NetworkTester {

    private static final String TEST_START = "Applying test '%s' (%s/%s)...";
    private static final String TEST_EXCEPTION = "Exception caught: %s";
    private static final String TEST_DONE = "Test-Results: %s";

    private final Collection<Test> tests;

    public NetworkTester() {
        this.tests = Arrays.stream(TestType.values()).map(TestType::createInstance).collect(Collectors.toSet());
        this.doTests();
    }

    private void doTests() {
        int i = 1;
        for (Test test : this.tests) {
            System.out.printf((TEST_START) + "%n", test.toString(), i, this.tests.size());
            try {
                test.test();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.printf((TEST_EXCEPTION) + "%n", e.getClass().getName());
            }
            System.out.printf((TEST_DONE) + "%n", test.parseResult());
            i++;
        }
    }
}