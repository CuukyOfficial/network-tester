package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.Network;
import de.cuuky.networktester.application.ParseException;

public class NetworkParseTester extends Test {

    NetworkParseTester() {
        super(TestType.NETWORK_PARSE);
    }

    private void testNetwork(String bracketNotation, boolean expected) {
        try {
            new Network(bracketNotation);
            this.addResult(bracketNotation, expected);
        } catch (ParseException e) {
            this.addResult(bracketNotation, !expected);
        }
    }

    @Override
    public void test() {
        this.testNetwork("(0.0.0.0)", false);
        this.testNetwork("(0.0.01. 187.187.187.187)", false);
        this.testNetwork("(0.0.010 187.187.187.187)", false);
        this.testNetwork("(0.0.0.0 (172.0.0.1 1.1.1.1))", true);
    }
}