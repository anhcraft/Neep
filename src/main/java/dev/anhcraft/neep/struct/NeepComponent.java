package dev.anhcraft.neep.struct;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepComponent {
    private NeepContainer<?> parent;

    public NeepComponent(@Nullable NeepContainer<?> parent) {
        this.parent = parent;
    }

    @Nullable
    public NeepContainer<?> getParent() {
        return parent;
    }

    public boolean isComment(){
        return this instanceof NeepComment;
    }

    public boolean isElement(){
        return this instanceof NeepElement;
    }

    public boolean isDynamic(){
        return this instanceof NeepDynamic<?>;
    }

    public boolean isPrimitive(){
        return this instanceof NeepPrimitive<?>;
    }

    public boolean isList(){
        return this instanceof NeepList<?>;
    }

    public boolean isSection(){
        return this instanceof NeepSection;
    }

    @NotNull
    public NeepComment asComment(){
        return (NeepComment) this;
    }

    @NotNull
    public NeepElement asElement(){
        return (NeepElement) this;
    }

    @NotNull
    public NeepDynamic<?> asDynamic(){
        return (NeepDynamic<?>) this;
    }

    @NotNull
    public NeepPrimitive<?> asPrimitive(){
        return (NeepPrimitive<?>) this;
    }

    @NotNull
    public NeepList<?> asList(){
        return (NeepList<?>) this;
    }

    @NotNull
    public NeepSection asSection(){
        return (NeepSection) this;
    }
}
