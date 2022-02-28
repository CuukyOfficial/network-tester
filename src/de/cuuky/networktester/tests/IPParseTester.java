package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.IP;
import de.cuuky.networktester.application.ParseException;

/**
 * Checks if your IP class accepts valid and
 * rejects invalid IP-Addresses.
 */
public class IPParseTester extends Test {

    IPParseTester() {
        super(TestType.IP_PARSE);
    }

    private void testIP(String ip, boolean expected) {
        try {
            new IP(ip);
            this.addResult(ip, expected);
        } catch (ParseException e) {
            this.addResult(ip, !expected);
        }
    }

    @Override
    public void test() {
        this.testIP("...", false);
        this.testIP("0a0.0.0", false);
        this.testIP("abc", false);
        this.testIP("a.b.c.d", false);
        this.testIP("01.10.0.1", false);
        this.testIP("0001.0.0.0", false);
        this.testIP("1.001.0.0", false);
        this.testIP("1..1.0.0", false);
        this.testIP("1.0.0.0.", false);
        this.testIP(".1.0.0.0", false);
        this.testIP("111.1110.111.111", false);
        this.testIP("256.192.0.100", false);
        this.testIP("111.111.1111.111", false);
        this.testIP(" 192.86.0.2", false);
        this.testIP("192.86.0.2 ", false);
        this.testIP("192.86.0.2", true);
        this.testIP("0.0.0.0", true);
        this.testIP("10.10.10.10", true);
        this.testIP("200.200.200.200", true);
        this.testIP("255.255.255.255", true);
    }
}