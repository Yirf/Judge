package me.yirf.judge.events;

import me.yirf.judge.group.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import static me.yirf.judge.group.Group.control;

public class OnDisconnect implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Group.check(p)) {Group.remove(p);}
        control.remove(p.getUniqueId());
    }
}
