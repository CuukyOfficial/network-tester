package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.IP;
import de.cuuky.networktester.application.Network;

import java.util.List;

public class ExampleInteractionTest extends Test {

    ExampleInteractionTest() {
        super(TestType.EXAMPLE_INTERACTION);
    }

    @Override
    public void test() {
        IP root = new IP("141.255.1.133");
        List<List<IP>> levels = List.of(List.of(root), List.of(new IP("0.146.197.108"), new IP("122.117.67.158")));
        final Network network = new Network(root, levels.get(1));

        this.verify("network1", network.toString(root), "(141.255.1.133 0.146.197.108 122.117.67.158)");
        this.verify("height1", network.getHeight(root), (levels.size() - 1));
        this.verify("levels1", network.getLevels(root), List.of(List.of(root), levels.get(1)));

        root = new IP("122.117.67.158");
        levels = List.of(List.of(root), List.of(new IP("141.255.1.133")),
            List.of(new IP("0.146.197.108")));

        this.verify("network2", network.toString(root), "(122.117.67.158 (141.255.1.133 0.146.197.108))");
        this.verify("height2", network.getHeight(root), (levels.size() - 1));
        this.verify("levels2", network.getLevels(root), levels);
        this.verify("addNetwork1", network.add(new Network("(122.117.67.158 0.146.197.108)")), false);
        this.verify("addNetwork2",
            network.add(new Network("(85.193.148.81 34.49.145.239 231.189.0.127 141.255.1.133)")), true);
        this.verify("addNetwork3", network.add(new Network("(231.189.0.127 252.29.23.0"
            + " 116.132.83.77 39.20.222.120 77.135.84.171)")), true);

        root = new IP("85.193.148.81");
        levels = List.of(List.of(root),
            List.of(new IP("34.49.145.239"), new IP("141.255.1.133"),
                new IP("231.189.0.127")),
            List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
                new IP("77.135.84.171"), new IP("116.132.83.77"),
                new IP("122.117.67.158"), new IP("252.29.23.0")));

        this.verify("network3", network.toString(root),
            "(85.193.148.81 34.49.145.239 (141.255.1.133 0.146.197.108"
                + " 122.117.67.158) (231.189.0.127 39.20.222.120"
                + " 77.135.84.171 116.132.83.77 252.29.23.0))");

        this.verify("height3", network.getHeight(root), levels.size() - 1);
        this.verify("levels3", network.getLevels(root), levels);
        this.verify("route1", network.getRoute(new IP("141.255.1.133"),
                new IP("231.189.0.127")),
            List.of(new IP("141.255.1.133"), new IP("85.193.148.81"),
                new IP("231.189.0.127")));

        root = new IP("34.49.145.239");
        levels = List.of(List.of(root), List.of(new IP("85.193.148.81")),
            List.of(new IP("141.255.1.133"), new IP("231.189.0.127")),
            List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
                new IP("77.135.84.171"), new IP("116.132.83.77"),
                new IP("122.117.67.158"), new IP("252.29.23.0")));

        this.verify("height4", network.getHeight(root), levels.size() - 1);
        this.verify("disconnect1", network.disconnect(new IP("85.193.148.81"),
            new IP("34.49.145.239")), true);

        this.verify("networkList1", network.list(),
            List.of(new IP("0.146.197.108"), new IP("39.20.222.120"),
            new IP("77.135.84.171"), new IP("85.193.148.81"),
            new IP("116.132.83.77"), new IP("122.117.67.158"),
            new IP("141.255.1.133"), new IP("231.189.0.127"),
            new IP("252.29.23.0")));
    }
}