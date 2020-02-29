package dev.anhcraft.neep.reader;

import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.struct.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class ReadContext {
    private ReadHandler readHandler;
    private NeepContainer<?> container; // current container
    private String string;
    private NeepComponent lastInlinedEntry;
    private int cursor;
    private Consumer<ReadContext> childCallback;

    public ReadContext(@NotNull String string, @NotNull NeepContainer<?> container) {
        this.string = string;
        this.container = container;
        readHandler = new ReadHandler(this);
    }

    public ReadContext(@NotNull ReadContext parent, @NotNull NeepContainer<?> container, @NotNull Consumer<ReadContext> childCallback) {
        this.string = parent.string;
        this.container = container;
        this.cursor = parent.cursor;
        this.childCallback = childCallback;
        readHandler = new ReadHandler(this);
    }

    @NotNull
    public ReadHandler getReadHandler() {
        return readHandler;
    }

    @NotNull
    public NeepContainer<?> getContainer() {
        return container;
    }

    @NotNull
    public String getString() {
        return string;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public void moveCursor(int delta){
        cursor += delta;
    }

    public int getCursor() {
        return cursor;
    }

    @Nullable
    public NeepComponent getLastInlinedEntry() {
        return lastInlinedEntry;
    }

    public void setLastInlinedEntry(NeepComponent lastInlinedEntry) {
        this.lastInlinedEntry = lastInlinedEntry;
    }

    @Nullable
    public Consumer<ReadContext> getChildCallback() {
        return childCallback;
    }

    public void report(@NotNull String error) throws NeepReaderException {
        throw new NeepReaderException(error);
    }

    public void submit(@NotNull NeepComponent component){
        if (!(component instanceof NeepComment)) {
            lastInlinedEntry = component;
        }
        if(container instanceof NeepSection) {
            ((NeepSection) container).appendLast(component);
        } else if(container instanceof NeepList){
            //noinspection unchecked,rawtypes
            ((NeepList) container).appendLast(component);
        }
    }

    public void collectUtil(char end, @NotNull Consumer<StringBuilder> callback) {
        collectUtil(end, callback, callback);
    }

    public void collectUtil(char end, @NotNull Consumer<StringBuilder> success, @NotNull Consumer<StringBuilder> eos) {
        StringBuilder stringBuilder = new StringBuilder();
        while (cursor < string.length()){
            char c = string.charAt(cursor++);
            if(c == end) {
                success.accept(stringBuilder);
                return;
            } else {
                stringBuilder.append(c);
            }
        }
        eos.accept(stringBuilder);
    }

    public void handle() throws NeepReaderException {
        if(cursor >= string.length()) return;
        while (cursor < string.length()) {
            char c = string.charAt(cursor++);
            if (!readHandler.next(c)) {
                return;
            }
        }
        readHandler.eos();
    }
}
