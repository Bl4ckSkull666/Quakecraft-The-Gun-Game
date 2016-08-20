package com.Geekpower14.Quake.Stuff.Item;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Arena.Arena;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DiamondHoe extends ItemBasic implements Listener {
    public DiamondHoe() {
        _name = "Amazing RailGun";
        _needToBuy = "GoldenGun";
        _Time = 20L;
        _price = 1200;
        loadConfig();
        saveConfig();
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    @Override
    public ItemStack getItem() {
        ItemStack item = defaultItem(Material.DIAMOND_HOE, _Time);
        return item;
    }

    @Override
    public void shot(Player player) {
        BasicShot(player, FireworkEffect.builder().withColor(Color.AQUA).with(FireworkEffect.Type.BURST).build());
    }

    @Override
    public void eject(Player player) {
    }

    public void explode(Player player, FireworkEffect effect) {
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if (arena == null) {
            return;
        }
        APlayer ap = arena.getAPlayer(player);
        if (ap.isReloading()) {
            return;
        }
        ap.setReloading(true);
        try {
            _fw.playFirework(player.getWorld(), player.getLocation(), effect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long startTime = System.currentTimeMillis();
        int maxRange = 15;
        ArrayList<Player> targets = new ArrayList<>();
        
        for(Player online: Bukkit.getOnlinePlayers()) {
            if (online != player && online.getLocation().distance(player.getEyeLocation()) < (double)maxRange) {
                targets.add(online);
            }
        }
        
        Location loc = player.getEyeLocation();
        for(Player p : targets) {
            if (p.getUniqueId() == player.getUniqueId()) continue;
            Location two = p.getEyeLocation();
            Vector from = new Vector(loc.getX(), loc.getY(), loc.getZ());
            Vector to = new Vector(two.getX(), two.getY(), two.getZ());
            Vector vector = to.subtract(from).normalize();
            vector.add(new Vector(0, 1, 0));
            vector.multiply(1.7);
            p.setVelocity(vector);
        }
        
        ap.setReloading(_Time);
    }
}

