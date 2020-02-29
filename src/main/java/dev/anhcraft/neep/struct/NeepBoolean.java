package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepBoolean extends NeepPrimitive<Boolean> {
    public NeepBoolean(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
    }

    @NotNull
    @Override
    Boolean handleValue(@NotNull String value) {
        return Boolean.parseBoolean(value);
    }
}
