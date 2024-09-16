package me.yirf.judge.commands;

import me.yirf.judge.config.Config;
import me.yirf.judge.interfaces.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class reload implements CommandExecutor {

    Config config = new Config();
    Color translate = new Color(){};

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!sender.hasPermission("judge.reload")) {return false;}
        config.reload();
        sender.sendMessage(translate.format("&aReloaded config.yml"));
        return true;
    }
}
