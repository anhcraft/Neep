import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.reader.NeepReader;
import dev.anhcraft.neep.struct.container.NeepSection;
import dev.anhcraft.neep.writer.NeepWriter;
import org.junit.Test;

import java.io.IOException;

public class WriterTest {
    private void print(NeepSection section) {
        try {
            NeepWriter.stringify(section);
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

    @Test
    public void test3(){
        try {
            print(NeepReader.parse(getClass().getResourceAsStream("/survey.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }
}
