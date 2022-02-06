package dev.vortezz.mpd;

import dev.vortezz.mpd.sql.Queries;
import dev.vortezz.mpd.utils.AutoSync;
import dev.vortezz.mpd.utils.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Manager {

    private final Main main;
    private final FileConfiguration configuration;
    private final Connection connection;
    private final ItemUtils itemUtils;
    private final AutoSync autoSync;
    private final Queries queries;

    Manager(Main main) throws SQLException {
        this.main = main;
        this.configuration = main.getConfig();
        this.connection = DriverManager.getConnection(
                "jdbc:mysql://" + this.configuration.getString("ddb.host", "127.0.0.1") + ":" + this.configuration.getInt("ddb.port", 3306) + "/"
                        + this.configuration.getString("ddb.name", "inventories"),
                this.configuration.getString("ddb.user", "root"), this.configuration.getString("ddb.pass", "root"));
        this.itemUtils = new ItemUtils(this);
        this.queries = new Queries(this);
        this.autoSync = new AutoSync(this);
    }

    public FileConfiguration getConfiguration() {
        return this.configuration;
    }

    public Main getMain() {
        return this.main;
    }

    public Connection getConnection() {
        return this.connection;
    }

    public Queries getQueries() {
        return this.queries;
    }

    public AutoSync getAutoSync() {
        return this.autoSync;
    }

    public String generateTextMessage(String message) {
        return String.format("%s[%sMPD%s] %s", ChatColor.GOLD, ChatColor.YELLOW, ChatColor.GOLD, message);
    }

    public ItemUtils getItemUtils() {
        return this.itemUtils;
    }
}
