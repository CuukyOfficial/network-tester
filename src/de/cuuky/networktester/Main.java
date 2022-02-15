package de.cuuky.networktester;

import de.cuuky.networktester.classloader.CustomJarLoader;

import java.io.IOException;

public class Main {

    private static final String STARTUP = "NetworkTester v0.1 by Cuuky";

    private static CustomJarLoader loadedJar;

    public static void main(String[] args) {
        System.out.println(STARTUP);
        String location = args.length >= 1 ? args[0] : "./test.jar";
        try {
            loadedJar = new CustomJarLoader(location);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Could not load test jar '" + location + "'!");
            System.exit(0);
        }

        new NetworkTester();
    }

    public static Class<?> findClass(String name) {
        return loadedJar.findClass(name);
    }
}