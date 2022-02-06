package dev.vortezz.mpd.commands;

import dev.vortezz.mpd.Manager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Save implements CommandExecutor {

    private final Manager manager;

    public Save(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("minv.save")) {
                if (args[0] != null && this.manager.getMain().getServer().getPlayer(args[0]) != null) {
                    try {
                        this.manager.getQueries().setPlayerInfos(this.manager.getMain().getServer().getPlayer(args[0]));
                    } catch (SQLException e) {
                        e.printStackTrace();
                        sender.sendMessage(this.manager.generateTextMessage(ChatColor.RED + "There is a problem while saving " + args[0] + " inventory"));
                        return true;
                    }
                    sender.sendMessage(this.manager.generateTextMessage(ChatColor.GREEN + args[0] + "s inventory was saved"));
                } else {
                    try {
                        this.manager.getQueries().setPlayerInfos(((Player) sender).getPlayer());
                    } catch (SQLException e) {
                        e.printStackTrace();
                        sender.sendMessage(this.manager.generateTextMessage(ChatColor.RED + "There is a problem while saving your inventory"));
                        return true;
                    }
                    sender.sendMessage(this.manager.generateTextMessage(ChatColor.GREEN + "Your inventory was saved"));
                }
            } else {
                sender.sendMessage(this.manager.generateTextMessage(ChatColor.RED + "Permission error"));
            }
            return true;
        }
        return false;
    }

}
