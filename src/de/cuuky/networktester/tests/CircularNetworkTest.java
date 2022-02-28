package de.cuuky.networktester.tests;

import de.cuuky.networktester.application.IP;
import de.cuuky.networktester.application.Network;

import java.util.List;

public class CircularNetworkTest extends Test {

    CircularNetworkTest() {
        super(TestType.CIRCULAR_NETWORK);
    }

    @Override
    public void test() {
        IP ip1 = new IP("0.0.0.0");
        IP ip2 = new IP("1.1.1.1");
        IP ip3 = new IP("2.2.2.2");
        Network network1 = new Network(ip1, List.of(ip2, ip3));
        this.verify("connect", network1.connect(ip2, ip3), false);

        Network network2 = new Network(ip2, List.of(ip3));
        this.verify("addSubnet", network1.add(network2), false);

        Network network3 = new Network(ip1, List.of(ip2, ip3));
        this.verify("addSubnetLegalArgs", network1.add(network3), false);

        IP ip4 = new IP("3.3.3.3");
        Network network4 = new Network(ip3, List.of(ip4));
        this.verify("ip4", network1.add(network4), true);
    }
}
