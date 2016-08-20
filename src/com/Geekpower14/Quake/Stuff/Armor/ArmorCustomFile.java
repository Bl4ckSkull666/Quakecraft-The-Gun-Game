package com.Geekpower14.Quake.Stuff.Armor;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ArmorCustomFile extends ArmorBasic implements Listener {
    FileConfiguration _config;

    public ArmorCustomFile(String path) {
        _config = YamlConfiguration.loadConfiguration(new File(path));
        _price = _config.getInt("Price");
        _name = _config.getString("Name");
        _givePerm = "Quake.admin";
        _needToBuy = "";
        ItemStack item = _config.getItemStack("Material");
        if (item == null) {
            _plugin.getLogger().severe(_config.getString("Name") + " not a valid Material.");
        } else
            _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        return _config.getItemStack("Material");
    }

    @Override
    public void disable() {
        HandlerList.unregisterAll((Listener)this);
    }

    @Override
    public void saveConfig() {
    }
}

