package de.andycandy.utils.when;

@FunctionalInterface
public interface ConditionWithArg<T> {

    boolean test(T t);
}
