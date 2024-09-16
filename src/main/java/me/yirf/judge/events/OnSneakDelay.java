package me.yirf.judge.events;

import me.yirf.judge.Judge;
import me.yirf.judge.config.Config;
import me.yirf.judge.group.Group;
import me.yirf.judge.menu.Display;
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

        if (group.get(p.getUniqueId()) != null) {
            Group.remove(p);
            control.remove(p.getUniqueId());
            return;
        }

        if(!Config.getBoolean("allow-all-worlds")) {
            if (!Judge.allowedWorlds.contains(p.getWorld())) {
                return;
            }
        }

        RayTraceResult result = p.rayTraceEntities(10);
        if (result == null || !(result.getHitEntity() instanceof Player)) {return;}
        Entity entity = result.getHitEntity();

        assert event.isSneaking();

        if(Bukkit.getServer().getOnlinePlayers().contains((Player) entity)) {
            if(control.get(p.getUniqueId()) != null) {;return;}
            Bukkit.getScheduler().runTaskLater(Judge.instance, () -> {
                control.put(p.getUniqueId(), true);
                assert event.isSneaking();
                Display.spawnMenu(p, (Player) entity);
            }, 20L * Config.getInt("delay"));
        }

    }

}