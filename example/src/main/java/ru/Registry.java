package ru;

public class Registry {
    public static Registry registry() {
        return new Registry();
    }

    public Registry registerService(Class<?> serviceClass) {
        return this;
    }

    public Registry registerMethod(Class<?> serviceClass, String method) {
        return this;
    }
}
