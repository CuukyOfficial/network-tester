package de.cuuky.networktester.tests;

import java.util.function.Supplier;

public enum TestType {

    DEFAULT_INTERACTION("Default interaction", DefaultInteractionTest::new);

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