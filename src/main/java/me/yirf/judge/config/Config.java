package me.yirf.judge.config;

import me.yirf.judge.Judge;
import me.yirf.judge.interfaces.Color;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Config implements Color {

    private static Judge judge = Judge.instance;
    private static FileConfiguration config = judge.getConfigYaml();
    private File configFile = judge.getConfigFile();
    private Color translate = new Color(){};


    public static String getString(String path) {
        return config.getString(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static Float getFloat(String path) {
        return (float) config.getDouble(path);
    }

    public Set<String> getNodes() {
        return config.getKeys(false);
    }

    public static List<String> getStringList(String path) {
        return config.getStringList(path);
    }


    public void reload() {
        if (configFile == null) {
            Bukkit.getLogger().warning("Unable to find config.yml in path 'plugins/afk/' remaking it...");
            configFile = new File(judge.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
