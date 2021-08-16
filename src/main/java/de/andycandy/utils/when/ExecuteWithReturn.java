package de.andycandy.utils.when;

@FunctionalInterface
public interface ExecuteWithReturn<R> {

    R execute();
}
