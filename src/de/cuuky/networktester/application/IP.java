package de.cuuky.networktester.application;

public class IP extends ApplicationMirror implements Comparable<IP> {

    public IP(final String pointNotation) throws Exception {
        super(pointNotation);
    }

    @Override
    public String toString() {
        return (String) this.call("toString");
    }

    @Override
    public int compareTo(IP o) {
        return (int) this.call("compareTo", o);
    }
}