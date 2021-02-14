import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.reader.NeepReader;
import org.junit.Test;

import java.io.IOException;

public class ReaderTest {
    private void print(String prefix, Object obj){/*
        if(obj instanceof NeepContainer<?>) {
            String k = ((NeepElement) obj).getKey();
            System.out.println(prefix+obj.getClass().getSimpleName()+": "+k+" ("+((NeepContainer<?>) obj).size()+")");
            ((NeepContainer<?>) obj).forEach((Consumer<Object>) o -> print(prefix + "  ", o));
        } else {
            System.out.println(prefix+obj.getClass().getSimpleName()+": " + obj.toString());
        }*/
    }

    @Test
    public void test0(){
        try {
            print("", NeepReader.parse(getClass().getResourceAsStream("/general.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){
        try {
            print("", NeepReader.parse(getClass().getResourceAsStream("/pet.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        try {
            print("", NeepReader.parse(getClass().getResourceAsStream("/webserver.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3(){
        try {
            print("", NeepReader.parse(getClass().getResourceAsStream("/survey.neep")));
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }
}
