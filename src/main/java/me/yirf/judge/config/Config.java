package me.yirf.judge.config;

import me.yirf.judge.Judge;
import me.yirf.judge.interfaces.Color;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Config implements Color {

    private static Judge judge = Judge.instance;
    private static FileConfiguration config = judge.getConfigYaml();
    private File configFile = judge.getConfigFile();


    public static String getString(String path) {
        return config.getString(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static Float getFloat(String path) {
        return (float) config.getDouble(path);
    }

    public static boolean getBoolean(String path) {return config.getBoolean(path);}

    public Set<String> getNodes() {
        return config.getKeys(false);
    }

    public static List<String> getStringList(String path) {
        return config.getStringList(path);
    }

    public static List<World> getWorldList(String path) {
        ArrayList<World> worlds = new ArrayList<>();
        for(String s : config.getStringList(path)) {
            worlds.add(Bukkit.getWorld(s));
        }
        return worlds;
    }


    public void reload() {
        if (configFile == null) {
            Bukkit.getLogger().warning("Unable to find config.yml in path 'plugins/afk/' remaking it...");
            configFile = new File(judge.getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        Judge.menuVert = Config.getFloat("translation.vertical");
        Judge.menuHoroz = Config.getFloat("translation.horizontal");
        Judge.menuScale = Config.getFloat("translation.scale");
        Judge.menuTexts = Config.getStringList("board");
        if(!Config.getBoolean("allow-all-worlds")) {
            Judge.allowedWorlds = Config.getWorldList("allowed-worlds");
        }
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
