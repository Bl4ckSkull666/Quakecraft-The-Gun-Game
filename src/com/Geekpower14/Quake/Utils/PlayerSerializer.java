package com.Geekpower14.Quake.Utils;

import com.Geekpower14.Quake.Quake;
import java.io.File;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerSerializer {
    public static FileConfiguration PlayerToConfig(Player p) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(""));
        config.set("Name", p.getName());
        config.set("Health", p.getHealth());
        config.set("Food", p.getFoodLevel());
        config.set("Saturation", p.getSaturation());
        config.set("Exhaustion", p.getExhaustion());
        config.set("XP-Level", p.getLevel());
        config.set("Exp", p.getExp());
        config.set("GameMode", p.getGameMode().name());
        PlayerSerializer.ItemStackToConfig((FileConfiguration)config, "Armor", p.getInventory().getArmorContents());
        PlayerSerializer.ItemStackToConfig((FileConfiguration)config, "Inventaire", p.getInventory().getContents());
        return config;
    }

    public static Boolean RetorePlayer(Player p) {
        File f = new File(Quake.getPlugin().getDataFolder() + "/Save_Players/" + p.getName() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)f);
        PlayerSerializer.ConfigToPlayer(p, (FileConfiguration)config);
        f.delete();
        return true;
    }

    public static Boolean ConfigToPlayer(Player p, FileConfiguration config) {
        if (!config.contains("Name") || !config.getString("Name").equals(p.getName())) {
            return false;
        }
        p.setHealth(config.getDouble("Health"));
        p.setFoodLevel(config.getInt("Food"));
        p.setSaturation((float)config.getDouble("Saturation"));
        p.setExhaustion((float)config.getDouble("Exhaustion"));
        p.setLevel(config.getInt("XP-Level"));
        p.setExp((float)config.getDouble("Exp"));
        try {
            p.setGameMode(GameMode.valueOf(config.getString("GameMode")));
        } catch (Exception var2_2) {
            // empty catch block
        }
        p.getInventory().clear();
        p.getInventory().setArmorContents(PlayerSerializer.ConfigToItemStack(config, "Armor"));
        p.getInventory().setContents(PlayerSerializer.ConfigToItemStack(config, "Inventaire"));
        try {
            p.updateInventory();
        } catch (Exception var2_3) {
            // empty catch block
        }
        return true;
    }

    public static ItemStack[] ConfigToItemStack(FileConfiguration config, String path) {
        int nb = config.getInt(String.valueOf(path) + ".Item-Nb");
        ItemStack[] item = new ItemStack[nb];
        int i = 0;
        while (i < nb) {
            item[i] = config.getItemStack(String.valueOf(path) + ".Item" + i);
            ++i;
        }
        return item;
    }

    public static FileConfiguration ItemStackToConfig(FileConfiguration config, String path, ItemStack[] items) {
        if (config == null) {
            return null;
        }
        config.set(String.valueOf(path) + ".Item-Nb", items.length);
        int i = 0;
        ItemStack[] arritemStack = items;
        int n = arritemStack.length;
        int n2 = 0;
        while (n2 < n) {
            ItemStack it = arritemStack[n2];
            config.set(String.valueOf(path) + ".Item" + i, it);
            ++i;
            ++n2;
        }
        return config;
    }

    /*public static String InventoryToString(Inventory inv) {
        if(inv instanceof PlayerInventory)
            return InventorySerialization.serializePlayerInventoryAsString((PlayerInventory)inv);
        else
            return InventorySerialization.serializeInventoryAsString(inv);
    }*/

    /*public static Inventory StringToInventory(String invString) {
        return InventorySerialization.
        String[] serializedBlocks = invString.split(";");
        String invInfo = serializedBlocks[0];
        Inventory deserializedInventory = Bukkit.getServer().createInventory(null, Integer.valueOf(invInfo));
        return 
        int i = 1;
        while (i < serializedBlocks.length) {
            String[] serializedBlock = serializedBlocks[i].split("#");
            int stackPosition = Integer.valueOf(serializedBlock[0]);
            if(stackPosition < deserializedInventory.getSize()) {
                ItemStack is = null;
                String[] arrstring = serializedBlock[1].split(":");
                int n = arrstring.length;
                int n2 = 0;
                while(n2 < n) {
                    String itemInfo = arrstring[n2];
                    String[] itemAttribute = itemInfo.split("@");
                    if (itemAttribute[0].equals("t")) {
                        is = new ItemStack(Material.getMaterial(itemAttribute[1]));
                    } else if (itemAttribute[0].equals("d") && is != null) {
                        is.setDurability(Short.valueOf(itemAttribute[1]));
                    } else if (itemAttribute[0].equals("a") && is != null) {
                        is.setAmount(Integer.valueOf(itemAttribute[1]));
                    } else if (itemAttribute[0].equals("e") && is != null) {
                        is.addEnchantment(Enchantment.getByName(itemAttribute[1]), Integer.valueOf(itemAttribute[2]));
                    }
                    n2++;
                }
                deserializedInventory.setItem(stackPosition, is);
            }
            i++;
        }
        return deserializedInventory;
    }*/
}

