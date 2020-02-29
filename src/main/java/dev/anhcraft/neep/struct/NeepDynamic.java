package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NeepDynamic<V> extends NeepElement {
    protected String value;

    public NeepDynamic(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, inlineComment);
        this.value = value;
    }

    @NotNull
    abstract V handleValue(@NotNull String value);

    @NotNull
    public V computeValue() {
        return handleValue(value);
    }

    @NotNull
    public String stringifyValue() {
        return value;
    }

    @Override
    public String toString() {
        return getKey() + " " + value.replace("\n", "\\n");
    }
}
