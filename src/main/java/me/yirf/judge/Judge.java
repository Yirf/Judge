package me.yirf.judge;

import me.yirf.judge.config.Config;
import me.yirf.judge.events.OnDisconnect;
import me.yirf.judge.events.OnSneak;
import me.yirf.judge.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public final class Judge extends JavaPlugin {

    File configFile = new File(getDataFolder(), "config.yml");
    FileConfiguration configYaml = YamlConfiguration.loadConfiguration(configFile);

    public static Judge instance;
    public static PluginManager pm;
    public static float menuVert;
    public static float menuHoroz;
    public static List<String> menuTexts;

    @Override
    public void onEnable() {
        instance = this;
        pm = getServer().getPluginManager();
        init();
        sched();
        data();

    }

    @Override
    public void onDisable() {
        for (World world: Bukkit.getWorlds()){
            for (TextDisplay entity : world.getEntitiesByClass(TextDisplay.class)){
                entity.remove();
            }
        }
    }

    private void data() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        if(!configFile.exists()) {
            saveResource("config.yml", false);
        }

        menuVert = Config.getFloat("translation.vertical");
        menuHoroz = Config.getFloat("translation.horizontal");
        menuTexts = Config.getStringList("board");
    }

    private void init() {
        pm.registerEvents(new OnSneak(), this);
        pm.registerEvents(new OnDisconnect(), this);
    }

    public void sched() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, this::checkFalse, 0L, 20L);
    }

    public void checkFalse() {
        Set<UUID> uuids = Group.group.keySet();
        for (UUID u : uuids) {
            Player player = Bukkit.getPlayer(u);
            if (!player.isSneaking()) {
                Group.remove(player);
            }
        }
    }

    public FileConfiguration getConfigYaml() {
        return configYaml;
    }

    public File getConfigFile() {
        return configFile;
    }
}
