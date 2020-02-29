import dev.anhcraft.neep.NeepConfig;
import dev.anhcraft.neep.errors.NeepReaderException;
import org.junit.Test;

import java.io.IOException;

public class ConfigTest {
    @Test
    public void test() {
        try {
            NeepConfig config = NeepConfig.fromInputStream(getClass().getResourceAsStream("/webserver.neep"));
            for(String s : config.getRoot().getKeys(false)){
                System.out.println(s);
            }
        } catch (IOException | NeepReaderException e) {
            e.printStackTrace();
        }
    }
}
