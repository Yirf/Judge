package me.yirf.judge.events;

import me.yirf.judge.group.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class onDisconnect implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(Group.check(p)) {Group.remove(p);}
    }
}
