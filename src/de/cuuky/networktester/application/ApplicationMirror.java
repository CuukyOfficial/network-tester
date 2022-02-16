package de.cuuky.networktester.application;

import de.cuuky.networktester.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class ApplicationMirror {

    private static final Collection<Class<?>> METHOD_MAPPINGS = List.of(List.class);
    private static final Map<Class<?>, Class<?>> mirrorCache = new HashMap<>();

    private final Class<?> mirrorClass;
    private final Object mirror;
    private final Map<String, Method> methodCache = new HashMap<>();

    public ApplicationMirror(Object... parameters) {
        this.mirrorClass = this.findClass();
        this.mirror = this.createOriginal(parameters);
        mirrorCache.put(this.getClass(), this.mirrorClass);
    }

    private String getClassName() {
        return this.getClass().getSimpleName();
    }

    private Class<?> checkMethodMapping(Class<?> check) {
        return METHOD_MAPPINGS.stream().filter(m -> m.isAssignableFrom(check)).findFirst().orElse(check);
    }

    private Class<?> mapParameterType(Class<?> toMap) {
        return mirrorCache.getOrDefault(toMap, this.checkMethodMapping(toMap));
    }

    private Object mapParameter(Object parameter) {
        return parameter instanceof ApplicationMirror ? ((ApplicationMirror) parameter).mirror : parameter;
    }

    private Class<?>[] mapParameterTypes(Object... parameterTypes) {
        return Arrays.stream(parameterTypes).map(Object::getClass).map(this::mapParameterType).toArray(Class[]::new);
    }

    private Object[] mapParameters(Object... parameters) {
        return Arrays.stream(parameters).map(this::mapParameter).toArray(Object[]::new);
    }

    private Method getMethod(String name, Class<?>... parameterClasses) throws NoSuchMethodException {
        if (this.methodCache.containsKey(name))
            return this.methodCache.get(name);
        else {
            Method method = this.mirrorClass.getDeclaredMethod(name, parameterClasses);
            this.methodCache.put(name, method);
            return method;
        }
    }

    private void handleExecutableException(InvocationTargetException e) {
        if (e.getCause().getClass().getSimpleName().equals(ParseException.class.getSimpleName()))
            throw new ParseException(e.getCause().getMessage());
        else throw new MirrorExecutableException(e);
    }

    private Class<?> findClass() {
        return Main.findClass(this.getClassName());
    }

    private Object createOriginal(Object... parameters) {
        Class<?>[] mapParameterTypes = this.mapParameterTypes(parameters);
        Object[] mappedParameters = this.mapParameters(parameters);
        try {
            return this.mirrorClass.getDeclaredConstructor(mapParameterTypes).newInstance(mappedParameters);
        } catch (InvocationTargetException e) {
            this.handleExecutableException(e);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Object call(String name, Object... parameters) {
        Class<?>[] parameterClasses = this.mapParameterTypes(parameters);
        Object[] mappedParameters = this.mapParameters(parameters);
        try {
            return this.getMethod(name, parameterClasses).invoke(this.mirror, mappedParameters);
        } catch (InvocationTargetException e) {
            this.handleExecutableException(e);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}