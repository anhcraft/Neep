package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.struct.container.NeepContainer;
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

    public void setContent(@NotNull String content) {
        this.content = content;
    }

    public boolean isInlined() {
        return inlined;
    }

    public void setInlined(boolean inlined) {
        this.inlined = inlined;
    }

    @Override
    public String toString() {
        return (inlined ? "-#" : "#") + content;
    }
}
