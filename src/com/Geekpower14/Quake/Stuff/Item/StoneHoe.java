package com.Geekpower14.Quake.Stuff.Item;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class StoneHoe extends ItemBasic implements Listener {
    public StoneHoe() {
        _name = "Advanced RailGun";
        _Time = 36L;
        _price = 400;
        loadConfig();
        saveConfig();
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = defaultItem(Material.STONE_HOE, _Time);
        return item;
    }

    @Override
    public void shot(Player player) {
        BasicShot(player, FireworkEffect.builder().withColor(Color.GREEN).with(FireworkEffect.Type.BALL_LARGE).build());
    }

    @Override
    public void eject(Player player) {
    }
}

