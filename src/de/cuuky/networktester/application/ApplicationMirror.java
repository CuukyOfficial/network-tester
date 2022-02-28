package de.cuuky.networktester.application;

import de.cuuky.networktester.Main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ApplicationMirror {

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

    private Method getMethod(String name, Class<?>... parameterClasses) throws NoSuchMethodException {
        if (this.methodCache.containsKey(name))
            return this.methodCache.get(name);
        else {
            Method method = this.mirrorClass.getDeclaredMethod(name, parameterClasses);
            this.methodCache.put(name, method);
            return method;
        }
    }

    private void handleExecutableException(Throwable t, Class<?> check) {
        if (check.getSimpleName().equals(ParseException.class.getSimpleName()))
            throw new ParseException(t.getMessage());
        else if (check.getSuperclass() != null) {
            this.handleExecutableException(t, check.getSuperclass());
        } else throw new MirrorExecutableException(t);
    }

    private Class<?> findClass() {
        return Main.findClass(this.getClassName());
    }

    private Object createOriginal(Object... parameters) {
        Class<?>[] mapParameterTypes = mapParameterTypes(parameters);
        Object[] mappedParameters = mapParameters(parameters);
        try {
            return this.mirrorClass.getDeclaredConstructor(mapParameterTypes).newInstance(mappedParameters);
        } catch (InvocationTargetException e) {
            this.handleExecutableException(e.getCause(), e.getCause().getClass());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    Object call(String name, Object... parameters) {
        Class<?>[] parameterClasses = mapParameterTypes(parameters);
        Object[] mappedParameters = mapParameters(parameters);
        try {
            return this.getMethod(name, parameterClasses).invoke(this.mirror, mappedParameters);
        } catch (InvocationTargetException e) {
            this.handleExecutableException(e.getCause(), e.getCause().getClass());
        } catch (IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        return (boolean) this.call("equals", o);
    }

    @Override
    public int hashCode() {
        return (int) this.call("hashCode");
    }

    @Override
    public String toString() {
        return (String) this.call("toString");
    }

    private static Class<?> checkMethodMapping(Class<?> check) {
        return METHOD_MAPPINGS.stream().filter(m -> m.isAssignableFrom(check)).findFirst().orElse(check);
    }

    private static Class<?> mapParameterType(Class<?> toMap) {
        return mirrorCache.getOrDefault(toMap, checkMethodMapping(toMap));
    }

    private static Object mapParameter(Object parameter) {
        if (parameter instanceof List<?>)
            return ((List<?>) parameter).stream().map(ApplicationMirror::mapParameter).collect(Collectors.toList());

        return parameter instanceof ApplicationMirror ? ((ApplicationMirror) parameter).mirror : parameter;
    }

    private static Class<?>[] mapParameterTypes(Object... parameterTypes) {
        return Arrays.stream(parameterTypes).map(Object::getClass).map(ApplicationMirror::mapParameterType).toArray(Class[]::new);
    }

    public static Object[] mapParameters(Object... parameters) {
        return Arrays.stream(parameters).map(ApplicationMirror::mapParameter).toArray(Object[]::new);
    }
}