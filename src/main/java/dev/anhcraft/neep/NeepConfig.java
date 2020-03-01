package dev.anhcraft.neep;

import dev.anhcraft.neep.errors.NeepReaderException;
import dev.anhcraft.neep.errors.NeepWriterException;
import dev.anhcraft.neep.reader.NeepReader;
import dev.anhcraft.neep.struct.*;
import dev.anhcraft.neep.writer.NeepWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.ArrayList;

public class NeepConfig {
    @NotNull
    public static NeepConfig create(){
        return new NeepConfig(new NeepSection(null, "", null, new ArrayList<>()));
    }

    @NotNull
    public static NeepConfig of(@NotNull NeepSection root) {
        return new NeepConfig(root);
    }

    @NotNull
    public static NeepConfig fromString(@NotNull String string) throws NeepReaderException {
        return new NeepConfig(NeepReader.parse(string));
    }

    @NotNull
    public static NeepConfig fromInputStream(@NotNull InputStream inputStream) throws IOException, NeepReaderException {
        return new NeepConfig(NeepReader.parse(inputStream));
    }

    @NotNull
    public static NeepConfig fromFile(@NotNull File file) throws IOException, NeepReaderException {
        return new NeepConfig(NeepReader.parse(new FileInputStream(file)));
    }

    private NeepSection root;

    private NeepConfig(NeepSection root) {
        this.root = root;
    }

    private NeepComponent get(String path, boolean parent) {
        if(path.isEmpty()) return null;
        String[] keys = path.split("\\.");
        int i = 0;
        NeepSection container = root;
        NeepComponent component = null;
        do {
            if(component instanceof NeepSection) {
                container = (NeepSection) component;
            } else if(component != null) {
                throw new IllegalArgumentException("Invalid path");
            } else if(i > 0) {
                return null;
            }
            String key = keys[i++];
            component = container.get(key);
        } while (i < keys.length);
        return parent ? container : component;
    }

    @Nullable
    public NeepComponent getComponent(@NotNull String path) {
        return get(path, false);
    }

    @Nullable
    public NeepSection getParent(@NotNull String path) {
        return (NeepSection) get(path, true);
    }

    @Nullable
    public NeepComponent set(@NotNull String path, @Nullable Object object) {
        if(path.isEmpty()) return null;
        String[] keys = path.split("\\.");
        int i = 0;
        NeepSection section = root;
        NeepComponent component = null;
        do {
            if(component instanceof NeepSection) {
                section = (NeepSection) component;
            } else if(component != null) {
                throw new IllegalArgumentException("Invalid path");
            } else if(i > 0) {
                if(object == null) return null;
                String k = keys[i - 1];
                NeepSection ns = new NeepSection(section, k, null , new ArrayList<>());
                section.add(ns);
                section = ns;
            }
            String key = keys[i++];
            component = section.get(key);
        } while (i < keys.length);
        String key = keys[keys.length - 1];
        if(object == null) {
            section.remove(key);
            return null;
        } else {
            int ind = section.indexOf(key);
            NeepComponent c = NeepComponent.create(section, key, object);
            if(ind == -1) {
                section.add(c);
            } else {
                section.set(ind, c);
            }
            return c;
        }
    }

    @Nullable
    public NeepComponent remove(@NotNull String path) {
        return set(path, null);
    }

    @Nullable
    public String getString(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepDynamic<?> ? ((NeepDynamic<?>) component).stringifyValue() : null;
    }

    @NotNull
    public String getString(@NotNull String path, @NotNull String def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepString ? ((NeepString) component).getValue() : def;
    }

    public boolean getBoolean(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepBoolean ? ((NeepBoolean) component).getValue() : false;
    }

    public boolean getBoolean(@NotNull String path, boolean def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepBoolean ? ((NeepBoolean) component).getValue() : def;
    }

    public int getInt(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepInt ? ((NeepInt) component).getValue() : 0;
    }

    public int getInt(@NotNull String path, int def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepInt ? ((NeepInt) component).getValue() : def;
    }

    public long getLong(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepLong ? ((NeepLong) component).getValue() : 0;
    }

    public long getLong(@NotNull String path, long def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepLong ? ((NeepLong) component).getValue() : def;
    }

    public double getDouble(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepDouble ? ((NeepDouble) component).getValue() : 0;
    }

    public double getDouble(@NotNull String path, double def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepDouble ? ((NeepDouble) component).getValue() : def;
    }

    public double getExpression(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepExpression ? ((NeepExpression) component).computeValue() : 0;
    }

    public double getExpression(@NotNull String path, double def) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepExpression ? ((NeepExpression) component).computeValue() : def;
    }

    @Nullable
    public NeepSection getSection(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepSection ? (NeepSection) component : null;
    }

    @Nullable
    public NeepConfig getConfigSection(@NotNull String path) {
        NeepSection section = getSection(path);
        return section == null ? null : NeepConfig.of(section);
    }

    @Nullable
    public NeepList<?> getList(@NotNull String path) {
        NeepComponent component = getComponent(path);
        return component instanceof NeepList ? (NeepList<?>) component : null;
    }

    public int size() {
        return root.size();
    }

    @NotNull
    public NeepSection getRoot() {
        return root;
    }

    @NotNull
    public String stringify() throws NeepWriterException {
        return NeepWriter.stringify(root);
    }

    public void save(@NotNull OutputStream outputStream) throws NeepWriterException, IOException {
        NeepWriter.write(root, outputStream);
        outputStream.close();
    }

    public boolean save(@NotNull File file) throws NeepWriterException, IOException {
        if(!file.exists() && !file.createNewFile()) {
            return false;
        }
        save(new FileOutputStream(file));
        return true;
    }
}
