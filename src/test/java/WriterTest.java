import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.reader.NeepReader;
import dev.anhcraft.neep.struct.NeepContainer;
import dev.anhcraft.neep.struct.NeepElement;
import dev.anhcraft.neep.struct.NeepSection;
import dev.anhcraft.neep.writer.NeepWriter;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Consumer;

public class WriterTest {
    private void print(NeepSection section) {
        try {
            System.out.println(NeepWriter.stringify(section));
        } catch (NeepWriterException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test0(){
        try {
            print(NeepReader.parse(getClass().getResourceAsStream("/general.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        try {
            print(NeepReader.parse(getClass().getResourceAsStream("/pet.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try {
            print(NeepReader.parse(getClass().getResourceAsStream("/webserver.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }
}
