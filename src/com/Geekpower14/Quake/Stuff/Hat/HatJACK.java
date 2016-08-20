package com.Geekpower14.Quake.Stuff.Hat;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HatJACK extends HatBasic implements Listener {
    public HatJACK() {
        _price = 50;
        _name = "JackOHat";
        _givePerm = "Quake.admin";
        _needToBuy = "";
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = new ItemStack(Material.JACK_O_LANTERN, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + _name);
        ArrayList<String> arg0 = new ArrayList<>();
        arg0.add(ChatColor.BOLD + "It's cool !");
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

