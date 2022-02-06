package dev.vortezz.mpd;

import dev.vortezz.mpd.listener.PlayerListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class Main extends JavaPlugin {

    private Manager manager;
    private FileConfiguration configuration;

    @Override
    public void onEnable() {
        this.getLogger().info("MysqlPlayerData is starting...");

        this.saveDefaultConfig();

        try {
            this.manager = new Manager(this);
            this.configuration = this.manager.getConfiguration();

            this.tryMysql();
            this.initTable();
        } catch (SQLException e) {
            e.printStackTrace();
            this.getLogger().severe("Mysql Error... Plugin is disabled.");
            this.getPluginLoader().disablePlugin(this);
        }

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this.manager), this);

        if (this.configuration.getBoolean("autosync.activated")) {
            this.manager.getAutoSync().start();
        }
    }

    public void tryMysql() throws SQLException {
        this.manager.getConnection().createStatement().executeQuery("SELECT 1");
    }

    public void initTable() throws SQLException {
        this.manager.getConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS `" + this.configuration.getString("ddb.tablename", "inventory") + "` (\r\n"
                + "  `uuid` text NOT NULL,\r\n" + "  `inventory` text NOT NULL,\r\n" + "  `armor` text NOT NULL,\r\n"
                + "  `ender` text NOT NULL,\r\n" + "  `food` float(11) NOT NULL,\r\n"
                + "  `saturation` float(11) NOT NULL,\r\n" + "  `xp` float(11) NOT NULL,\r\n"
                + "  `health` float(11) NOT NULL\r\n" + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;");
    }

    public void reloadAll() throws SQLException {
        this.manager = new Manager(this);
    }

}
