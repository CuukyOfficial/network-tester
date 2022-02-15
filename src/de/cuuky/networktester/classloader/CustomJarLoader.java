package de.cuuky.networktester.classloader;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomJarLoader {

    private final ClassLoader classLoader;
    private final Collection<Class<?>> loaded;

    public CustomJarLoader(String pathToJar) throws IOException, ClassNotFoundException {
        this.classLoader = new CustomClassLoader(new URL("jar:file:" + pathToJar+"!/"));
        this.loaded = new HashSet<>();
        this.loadClasses(new JarFile(pathToJar));
    }

    // joinked from https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
    private void loadClasses(JarFile jarFile) throws ClassNotFoundException {
        Enumeration<JarEntry> e = jarFile.entries();
        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            // -6 because of .class
            String className = je.getName().substring(0,je.getName().length()-6);
            className = className.replace('/', '.');
            this.loaded.add(this.classLoader.loadClass(className));
        }
    }

    public Class<?> findClass(String name) {
        return this.loaded.stream().filter(c -> c.getSimpleName().equals(name))
            .findFirst().orElse(null);
    }
}