package dev.anhcraft.neep.writer;

import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepSection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NeepWriter {
    @NotNull
    public static String stringify(@NotNull NeepSection section) throws NeepWriterException {
        WriteContext readContext = new WriteContext(section, "");
        readContext.getWriteHandler().write();
        return readContext.buildString();
    }

    public static void write(@NotNull NeepSection section, @NotNull OutputStream outputStream) throws NeepWriterException, IOException {
        outputStream.write(stringify(section).getBytes(StandardCharsets.UTF_8));
    }
}
