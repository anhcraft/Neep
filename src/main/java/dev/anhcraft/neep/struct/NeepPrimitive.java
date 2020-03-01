package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NeepPrimitive<V> extends NeepDynamic<V> {
    private V val;

    public NeepPrimitive(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
        val = super.computeValue();
    }

    @Override
    @NotNull
    public V computeValue() {
        return val;
    }

    @NotNull
    public V getValue() {
        return val;
    }

    @Nullable
    public String setValue(@NotNull String value) {
        String old = super.setValue(value);
        val = super.computeValue();
        return old;
    }

    @Nullable
    public Object setValue(@NotNull Object value) {
        return setValue(String.valueOf(value));
    }
}
