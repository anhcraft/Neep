package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.Mark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepElement extends NeepComponent {
    private String key;
    private NeepComment inlineComment;

    public NeepElement(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment) {
        super(parent);
        this.key = key;
        this.inlineComment = inlineComment;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    @NotNull
    public String getPath() {
        if(getParent() == null) return key;
        String path = ((NeepElement) getParent()).getPath();
        return path.isEmpty() ? key : path + Mark.PATH_SEPARATOR + key;
    }

    @Nullable
    public NeepComment getInlineComment() {
        return inlineComment;
    }

    public void setInlineComment(@Nullable NeepComment inlineComment) {
        this.inlineComment = inlineComment;
    }

    @Override
    public String toString() {
        return key;
    }
}
