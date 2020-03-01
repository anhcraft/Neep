package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.Mark;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeepElement element = (NeepElement) o;
        return getPath().equals(element.getPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPath());
    }
}
