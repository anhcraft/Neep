package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.Mark;
import dev.anhcraft.neep.struct.container.NeepContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Predicate;

public class NeepElement extends NeepComponent {
    public static final Predicate<Character> KEY_VALIDATOR = c -> Character.isLetterOrDigit(c) || c == '_' || c == '-';

    private String key;
    private NeepComment inlineComment;

    public NeepElement(@Nullable NeepContainer<?> parent, @NotNull String key, @Nullable NeepComment inlineComment) {
        super(parent);
        for (char c : (key = key.trim()).toCharArray()) {
            if (!KEY_VALIDATOR.test(c)) {
                throw new IllegalArgumentException("Key contains invalid character(s)");
            }
        }
        this.key = key;
        this.inlineComment = inlineComment;
    }

   @Deprecated
    public void setKey(@NotNull String key) {
        if(!this.key.isEmpty()) throw new UnsupportedOperationException();
        this.key = key;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    @NotNull
    public String getPath() {
        if(getParent() == null) return key;
        String path = getParent().getPath();
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
