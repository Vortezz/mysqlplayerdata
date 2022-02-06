package dev.vortezz.mpd.utils;

import dev.vortezz.mpd.Manager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class ItemUtils {

    private final Manager manager;

    public ItemUtils(Manager manager) {
        this.manager = manager;
    }

    public String encodeItemsData(ItemStack[] items) throws IllegalStateException {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            BukkitObjectOutputStream data = new BukkitObjectOutputStream(output);

            data.writeInt(items.length);

            for (ItemStack i : items) {
                data.writeObject(i);
            }

            data.close();
            return Base64Coder.encodeLines(output.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack[] decodeItemsData(String str) throws IllegalStateException {
        try {
            ByteArrayInputStream input = new ByteArrayInputStream(Base64Coder.decodeLines(str));
            BukkitObjectInputStream data = new BukkitObjectInputStream(input);
            ItemStack[] items = new ItemStack[data.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) data.readObject();
            }

            data.close();
            return items;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
