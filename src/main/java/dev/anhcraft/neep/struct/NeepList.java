package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.Mark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class NeepList<T> extends NeepContainer<T> {
    private List<T> backend;

    public NeepList(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment, @NotNull List<T> backend) {
        super(parent, key, inlineComment);
        this.backend = backend;
    }

    @Nullable
    public T set(int index, @NotNull T object){
        return backend.set(index, object);
    }

    public void append(int index, @NotNull T object){
        backend.add(index, object);
    }

    public void appendFirst(@NotNull T object){
        backend.add(0, object);
    }

    public void appendLast(@NotNull T object){
        backend.add(object);
    }

    @Nullable
    public T remove(int index){
        return backend.remove(index);
    }

    @NotNull
    public T get(int index){
        return backend.get(index);
    }

    @Override
    public boolean contains(@NotNull T object) {
        return backend.contains(object);
    }

    @Override
    public int size() {
        return backend.size();
    }

    @Override
    public void remove(@NotNull T object) {
        backend.remove(object);
    }

    @Override
    public void add(@NotNull T object) {
        backend.add(object);
    }

    @Override
    public void addAll(@NotNull Collection<T> objects) {
        backend.addAll(objects);
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<>(backend);
    }

    @Override
    public void clear() {
        backend.clear();
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return backend.iterator();
    }

    @Override
    public String toString() {
        return getKey() + Mark.LIST_OPEN + size() + Mark.LIST_CLOSE;
    }
}
