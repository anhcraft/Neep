package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface NeepContainer<T> extends Iterable<T> {
    boolean contains(@NotNull T object);
    int size();
    void remove(@NotNull T object);
    void add(@NotNull T object);
    void addAll(@NotNull Collection<T> objects);
    Collection<T> getAll();
    default void addAll(@NotNull NeepContainer<T> container) {
        addAll(container.getAll());
    }
    void clear();
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
