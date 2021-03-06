package dev.anhcraft.neep.reader;

import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.struct.container.NeepSection;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class NeepReader {
    private static final int BUFFER_SIZE = 1024;

    @NotNull
    public static NeepSection parse(@NotNull String string) throws NeepReaderException {
        NeepSection section = new NeepSection(null, "", null, new ArrayList<>());
        ReadContext readContext = new ReadContext(
                string,
                section
        );
        readContext.handle();
        return section;
    }

    @NotNull
    public static NeepSection parse(@NotNull InputStream stream) throws IOException, NeepReaderException {
        return parse(stream, BUFFER_SIZE);
    }

    @NotNull
    public static NeepSection parse(@NotNull InputStream stream, int bufferSize) throws IOException, NeepReaderException {
        char[] buffer = new char[bufferSize];
        StringBuilder stringBuilder = new StringBuilder();
        Reader in = new InputStreamReader(stream, StandardCharsets.UTF_8);
        int charsRead;
        while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            stringBuilder.append(buffer, 0, charsRead);
        }
        in.close();
        return parse(stringBuilder.toString());
    }
}
