package dev.anhcraft.neep.struct.dynamic;

import dev.anhcraft.neep.struct.NeepComment;
import dev.anhcraft.neep.struct.container.NeepContainer;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NeepExpression extends NeepDynamic<Double> {
    private final Expression expression;

    public NeepExpression(@Nullable NeepContainer<?> parent, @NotNull String key, @NotNull String value, @Nullable NeepComment inlineComment){
        super(parent, key, value, inlineComment);
        expression = new ExpressionBuilder(value).build();
    }

    @NotNull
    @Override
    protected Double handleValue(@NotNull String value) {
        return expression.evaluate();
    }

    @Override
    public String toString() {
        return getKey() + " " + value + " (= "+ computeValue() +")";
    }
}
