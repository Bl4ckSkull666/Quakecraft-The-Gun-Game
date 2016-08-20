/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Color
 *  org.bukkit.FireworkEffect
 *  org.bukkit.FireworkEffect$Builder
 *  org.bukkit.FireworkEffect$Type
 *  org.bukkit.Material
 *  org.bukkit.Server
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package com.Geekpower14.Quake.Stuff.Item;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class WoodenHoe extends ItemBasic implements Listener {
    public WoodenHoe() {
        _name = "RailGun";
        _Time = 40L;
        _givePerm = "";
        _price = 0;
        loadConfig();
        saveConfig();
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = defaultItem(Material.WOOD_HOE, _Time);
        return item;
    }

    @Override
    public void shot(Player player) {
        BasicShot(player, FireworkEffect.builder().withColor(Color.RED).with(FireworkEffect.Type.BALL_LARGE).build());
    }

    @Override
    public void eject(Player player) {
    }
}

