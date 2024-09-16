package me.yirf.judge.commands;

import me.yirf.judge.config.Config;
import me.yirf.judge.interfaces.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    Config config = new Config();
    //Color translate = new Color(){};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!sender.hasPermission("judge.reload")) {return false;}
        config.reload();
        sender.sendMessage(Color.format("&aReloaded config.yml"));
        return true;
    }
}
