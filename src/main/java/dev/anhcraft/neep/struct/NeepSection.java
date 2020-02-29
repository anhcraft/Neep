package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.utils.Mark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
    public NeepComponent set(int index, @NotNull NeepComponent component){
        return components.set(index, component);
    }

    public void append(int index, @NotNull NeepComponent component){
        components.add(index, component);
    }

    public void appendFirst(@NotNull NeepComponent component){
        components.add(0, component);
    }

    public void appendLast(@NotNull NeepComponent component){
        components.add(component);
    }

    @Nullable
    public NeepComponent remove(int index){
        NeepComponent nc = components.remove(index);
        if(nc != null){
            key2Index.remove(nc.asElement().getKey());
        }
        return nc;
    }

    @Nullable
    public NeepComponent remove(String key){
        int i = indexOf(key);
        return i == -1 ? null : remove(i);
    }

    @Override
    public boolean contains(@NotNull NeepComponent object) {
        return components.contains(object);
    }

    @Override
    public int size() {
        return components.size();
    }

    @NotNull
    @Override
    public Iterator<NeepComponent> iterator() {
        return components.iterator();
    }

    @Override
    public String toString() {
        return getKey() + Mark.SECTION_OPEN + size() + Mark.SECTION_CLOSE;
    }
}
