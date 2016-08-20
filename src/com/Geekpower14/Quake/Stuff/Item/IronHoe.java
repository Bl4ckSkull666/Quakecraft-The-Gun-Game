package com.Geekpower14.Quake.Stuff.Item;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class IronHoe extends ItemBasic implements Listener {
    public IronHoe() {
        _name = "Boosted RailGun";
        _needToBuy = "Advanced RailGun";
        _Time = 32L;
        _price = 600;
        loadConfig();
        saveConfig();
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = defaultItem(Material.IRON_HOE, _Time);
        return item;
    }

    @Override
    public void shot(Player player) {
        BasicShot(player, FireworkEffect.builder().withColor(Color.GRAY).with(FireworkEffect.Type.BALL_LARGE).build());
    }

    @Override
    public void eject(Player player) {
    }
}

