package dev.vortezz.mpd.listener;

import dev.vortezz.mpd.Manager;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class PlayerListener implements Listener {

    private final Manager manager;

    public PlayerListener(Manager manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        try {
            this.manager.getQueries().setPlayerInfos(event.getPlayer());
        } catch (SQLException e) {
            this.manager.getMain().getLogger().severe("Inventory of " + event.getPlayer().getDisplayName() + " haven't been saved");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            this.manager.getQueries().getPlayerInfos(event.getPlayer());
        } catch (SQLException e) {
            e.printStackTrace();
            event.getPlayer().sendMessage(this.manager.generateTextMessage(ChatColor.RED + "There is a problem while loading your inventory, please contact server staff"));
        }
    }

}
