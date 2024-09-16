package me.yirf.judge.config;

import me.yirf.judge.Judge;
import me.yirf.judge.interfaces.Color;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Config implements Color {

    private static Judge judge = Judge.instance;
    private static FileConfiguration config = judge.getConfigYaml();
    private File configFile = judge.getConfigFile();


    public static String getString(String path) {
        return config.getString(path);
    }

    public static Float getFloat(String path) {
        return (float) config.getDouble(path);
    }

    public static int getInt(String path) {
        return config.getInt(path);
    }

    public static boolean getBoolean(String path) {return config.getBoolean(path);}

    public static org.bukkit.Color getRGB(String path) {
        List<Integer> rgbList = Arrays.stream(config.getString(path).split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        return org.bukkit.Color.fromRGB(
                rgbList.get(0),
                rgbList.get(1),
                rgbList.get(2)
        );
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
        Judge.menuVert = Config.getFloat("properties.vertical");
        Judge.menuHoroz = Config.getFloat("properties.horizontal");
        Judge.menuScale = Config.getFloat("properties.scale");
        Judge.menuTexts = Config.getStringList("board");
        if(!Config.getBoolean("allow-all-worlds")) {
            Judge.allowedWorlds = Config.getWorldList("allowed-worlds");
        }
    }
}
