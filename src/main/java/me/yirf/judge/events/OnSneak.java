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

public class OnSneak implements Listener {
    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();

        if (group.get(p.getUniqueId()) != null) {
            Group.remove(p);
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

        RayTraceResult result = p.rayTraceEntities(10);
        if (result == null || !(result.getHitEntity() instanceof Player)) {return;}
        Entity entity = result.getHitEntity();

        if (!event.isSneaking()) {return;}

        if(Bukkit.getServer().getOnlinePlayers().contains(p)) {
            Display.spawnMenu(p, (Player) entity);
        }

    }

}
