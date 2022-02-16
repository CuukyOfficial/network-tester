package de.cuuky.networktester.application;

@SuppressWarnings({"unused"})
public class IP extends ApplicationMirror implements Comparable<IP> {

    public IP(final String pointNotation) {
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