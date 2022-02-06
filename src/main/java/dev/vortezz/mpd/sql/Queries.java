package dev.vortezz.mpd.sql;

import dev.vortezz.mpd.Manager;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries {

    private final Manager manager;

    public Queries(Manager manager) {
        this.manager = manager;
    }

    public void setPlayerInfos(Player player) throws SQLException {
        PreparedStatement query = this.manager.getConnection().prepareStatement("INSERT INTO `" + this.manager.getConfiguration().getString("ddb.tablename", "inventory") + "`(`uuid`, `inventory`, `armor`, `ender`, `food`, `saturation`, `xp`, `health`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        query.setString(1, player.getUniqueId().toString());
        query.setString(2, this.manager.getItemUtils().encodeItemsData(player.getInventory().getContents()));
        query.setString(3, this.manager.getItemUtils().encodeItemsData(player.getInventory().getArmorContents()));
        query.setString(4, this.manager.getItemUtils().encodeItemsData(player.getEnderChest().getContents()));
        query.setFloat(5, player.getFoodLevel());
        query.setFloat(6, player.getSaturation());
        query.setFloat(7, player.getTotalExperience());
        query.setFloat(8, (int) player.getHealth());
        query.executeUpdate();

        this.manager.getMain().getLogger().info("Inventory of " + player.getDisplayName() + " have been saved");

    }

    public void getPlayerInfos(Player player) throws SQLException {
        PreparedStatement query = this.manager.getConnection().prepareStatement("SELECT * FROM `" + this.manager.getConfiguration().getString("ddb.tablename", "inventory") + "` WHERE `uuid` = ?");
        query.setString(1, player.getUniqueId().toString());
        ResultSet rs = query.executeQuery();
        while (rs.next()) {
            player.getInventory().setContents(this.manager.getItemUtils().decodeItemsData(rs.getString("inventory")));
            player.getInventory().setArmorContents(this.manager.getItemUtils().decodeItemsData(rs.getString("armor")));
            player.getEnderChest().setContents(this.manager.getItemUtils().decodeItemsData(rs.getString("ender")));
            player.setFoodLevel((int) rs.getFloat("food"));
            player.setSaturation(rs.getFloat("saturation"));
            player.setTotalExperience((int) rs.getFloat("xp"));
            player.setHealth(rs.getFloat("health"));
        }

        this.manager.getMain().getLogger().info("Inventory of " + player.getDisplayName() + " have been loaded");
    }
}
