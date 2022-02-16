package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.IP;
import de.cuuky.networktester.application.Network;

import java.util.List;

public class DefaultInteractionTest extends Test {

    DefaultInteractionTest() {
        super(TestType.DEFAULT_INTERACTION);
    }

    // Unfinished cuz im lazy
    @Override
    public void test() {
        IP root = new IP("141.255.1.133");
        List<List<IP>> levels = List.of(List.of(root), List.of(new IP("0.146.197.108"), new IP("122.117.67.158")));
        final Network network = new Network(root, levels.get(1));

        this.verify("networkToString", network.toString(root), "(141.255.1.133 0.146.197.108 122.117.67.158)");
        this.verify("networkHeight", network.getHeight(root), (levels.size() - 1));
        this.verify("levelsCheck", network.getLevels(root), List.of(List.of(root), levels.get(1)));

        root = new IP("122.117.67.158");
        levels = List.of(List.of(root), List.of(new IP("141.255.1.133")),
            List.of(new IP("0.146.197.108")));

        this.verify("newRootCheck", network.toString(root), "(122.117.67.158 (141.255.1.133 0.146.197.108))");
        this.verify("newRootHeightCheck", network.getHeight(root), (levels.size() - 1));
        this.verify("levelEquality", network.getLevels(root), levels);
        this.verify("addNetwork1", network.add(new Network("(122.117.67.158 0.146.197.108)")), false);
        this.verify("addNetwork2", network.add(new Network("(85.193.148.81 34.49.145.239 231.189.0.127 141.255.1.133)")), true);
        this.verify("addNetwork3", network.add(new Network("(231.189.0.127 252.29.23.0"
            + " 116.132.83.77 39.20.222.120 77.135.84.171)")), true);
    }
}