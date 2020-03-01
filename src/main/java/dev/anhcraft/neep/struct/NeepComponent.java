package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class NeepComponent {
    @NotNull
    public static NeepComponent create(@NotNull NeepContainer<?> container, @NotNull String key, @NotNull Object object) {
        if(object instanceof NeepComponent) {
            return (NeepComponent) object;
        } else if(object instanceof String) {
            return new NeepString(container, key, object.toString(), null);
        } else if(object instanceof Boolean) {
            return new NeepBoolean(container, key, object.toString(), null);
        } else if(object instanceof Double || object instanceof Float) {
            double val = ((Number) object).doubleValue();
            return new NeepDouble(container, key, String.valueOf(val), null);
        } else if(object instanceof Long) {
            long val = (Long) object;
            return new NeepLong(container, key, String.valueOf(val), null);
        } else if(object instanceof Number) {
            int val = ((Number) object).intValue();
            return new NeepInt(container, key, String.valueOf(val), null);
        } else if(object instanceof Collection) {
            NeepList<NeepComponent> list = new NeepList<>(container, key, null, new ArrayList<>());
            int i = 0;
            for(Object o : (Collection<?>) object){
                list.add(create(list, String.valueOf(i++), o));
            }
            return list;
        } else if(object instanceof Map) {
            NeepSection section = new NeepSection(container, key, null, new ArrayList<>());
            for(Map.Entry<?, ?> entry : ((Map<?, ?>) object).entrySet()){
                section.add(create(section, entry.getKey().toString(), entry.getValue()));
            }
            return section;
        } else if(object.getClass().isArray()) {
            NeepList<NeepComponent> list = new NeepList<>(container, key, null, new ArrayList<>());
            int i = 0;
            for(Object o : (Object[]) object){
                list.add(create(list, String.valueOf(i++), o));
            }
            return list;
        } else {
            throw new IllegalArgumentException("Cannot convert object to component");
        }
    }

    private NeepContainer<?> parent;

    public NeepComponent(@Nullable NeepContainer<?> parent) {
        this.parent = parent;
    }

    @Nullable
    public NeepContainer<?> getParent() {
        return parent;
    }

    public boolean isComment(){
        return this instanceof NeepComment;
    }

    public boolean isElement(){
        return this instanceof NeepElement;
    }

    public boolean isDynamic(){
        return this instanceof NeepDynamic<?>;
    }

    public boolean isPrimitive(){
        return this instanceof NeepPrimitive<?>;
    }

    public boolean isList(){
        return this instanceof NeepList<?>;
    }

    public boolean isSection(){
        return this instanceof NeepSection;
    }

    @NotNull
    public NeepComment asComment(){
        return (NeepComment) this;
    }

    @NotNull
    public NeepElement asElement(){
        return (NeepElement) this;
    }

    @NotNull
    public NeepDynamic<?> asDynamic(){
        return (NeepDynamic<?>) this;
    }

    @NotNull
    public NeepPrimitive<?> asPrimitive(){
        return (NeepPrimitive<?>) this;
    }

    @NotNull
    public NeepList<?> asList(){
        return (NeepList<?>) this;
    }

    @NotNull
    public NeepSection asSection(){
        return (NeepSection) this;
    }
}
