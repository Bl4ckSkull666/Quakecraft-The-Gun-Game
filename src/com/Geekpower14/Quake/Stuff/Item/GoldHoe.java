package com.Geekpower14.Quake.Stuff.Item;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class GoldHoe extends ItemBasic implements Listener {
    public GoldHoe() {
        _name = "GoldenGun";
        _needToBuy = "Boosted RailGun";
        _Time = 28L;
        _price = 800;
        loadConfig();
        saveConfig();
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = defaultItem(Material.GOLDEN_HOE, _Time);
        return item;
    }

    @Override
    public void shot(Player player) {
        BasicShot(player, FireworkEffect.builder().withColor(Color.YELLOW).with(FireworkEffect.Type.BALL_LARGE).build());
    }

    @Override
    public void eject(Player player) {
    }
}

