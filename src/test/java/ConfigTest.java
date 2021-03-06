import dev.anhcraft.neep.NeepConfig;
import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.struct.NeepComponent;
import dev.anhcraft.neep.struct.primitive.NeepBoolean;
import dev.anhcraft.neep.struct.primitive.NeepString;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

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
            config.stringify();
            Assert.assertTrue(config.getBoolean("settings.logging.enabled"));
            Assert.assertEquals(config.get("settings.logging.file.path"), "logs.txt");
            Assert.assertEquals(config.getConfigSection("settings.logging.file").getInt("auto_remove"), 24000);
            Assert.assertArrayEquals(config.getConfigSection("settings.logging.file").getParent().asSection().get("filters").asList().stream().map(NeepComponent::getValueAsObject).toArray(), new String[]{"main","request","response"});
            Assert.assertEquals(config.getConfigSection("routers").size(), 5);
            Assert.assertTrue(config.contains("settings.logging.file.path"));
            Assert.assertTrue(config.contains("settings.logging.file.path", NeepString.class));
            Assert.assertFalse(config.contains("settings.logging.file.path", NeepBoolean.class));
            NeepConfig api = NeepConfig.create();
            api.set("version", 1);
            api.set("paths", ((Supplier<NeepConfig>) () -> {
                NeepConfig paths = NeepConfig.create();
                paths.set("create_post", "posts/create/:id");
                paths.set("remove_post", "posts/remove/:id");
                paths.set("get_post", "posts/get/:id");
                return paths;
            }).get());
            config.set("api", api);
        } catch (IOException | NeepReaderException | NeepWriterException e) {
            e.printStackTrace();
        }
    }
}
