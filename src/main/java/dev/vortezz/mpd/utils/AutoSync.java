package dev.vortezz.mpd.utils;

import dev.vortezz.mpd.Manager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class AutoSync {

    private final Manager manager;

    public AutoSync(Manager manager) {
        this.manager = manager;
    }

    public void start() {
        this.manager.getMain().getServer().getScheduler().scheduleSyncRepeatingTask(this.manager.getMain(), () -> {
            for (Player player : this.manager.getMain().getServer().getOnlinePlayers()) {
                try {
                    this.manager.getQueries().setPlayerInfos(player);
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.manager.getMain().getLogger().severe("Mysql Error while loading " + player.getDisplayName() + " inventory");
                }
            }
        }, 20L * this.manager.getConfiguration().getInt("autosync.first", 60), 20L * this.manager.getConfiguration().getInt("autosync.between", 60));
    }

}
