package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.IP;
import de.cuuky.networktester.application.MirrorExecutableException;

public class IPParseTester extends Test {

    IPParseTester() {
        super(TestType.IP_PARSE);
    }

    private void testIP(String ip, boolean expected) {
        try {
            new IP(ip);
            this.addResult(ip, expected);
        } catch (MirrorExecutableException e) {
            this.addResult(ip, !expected);
        }
    }

    @Override
    public void test() {
        this.testIP("abc", false); // lol
        this.testIP("a.b.c.d", false);
        this.testIP("01.10.0.1", false);
        this.testIP("00001.0.0.0", false);
        this.testIP("1.001.0.0", false);
        this.testIP("1..1.0.0", false);
        this.testIP("1.0.0.0.", false);
        this.testIP("1111.11110.1111.1111", false);
        this.testIP("256.192.0.100", false);
        this.testIP("1111.1111.1111.1111", false);
        this.testIP("192.86.0.2", true);
        this.testIP("0.0.0.0", true);
        this.testIP("10.10.10.10", true);
        this.testIP("200.200.200.200", true);
        this.testIP("255.255.255.255", true);
    }
}