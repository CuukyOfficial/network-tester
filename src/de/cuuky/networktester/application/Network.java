package de.cuuky.networktester.application;

import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public class Network extends ApplicationMirror {

    public Network(final IP root, final List<IP> children) {
        super(root, children);
    }

    public Network(final String bracketNotation) {
        super(bracketNotation);
    }

    public boolean add(final Network subnet) {
        return (boolean) this.call("add", subnet);
    }

    public List<IP> list() {
        return (List<IP>) this.call("list");
    }

    public boolean connect(final IP ip1, final IP ip2) {
        return (boolean) this.call("connect", ip1, ip2);
    }

    public boolean disconnect(final IP ip1, final IP ip2) {
        return (boolean) this.call("disconnect", ip1, ip2);
    }

    public boolean contains(final IP ip) {
        return (boolean) this.call("contains", ip);
    }

    public int getHeight(final IP root) {
        return (int) this.call("getHeight", root);
    }

    public List<List<IP>> getLevels(final IP root) {
        return (List<List<IP>>) this.call("getLevels", root);
    }

    public List<IP> getRoute(final IP start, final IP end) {
        return (List<IP>) this.call("getRoute", start, end);
    }

    public String toString(IP root) {
        return (String) this.call("toString", root);
    }
}