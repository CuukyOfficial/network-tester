package de.cuuky.networktester.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class CustomClassLoader extends URLClassLoader {

    public CustomClassLoader(URL url) {
        super(new URL[] {url});
    }
}