package dev.anhcraft.neep.struct;

import dev.anhcraft.neep.utils.MathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepLong extends NeepPrimitive<Long> implements NeepNumber {
    public NeepLong(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
    }

    @NotNull
    @Override
    Long handleValue(@NotNull String value) {
        return Long.parseLong(value);
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
        return (int) MathUtil.clamp(Integer.MIN_VALUE, Integer.MAX_VALUE, computeValue());
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
