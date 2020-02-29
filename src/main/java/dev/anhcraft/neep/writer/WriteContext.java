package dev.anhcraft.neep.writer;

import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepComponent;
import dev.anhcraft.neep.struct.NeepContainer;
import dev.anhcraft.neep.utils.Mark;
import org.jetbrains.annotations.NotNull;

public class WriteContext {
    private StringBuilder stringBuilder = new StringBuilder();
    private WriteHandler writeHandler;
    private NeepContainer<NeepComponent> container;
    private String prefix;

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