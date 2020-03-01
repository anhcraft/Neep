import dev.anhcraft.neep.NeepConfig;
import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepComponent;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ConfigTest {
    @Test
    public void test() {
        try {
            NeepConfig config = NeepConfig.fromInputStream(getClass().getResourceAsStream("/webserver.neep"));
            config.set("settings.logging.enabled", true);
            config.set("settings.logging.file.path", "logs.txt");
            config.set("settings.logging.file.auto_remove", 24000);
            config.set("settings.logging.filters", new String[]{"main","request","response"});
            Map<String, String> routers = new HashMap<>();
            routers.put("home", "/home");
            routers.put("profile", "/profile/:user/");
            routers.put("post", "/post/:id/");
            routers.put("posts", "/posts/");
            routers.put("posts_sort", "/posts/:sort/");
            config.set("routers", routers);
            config.set("settings.ssl", null);
            Objects.requireNonNull(config.getSection("settings")).remove(4);
            System.out.println(config.stringify());
            System.out.println("--------------------------------------------------");
            for(String s : config.getRoot().getKeys(true)){
                NeepComponent c = config.getComponent(s);
                if(c != null) {
                    System.out.println(s + ": " + c.getClass().getSimpleName());
                }
            }
        } catch (IOException | NeepReaderException | NeepWriterException e) {
            e.printStackTrace();
        }
    }
}
