package dev.vortezz.mpd.commands;

import dev.vortezz.mpd.Manager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Reload implements CommandExecutor {

    private final Manager manager;

    public Reload(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("minv.reload")) {
                try {
                    this.manager.getMain().reloadAll();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sender.sendMessage(this.manager.generateTextMessage(ChatColor.GREEN + "Plugin configuration have been reloaded"));
            } else {
                sender.sendMessage(this.manager.generateTextMessage(ChatColor.RED + "Permission error"));
            }
            return true;
        }
        return false;
    }

}
