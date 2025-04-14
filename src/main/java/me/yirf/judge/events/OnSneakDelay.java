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
import static me.yirf.judge.group.Group.control;

public class OnSneakDelay implements Listener {
    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();

        if (control.get(p.getUniqueId()) != null) {
            return;
        }

        if (group.get(p.getUniqueId()) != null) {
            Group.remove(p);
            control.remove(p.getUniqueId());
            return;
        }

        if (!Config.getBoolean("allow-all-worlds")) {
            if (!Judge.allowedWorlds.contains(p.getWorld())) {
                return;
            }
        }

        if (Judge.hasWorldGuard || Config.getBoolean("specific-regions")) {
            if (!RegionUtil.containsRegion(p, Config.getStringList("allowed-regions"))) {
                return;
            }
        }

        if (!event.isSneaking()) {
            return;
        }

        Bukkit.getScheduler().runTaskLater(Judge.instance, () -> {
            control.put(p.getUniqueId(), true);

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

            if (!Bukkit.getServer().getOnlinePlayers().contains(target)) {
                return;
            }

            if (!p.isSneaking()) {
                return;
            }

            Display.spawnMenu(p, target);
        }, Config.getInt("delay"));
    }
}
