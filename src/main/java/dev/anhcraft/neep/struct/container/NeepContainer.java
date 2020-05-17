package dev.anhcraft.neep.struct.container;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.NeepElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class NeepContainer<T> extends NeepElement implements Iterable<T> {
    public NeepContainer(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment) {
        super(parent, key, inlineComment);
    }

    public abstract int size();
    public abstract boolean contains(@NotNull T object);
    public abstract void remove(@NotNull T object);
    public abstract void clear();
    public abstract void add(@NotNull T object);
    public abstract void addAll(@NotNull Collection<T> objects);
    public abstract Collection<T> getAll();

    public void addAll(@NotNull NeepContainer<T> container) {
        addAll(container.getAll());
    }

    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
