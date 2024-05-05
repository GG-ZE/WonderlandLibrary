package client.util.file.config;

import client.util.file.FileManager;

import java.io.File;
import java.util.ArrayList;

public class ConfigManager extends ArrayList<ConfigFile> {

    public static final File CONFIG_DIRECTORY = new File(FileManager.DIRECTORY, "configs");

    public void init() {
        if (!CONFIG_DIRECTORY.exists()) CONFIG_DIRECTORY.mkdir();
        update();
    }

    public ConfigFile get(final String config, final boolean allowKey) {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, config + ".json");
        final ConfigFile configFile = new ConfigFile(file);
        if (allowKey) configFile.allowKeyCodeLoading();
        return configFile;
    }

    public ConfigFile get(final String config) {
        final File file = new File(ConfigManager.CONFIG_DIRECTORY, config + ".json");
        final ConfigFile configFile = new ConfigFile(file);
        configFile.allowKeyCodeLoading();
        return configFile;
    }

    public void set(final String config) {
        final File file = new File(CONFIG_DIRECTORY, config + ".json");
        ConfigFile configFile = get(config);
        if (configFile == null) {
            configFile = new ConfigFile(file);
            add(configFile);
            System.out.println("Creating new config...");
        } else System.out.println("Overwriting existing config...");
        configFile.write();
        System.out.println("Config saved to files.");
    }

    public boolean update() {
        clear();
        final File[] files = CONFIG_DIRECTORY.listFiles();
        if (files == null) return false;
        for (final File file : files) {
            if (file.getName().endsWith(".json")) add(new ConfigFile(file));
        }
        return true;
    }

    public boolean delete(final String config) {
        final ConfigFile configFile = get(config);
        if (configFile == null) return false;
        remove(configFile);
        return configFile.getFile().delete();
    }
}