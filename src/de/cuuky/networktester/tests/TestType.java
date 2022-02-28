package de.cuuky.networktester.tests;

import java.util.function.Supplier;

/**
 * Register new tests here with their name and a method reference to
 * their empty constructor.
 */
public enum TestType {

    EXAMPLE_INTERACTION("Example Interaction", ExampleInteractionTest::new),
    IP_PARSE("IP Parser", IPParseTester::new),
    NETWORK_PARSE("Network Parser", NetworkParseTester::new),
    CIRCULAR_NETWORK("CircularNetwork", CircularNetworkTest::new);

    private final String name;
    private final Supplier<Test> creator;

    TestType(String name, Supplier<Test> creator) {
        this.name = name;
        this.creator = creator;
    }

    String getName() {
        return this.name;
    }

    public Test createInstance() {
        return this.creator.get();
    }
}