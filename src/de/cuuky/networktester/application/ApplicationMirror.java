package de.cuuky.networktester.application;

import de.cuuky.networktester.Main;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ApplicationMirror {

    private static final Map<Class<?>, Class<?>> mirrorCache = new HashMap<>();

    private Class<?> mirrorClass;
    private Object mirror;

    public ApplicationMirror(Object... parameters) {
        this.mirrorClass = this.findClass();
        this.mirror = this.createOriginal(parameters);
        mirrorCache.put(this.getClass(), this.mirrorClass);
    }

    private String getClassName() {
        return this.getClass().getSimpleName();
    }

    private Class<?> mapParameterType(Class<?> toMap) {
        // kind of a cheat but who cares
        return List.class.isAssignableFrom(toMap) ? List.class : mirrorCache.getOrDefault(toMap, toMap);
    }

    private Object mapParameter(Object parameter) {
        return parameter instanceof ApplicationMirror ? ((ApplicationMirror) parameter).getMirror() : parameter;
    }

    private Class<?>[] mapParameterTypes(Object... parameterTypes) {
        return Arrays.stream(parameterTypes).map(Object::getClass).map(this::mapParameterType).toArray(Class[]::new);
    }

    private Object[] mapParameters(Object... parameters) {
        return Arrays.stream(parameters).map(this::mapParameter).toArray(Object[]::new);
    }

    private Class<?> findClass() {
        return Main.findClass(this.getClassName());
    }

    private Object createOriginal(Object... parameters) {
        Class<?>[] mapParameterTypes = this.mapParameterTypes(parameters);
        Object[] mappedParameters = this.mapParameters(parameters);
        try {
             return this.mirrorClass.getDeclaredConstructor(mapParameterTypes).newInstance(mappedParameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Object call(String name, Object... parameters) {
        Class<?>[] parameterClasses = this.mapParameterTypes(parameters);
        Object[] mappedParameters = this.mapParameters(parameters);
        try {
            return this.mirrorClass.getDeclaredMethod(name, parameterClasses).invoke(this.mirror, mappedParameters);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Object getMirror() {
        return this.mirror;
    }
}