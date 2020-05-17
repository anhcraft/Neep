import dev.anhcraft.neep.NeepConfig;
import dev.anhcraft.neep.struct.*;
import dev.anhcraft.neep.struct.container.NeepContainer;
import dev.anhcraft.neep.struct.container.NeepList;
import dev.anhcraft.neep.struct.container.NeepSection;
import dev.anhcraft.neep.struct.primitive.NeepPrimitive;
import org.junit.Test;
import utils.Action;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

public class AutomaticTest {
    private static final String KEY_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";
    private static final String STRING_CHARS_1 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@$%^&*+-*/=;:<>,.?/|";
    private static final String STRING_CHARS_2 = STRING_CHARS_1 + "\n";
    private static final SecureRandom RANDOMIZER = new SecureRandom();

    private static String randomKey(int len){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < len; i++){
            stringBuilder.append(KEY_CHARS.charAt(RANDOMIZER.nextInt(KEY_CHARS.length())));
        }
        return stringBuilder.toString();
    }

    private static String randomComment(int len){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < len; i++){
            stringBuilder.append(STRING_CHARS_1.charAt(RANDOMIZER.nextInt(STRING_CHARS_1.length())));
        }
        return stringBuilder.toString();
    }

    private static String randomString(int len){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < len; i++){
            stringBuilder.append(STRING_CHARS_2.charAt(RANDOMIZER.nextInt(STRING_CHARS_2.length())));
        }
        return stringBuilder.toString();
    }

    private static NeepComment generateRandomComment(NeepContainer<?> container, boolean inlined){
        return new NeepComment(container, " "+randomComment(10), inlined);
    }

    private static int addRandomPrimitive(NeepContainer<NeepComponent> container){
        Object object = null;
        switch (RANDOMIZER.nextInt(5)){
            case 0: {
                object = RANDOMIZER.nextBoolean();
                break;
            }
            case 1: {
                object = RANDOMIZER.nextInt(10000);
                break;
            }
            case 2: {
                object = RANDOMIZER.nextDouble();
                break;
            }
            case 3: {
                object = RANDOMIZER.nextLong();
                break;
            }
            case 4: {
                object = randomString(RANDOMIZER.nextInt(51) + 3);
                break;
            }
        }
        assert object != null;
        NeepElement element = NeepPrimitive.create(container, randomKey(5), object).asElement();
        container.add(element);
        if(RANDOMIZER.nextInt(5) == 0) {
            NeepComment cmt = generateRandomComment(container, true);
            element.setInlineComment(cmt);
            container.add(cmt);
            return 2;
        }
        return 1;
    }

    private static int addRandomContainer(NeepContainer<NeepComponent> container, int size){
        NeepContainer<NeepComponent> component = RANDOMIZER.nextBoolean() ? new NeepSection(
                container,
                randomKey(5),
                null,
                new ArrayList<>()
        ) : new NeepList<>(
                container,
                randomKey(5),
                null,
                new ArrayList<>()
        );
        int i = 1;
        container.add(component);
        if(RANDOMIZER.nextInt(5) == 0) {
            NeepComment cmt = generateRandomComment(container, true);
            component.setInlineComment(cmt);
            container.add(cmt);
            i++;
        }
        while (i < size){
            i += nextComponent(component);
        }
        return i;
    }

    public static int nextComponent(NeepContainer<NeepComponent> container) {
        switch (RANDOMIZER.nextInt(10)) {
            case 0:
                return addRandomContainer(container, RANDOMIZER.nextInt(30) + 3);
            case 1:
            case 2: {
                NeepComment cmt = generateRandomComment(container, false);
                container.add(cmt);
                return 1;
            }
            default:
                return addRandomPrimitive(container);
        }
    }

    private static NeepSection randomConfig(){
        NeepSection section = new NeepSection(null, "", null, new ArrayList<>());
        int max = RANDOMIZER.nextInt(100) + 10;
        int i = 0;
        while (i < max) {
            i += nextComponent(section);
        }
        return section;
    }

    @Test
    public void main(){
        NeepConfig config = NeepConfig.of(randomConfig());
        try {
            System.out.println("---------------------------------------------------");
            String s = config.stringify();
            System.out.println(s);
            System.out.println("---------------------------------------------------");
            new Action("write", config::stringify, 10000).start().report();
            new Action("read", () -> NeepConfig.fromString(s), 10000).start().report();
            System.out.println("---------------------------------------------------");
            NeepConfig c = NeepConfig.fromString(s);
            for(String k : config.getRoot().getKeys(true)){
                if(!Objects.requireNonNull(config.get(k)).equals(c.get(k))){
                    throw new Exception("Equals check failed! Key = " + k);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
