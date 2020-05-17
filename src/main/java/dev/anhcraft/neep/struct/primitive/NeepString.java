package dev.anhcraft.neep.struct.primitive;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.container.NeepContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepString extends NeepPrimitive<String> {
    public NeepString(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
    }

    @NotNull
    @Override
    protected String handleValue(@NotNull String value) {
        return value;
    }
}
