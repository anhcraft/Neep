package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.Mark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class NeepSection extends NeepElement implements NeepContainer<NeepComponent> {
    private Map<String, Integer> key2Index = new HashMap<>();
    private List<NeepComponent> components;

    public NeepSection(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment,
                       @NotNull List<NeepComponent> components) {
        super(parent, key, inlineComment);
        this.components = components;
        for(int i = 0; i < components.size(); i++){
            NeepComponent c = components.get(i);
            if(c.isElement()){
                key2Index.put(c.asElement().getKey(), i);
            }
        }
    }

    public int indexOf(@NotNull String key){
        return key2Index.getOrDefault(key, -1);
    }

    @NotNull
    public NeepComponent get(int index){
        return components.get(index);
    }

    @Nullable
    public NeepComponent get(@NotNull String key){
        return components.get(key2Index.get(key));
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

    public void appendFirst(@NotNull NeepComponent component){
        append(0, component);
    }

    public void appendLast(@NotNull NeepComponent component){
        append(size(), component);
    }

    @Nullable
    public NeepComponent remove(int index){
        NeepComponent nc = components.remove(index);
        if(nc.isElement()){
            key2Index.remove(nc.asElement().getKey());
        }
        return nc;
    }

    @Nullable
    public NeepComponent remove(String key){
        int i = indexOf(key);
        return i == -1 ? null : remove(i);
    }

    @NotNull
    public Set<String> getKeys() {
        return getKeys(false);
    }

    private void getKeys(String prefix, Set<String> keys, boolean deep) {
        for(NeepComponent component : this){
            if(component.isElement()) {
                keys.add(prefix + component.asElement().getKey());
            }
            if(deep && component.isSection()) {
                NeepSection section = component.asSection();
                section.getKeys(prefix + section.getKey() + ".", keys, true);
            }
        }
    }

    @NotNull
    public Set<String> getKeys(boolean deep) {
        Set<String> keys = new LinkedHashSet<>();
        getKeys(getKey().isEmpty() ? "" : getKey() + ".", keys, deep);
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
    }

    @Override
    public void add(@NotNull NeepComponent object) {
        appendLast(object);
    }

    @Override
    public void addAll(@NotNull Collection<NeepComponent> objects) {
        for (NeepComponent component : objects) {
            appendLast(component);
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
        return new Itr();
    }

    @Override
    public String toString() {
        return getKey() + Mark.SECTION_OPEN + size() + Mark.SECTION_CLOSE;
    }

    public class Itr implements Iterator<NeepComponent> {
        private int cursor = -1;

        @Override
        public boolean hasNext() {
            return cursor + 1 < size();
        }

        @Override
        public NeepComponent next() {
            return get(++cursor);
        }

        @Override
        public void remove() {
            if(cursor != -1) {
                NeepSection.this.remove(cursor);
            }
        }
    }
}
