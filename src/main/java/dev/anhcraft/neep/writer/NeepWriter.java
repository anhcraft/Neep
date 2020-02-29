package dev.anhcraft.neep.writer;

import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepSection;
import org.jetbrains.annotations.NotNull;

public class NeepWriter {
    @NotNull
    public static String stringify(@NotNull NeepSection section) throws NeepWriterException {
        WriteContext readContext = new WriteContext(section, "");
        readContext.getWriteHandler().write();
        return readContext.buildString();
    }
}
