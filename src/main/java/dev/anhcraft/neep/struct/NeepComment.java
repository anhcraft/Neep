package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepComment extends NeepComponent {
    private String content;
    private boolean inlined;

    public NeepComment(@Nullable NeepContainer<?> parent, @NotNull String content, boolean inlined) {
        super(parent);
        this.content = content;
        this.inlined = inlined;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    public boolean isInlined() {
        return inlined;
    }

    @Override
    public String toString() {
        return (inlined ? "-#" : "#") + content;
    }
}
