package me.yirf.judge.events;

import me.yirf.judge.group.Group;
import me.yirf.judge.menu.Display;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

// removed for now

public class onInteract implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Player target = (Player) event.getRightClicked();

        if (Group.check(player)) {
            Bukkit.broadcastMessage(ChatColor.AQUA + "" + Group.check(player) + "");
            Group.remove(player);
            Bukkit.broadcastMessage(ChatColor.GREEN + "removing");
            return;
        }

        assert player.isSneaking();
        assert target instanceof Player;

        Display.spawnMenu(target, target);
        Bukkit.broadcastMessage(ChatColor.RED + "spawning");
    }
}
