package dev.anhcraft.neep.struct.primitive;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.container.NeepContainer;
import dev.anhcraft.neep.struct.NeepNumber;
import dev.anhcraft.neep.utils.MathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepDouble extends NeepPrimitive<Double> implements NeepNumber {
    public NeepDouble(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
    }

    @NotNull
    @Override
    protected Double handleValue(@NotNull String value) {
        return Double.parseDouble(value);
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
        return (long) MathUtil.clamp(Long.MIN_VALUE, Long.MAX_VALUE, computeValue());
    }

    @Override
    public float getValueAsFloat() {
        return (float) MathUtil.clamp(Float.MIN_VALUE, Float.MAX_VALUE, computeValue());
    }

    @Override
    public double getValueAsDouble() {
        return computeValue();
    }
}
