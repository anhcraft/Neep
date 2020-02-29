package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface NeepContainer<T> extends Iterable<T> {
    boolean contains(@NotNull T object);
    int size();
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
