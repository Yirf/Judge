package me.yirf.judge.events;

import com.viaversion.viaversion.api.Via;
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

        if(control.get(p.getUniqueId()) != null) {;return;}

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

        //Prevent displaying to the player if they are on a version without display entities (under 1.19.4) if ViaVersion is installed.
        if(Bukkit.getServer().getPluginManager().isPluginEnabled("ViaVersion")) {
            if(Via.getAPI().getPlayerVersion(p.getUniqueId()) < 762) {
                return;
            }
        }

        if (!event.isSneaking()) {return;}

        Bukkit.getScheduler().runTaskLater(Judge.instance, () -> { //super fat fucking sched!!!
            control.put(p.getUniqueId(), true);
            RayTraceResult result = p.rayTraceEntities(10);
            if (result == null || !(result.getHitEntity() instanceof Player)) {return;}
            Entity entity = result.getHitEntity();
            if(!Bukkit.getServer().getOnlinePlayers().contains((Player) entity)) {return;}
            if (!event.isSneaking()) {return;}
            Display.spawnMenu(p, (Player) entity);
        }, 20L * Config.getInt("delay"));

    }

}
