package dev.anhcraft.neep.writer;

import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepComponent;
import dev.anhcraft.neep.struct.container.NeepContainer;
import dev.anhcraft.neep.Mark;
import org.jetbrains.annotations.NotNull;

class WriteContext {
    private final StringBuilder stringBuilder = new StringBuilder();
    private final WriteHandler writeHandler;
    private final NeepContainer<NeepComponent> container;
    private final String prefix;

    public WriteContext(@NotNull NeepContainer<NeepComponent> container, @NotNull String prefix) {
        this.container = container;
        this.prefix = prefix;
        writeHandler = new WriteHandler(this);
    }

    @NotNull
    public WriteHandler getWriteHandler() {
        return writeHandler;
    }

    public String getPrefix() {
        return prefix;
    }

    @NotNull
    public NeepContainer<NeepComponent> getContainer() {
        return container;
    }

    public void report(@NotNull String error) throws NeepWriterException {
        throw new NeepWriterException(error);
    }

    public WriteContext append(char c){
        stringBuilder.append(c);
        return this;
    }

    public WriteContext append(@NotNull String s){
        stringBuilder.append(s);
        return this;
    }

    public WriteContext nextLine(){
        stringBuilder.append(Mark.LINE_BREAK);
        return this;
    }

    public WriteContext newLine(char c){
        stringBuilder.append(prefix).append(c);
        return this;
    }

    public WriteContext newLine(@NotNull String s){
        stringBuilder.append(prefix).append(s);
        return this;
    }

    @NotNull
    public String buildString(){
        return stringBuilder.toString();
    }
}
