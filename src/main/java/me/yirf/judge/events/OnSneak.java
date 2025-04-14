package me.yirf.judge.events;

import me.yirf.judge.Judge;
import me.yirf.judge.config.Config;
import me.yirf.judge.group.Group;
import me.yirf.judge.menu.Display;
import me.yirf.judge.utils.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.RayTraceResult;
import static me.yirf.judge.group.Group.group;

@EventHandler
public void onShift(PlayerToggleSneakEvent event) {
    Player p = event.getPlayer();

    if (group.get(p.getUniqueId()) != null) {
        Group.remove(p);
        return;
    }

    if (!Config.getBoolean("allow-all-worlds")) {
        if (!Judge.allowedWorlds.contains(p.getWorld())) {
            return;
        }
    }

    if (Judge.hasWorldGuard && Config.getBoolean("specific-regions")) {
        if (!RegionUtil.containsRegion(p, Config.getStringList("allowed-regions"))) {
            return;
        }
    }

    RayTraceResult result = p.rayTraceEntities(10);
    if (result == null || !(result.getHitEntity() instanceof Player)) {
        return;
    }

    Player target = (Player) result.getHitEntity();

    if (target.hasMetadata("NPC")) {
        return;
    }

    if (target.hasMetadata("vanished")) {
        return;
    }

    if (!event.isSneaking()) {
        return;
    }

    if (Bukkit.getServer().getOnlinePlayers().contains(p)) {
        Display.spawnMenu(p, target);
    }
}

