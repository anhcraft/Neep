package dev.anhcraft.neep.struct.container;

import dev.anhcraft.neep.Mark;
import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.NeepComponent;
import dev.anhcraft.neep.struct.NeepElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NeepSection extends NeepContainer<NeepComponent> {
    private final Map<String, Integer> key2Index = new HashMap<>();
    private final List<NeepComponent> components;
    private boolean needUpdateIndexes = true;

    private void checkIndexes() {
        if(needUpdateIndexes) {
            needUpdateIndexes = false;
            key2Index.clear();
            for (int i = 0; i < components.size(); i++) {
                NeepComponent c = components.get(i);
                if (c.isElement()) {
                    key2Index.put(c.asElement().getKey(), i);
                }
            }
        }
    }

    public NeepSection(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment,
                       @NotNull List<NeepComponent> components) {
        super(parent, key, inlineComment);
        this.components = components;
        checkIndexes();
    }

    public int indexOf(@NotNull String key){
        checkIndexes();
        return key2Index.getOrDefault(key, -1);
    }

    @NotNull
    public NeepComponent get(int index){
        return components.get(index);
    }

    @Nullable
    public NeepComponent get(@NotNull String key){
        int i = indexOf(key);
        return i == -1 ? null : components.get(i);
    }

    @NotNull
    public NeepComponent get(@NotNull String key, @NotNull NeepComponent def){
        NeepComponent nc = get(key);
        return nc == null ? def : nc;
    }

    @Nullable
    public NeepComponent set(int index, @NotNull NeepComponent component) {
        if(component.isElement()) {
            key2Index.put(component.asElement().getKey(), index);
        }
        return components.set(index, component);
    }

    public void append(int index, @NotNull NeepComponent component){
        if(component.isElement()) {
            key2Index.put(component.asElement().getKey(), index);
        }
        components.add(index, component);
    }

    public void prepend(@NotNull NeepComponent component){
        append(0, component);
    }

    public void append(@NotNull NeepComponent component){
        append(size(), component);
    }

    @Nullable
    public NeepComponent remove(int index){
        NeepComponent nc = components.remove(index);
        if(nc.isElement()){
            key2Index.remove(nc.asElement().getKey());
        }
        needUpdateIndexes = true;
        return nc;
    }

    @Nullable
    public NeepComponent remove(String key){
        int i = indexOf(key);
        return i == -1 ? null : remove(i);
    }

    @NotNull
    public Set<String> getPaths() {
        return getPaths(false);
    }

    @NotNull
    public Set<String> getPaths(boolean deep) {
        Set<String> paths = new LinkedHashSet<>();
        getPaths(getKey().isEmpty() ? "" : getKey() + ".", paths, deep);
        return paths;
    }

    private void getPaths(String prefix, Set<String> keys, boolean deep) {
        for(NeepComponent component : this){
            if(component.isElement()) {
                keys.add(prefix + component.asElement().getKey());
            }
            if(deep && component.isSection()) {
                NeepSection section = component.asSection();
                section.getPaths(prefix + section.getKey() + ".", keys, true);
            }
        }
    }

    @NotNull
    public Set<String> getKeys() {
        return getKeys(false);
    }

    @NotNull
    public Set<String> getKeys(boolean deep) {
        Set<String> keys = new LinkedHashSet<>();
        getPaths("", keys, deep);
        return keys;
    }

    @Override
    public boolean contains(@NotNull NeepComponent object) {
        return components.contains(object);
    }

    @Override
    public int size() {
        return components.size();
    }

    @Override
    public void remove(@NotNull NeepComponent object) {
        components.remove(object);
        if(object instanceof NeepElement) {
            key2Index.remove(object.asElement().getKey());
        }
        needUpdateIndexes = true;
    }

    @Override
    public void add(@NotNull NeepComponent object) {
        append(object);
    }

    @Override
    public void addAll(@NotNull Collection<NeepComponent> objects) {
        for (NeepComponent component : objects) {
            append(component);
        }
    }

    @Override
    public Collection<NeepComponent> getAll() {
        return new ArrayList<>(components);
    }

    @Override
    public void clear() {
        components.clear();
        key2Index.clear();
    }

    @NotNull
    @Override
    public Iterator<NeepComponent> iterator() {
        return new Itr(this);
    }

    @Override
    public String toString() {
        return getKey() + Mark.SECTION_OPEN + size() + Mark.SECTION_CLOSE;
    }

    private static class Itr implements Iterator<NeepComponent> {
        private int cursor = -1;
        private final NeepSection section;

        public Itr(NeepSection section) {
            this.section = section;
        }

        @Override
        public boolean hasNext() {
            return cursor + 1 < section.size();
        }

        @Override
        public NeepComponent next() {
            return section.get(++cursor);
        }

        @Override
        public void remove() {
            if(cursor != -1) {
                section.remove(cursor);
            }
        }
    }
}
