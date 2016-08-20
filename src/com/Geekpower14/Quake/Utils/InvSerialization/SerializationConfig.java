package com.Geekpower14.Quake.Utils.InvSerialization;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class SerializationConfig {
    private static YamlConfiguration config;

    protected SerializationConfig() {
    }

    public static File getDataFolder() {
        File pluginFolder = Bukkit.getServer().getPluginManager().getPlugins()[0].getDataFolder();
        return new File(pluginFolder.getParentFile() + "/TacoSerialization/");
    }

    public static File getConfigFile() {
        return new File(SerializationConfig.getDataFolder() + "/config.yml");
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration((File)SerializationConfig.getConfigFile());
        SerializationConfig.setDefaults();
        SerializationConfig.save();
        Logger.getLogger("Minecraft").log(Level.INFO, "[TacoSerialization] Config reloaded");
    }

    public static void save() {
        try {
            config.save(SerializationConfig.getConfigFile());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setDefaults() {
        SerializationConfig.addDefault("horse.color", true);
        SerializationConfig.addDefault("horse.inventory", true);
        SerializationConfig.addDefault("horse.jump-stength", true);
        SerializationConfig.addDefault("horse.style", true);
        SerializationConfig.addDefault("horse.variant", true);
        SerializationConfig.addDefault("living-entity.age", true);
        SerializationConfig.addDefault("living-entity.health", true);
        SerializationConfig.addDefault("living-entity.name", true);
        SerializationConfig.addDefault("living-entity.potion-effects", true);
        SerializationConfig.addDefault("ocelot.type", true);
        SerializationConfig.addDefault("player.ender-chest", true);
        SerializationConfig.addDefault("player.inventory", true);
        SerializationConfig.addDefault("player.stats", true);
        SerializationConfig.addDefault("player-stats.can-fly", true);
        SerializationConfig.addDefault("player-stats.display-name", true);
        SerializationConfig.addDefault("player-stats.exhaustion", true);
        SerializationConfig.addDefault("player-stats.exp", true);
        SerializationConfig.addDefault("player-stats.food", true);
        SerializationConfig.addDefault("player-stats.flying", true);
        SerializationConfig.addDefault("player-stats.health", true);
        SerializationConfig.addDefault("player-stats.level", true);
        SerializationConfig.addDefault("player-stats.potion-effects", true);
        SerializationConfig.addDefault("player-stats.saturation", true);
        SerializationConfig.addDefault("wolf.collar-color", true);
    }

    public static void addDefault(String path, Object value) {
        if (!SerializationConfig.getConfig().contains(path)) {
            SerializationConfig.getConfig().set(path, value);
        }
    }

    private static YamlConfiguration getConfig() {
        if (config == null) {
            SerializationConfig.reload();
        }
        return config;
    }

    public static boolean getShouldSerialize(String path) {
        return SerializationConfig.getConfig().getBoolean(path);
    }
}

