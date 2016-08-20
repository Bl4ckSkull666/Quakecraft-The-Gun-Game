package com.Geekpower14.Quake.Stuff.Hat;

import com.Geekpower14.Quake.Trans.Translate;
import java.io.File;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class HatCustomFile extends HatBasic implements Listener {
    FileConfiguration _config;

    public HatCustomFile(String path) {
        _config = YamlConfiguration.loadConfiguration(new File(path));
        _price = _config.getInt("Price");
        _name = _config.getString("Name");
        _givePerm = "Quake.admin";
        _needToBuy = "";
        Material material = Material.getMaterial(_config.getString("Material"));
        if(material == null || material == Material.AIR) {
            _plugin.getLogger().severe(_config.getString("Material") + " not a valid Material. Example: " + Material.APPLE.name());
        }
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    public static ItemStack Skull(String skullOwner) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
        skullMeta.setOwner(skullOwner);
        skull.setItemMeta((ItemMeta)skullMeta);
        return skull;
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = null;
        if(_config.getString("Material").startsWith("Player:")) {
            String tmp = _config.getString("Material");
            String[] tb = tmp.split(":");
            item = HatCustomFile.Skull(tb[1]);
        } else {
            Material material = Material.getMaterial((String)_config.getString("Material"));
            if (material == null || material == Material.AIR) {
                _plugin.getLogger().severe(_config.getString("Material") + " not a valid Material. Example: " + Material.APPLE.name());
            }
            item = new ItemStack(material, 1);
        }
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + _name);
        ArrayList<String> arg0 = new ArrayList<>();
        String desc = _config.getString("Description");
        if(desc == null) {
            _plugin.getLogger().severe("Description not set");
        }
        arg0.add(ChatColor.RESET + Translate.replaceColors(desc));
        meta.setLore(arg0);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll((Listener)this);
    }

    @Override
    public void saveConfig() {
    }
}

