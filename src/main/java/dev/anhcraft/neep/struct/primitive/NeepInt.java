package dev.anhcraft.neep.struct.primitive;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.container.NeepContainer;
import dev.anhcraft.neep.struct.NeepNumber;
import dev.anhcraft.neep.utils.MathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepInt extends NeepPrimitive<Integer> implements NeepNumber {
    public NeepInt(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
    }

    @NotNull
    @Override
    protected Integer handleValue(@NotNull String value) {
        return Integer.parseInt(value);
    }

    @Override
    public byte getValueAsByte() {
        return (byte) MathUtil.clamp(Byte.MIN_VALUE, Byte.MAX_VALUE, computeValue());
    }

    @Override
    public short getValueAsShort() {
        return (short) MathUtil.clamp(Short.MIN_VALUE, Short.MAX_VALUE, computeValue());
    }

    @Override
    public int getValueAsInt() {
        return computeValue();
    }

    @Override
    public long getValueAsLong() {
        return computeValue();
    }

    @Override
    public float getValueAsFloat() {
        return computeValue();
    }

    @Override
    public double getValueAsDouble() {
        return computeValue();
    }
}
