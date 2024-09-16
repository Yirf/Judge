package me.yirf.judge.group;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class Group {

    public static HashMap<UUID, UUID> group = new HashMap<>();
    public static HashMap<UUID, Boolean> control = new HashMap<>();

    public static void add(Entity entity, Player p) {
        UUID eu = entity.getUniqueId();
        UUID pu = p.getUniqueId();
        group.put(pu, eu);
    }

    public static void remove(Player p) {
        UUID du = group.get(p.getUniqueId());
        Entity display = Bukkit.getEntity(du);
        display.remove();
        group.remove(p.getUniqueId());
    }

    public static boolean check(Player p) {
        return group.get(p.getUniqueId()) != null;
    }
}
