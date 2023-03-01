package dev.anhcraft.neep.struct.dynamic;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.NeepElement;
import dev.anhcraft.neep.struct.container.NeepContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a dynamic element.
 */
public abstract class NeepDynamic<V> extends NeepElement {
    protected String value;

    public NeepDynamic(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, inlineComment);
        this.value = value;
    }

    @NotNull
    protected abstract V handleValue(@NotNull String value);

    @NotNull
    public V computeValue() {
        return handleValue(value);
    }

    @NotNull
    public String stringifyValue() {
        return value;
    }

    @Nullable
    public String setValue(@NotNull String value) {
        String old = this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return getKey() + " " + value.replace("\n", "\\n");
    }
}
