package me.yirf.judge.events;


import me.yirf.judge.group.Group;
import me.yirf.judge.menu.Display;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.UUID;

// decided to not use sneak event (this is debug version so dont use unless u change.)

public class onSneak implements Listener {

    HashMap<UUID, UUID> group = Group.group;

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();

        if (group.get(p.getUniqueId()) != null) {
            Group.remove(p);
            return;
        }

        RayTraceResult result = p.rayTraceEntities(10);
        Entity entity = result.getHitEntity();

        if (result == null || !(entity instanceof Player)) {return;}

        assert event.isSneaking();

        if(Bukkit.getServer().getOnlinePlayers().contains(p)) {
            Display.spawnMenu(p, (Player) entity);
        }

    }

}
