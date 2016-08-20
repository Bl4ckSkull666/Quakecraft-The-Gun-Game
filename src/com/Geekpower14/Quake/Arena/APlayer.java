package com.Geekpower14.Quake.Arena;

import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Stuff.Armor.ArmorBasic;
import com.Geekpower14.Quake.Stuff.Hat.HatBasic;
import com.Geekpower14.Quake.Stuff.Item.ItemBasic;
import com.Geekpower14.Quake.Utils.PlayerSerializer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class APlayer {
    private final Quake _plugin;
    private final Arena _game;
    private final Player _p;
    private Boolean _Reloading = false;
    private Boolean _Invincible = false;
    private Boolean _VIP = false;
    public int _score = 0;
    public int _kill = 0;
    public int _death = 0;
    public ArmorBasic _armor;
    public HatBasic _hat;
    public ItemBasic _item;

    public APlayer(Quake pl, Arena aren, Player player) {
        _plugin = pl;
        _game = aren;
        _p = player;
        if(Quake.hasPermission(player, "Quake.VIP"))
            _VIP = true;
        APlayer.createConfig(player);
        SaveInventory();
    }

    public static void createConfig(Player p) {
        String path = Quake.getPlugin().getDataFolder() + "/Players/" + p.getUniqueId() + ".yml";
        File file = new File(path);
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)file);
        if (!file.exists()) {
            config.set("Name", p.getName());
            config.set("Hoe.Selected", "RailGun");
            ArrayList<String> h = new ArrayList<>();
            h.add("RailGun");
            config.set("Hoe.Bought", h);
            config.set("Hat.Selected", "");
            ArrayList ha = new ArrayList<>();
            config.set("Hat.Bought", ha);
            config.set("Armor.Selected", "");
            ArrayList ar = new ArrayList<>();
            config.set("Armor.Bought", ar);
            try {
                config.save(new File(path));
            } catch (IOException var7_7) {
                // empty catch block
            }
        }
    }

    public void SaveInventory() {
        FileConfiguration config = PlayerSerializer.PlayerToConfig(_p);
        try {
            config.save(new File(_plugin.getDataFolder() + "/Save_Players/" + _p.getUniqueId() + ".yml"));
        } catch (IOException var2_2) {
            // empty catch block
        }
    }

    public void RestoreInventory() {
        File f = new File(_plugin.getDataFolder() + "/Save_Players/" + _p.getUniqueId() + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)f);
        PlayerSerializer.ConfigToPlayer(_p, (FileConfiguration)config);
        f.delete();
    }

    public String getConfig() {
        return _plugin.getDataFolder() + "/Players/" + _p.getUniqueId() + ".yml";
    }

    public void initConfig() {
    }

    public void getKits() {
        _item = _plugin._stuff.getSelectedItem(this);
        _hat = _plugin._stuff.getSelectedHat(this);
        _armor = _plugin._stuff.getSelectedArmor(this);
    }

    public void giveKit() {
        if (_item != null) {
            _p.getInventory().addItem(new ItemStack[]{_item.getItem()});
        }
        if (_hat != null) {
            _p.getInventory().setHelmet(_hat.getItem());
        }
        if (_armor != null) {
            _p.getInventory().setChestplate(_armor.getItem());
        }
    }

    public void giveHat() {
        if (_hat != null) {
            _p.getInventory().setHelmet(_hat.getItem());
        }
    }

    public void giveArmor() {
        if (_armor != null) {
            _p.getInventory().setChestplate(_armor.getItem());
        }
    }

    public void giveItem() {
        if (_item != null) {
            _p.getInventory().addItem(new ItemStack[]{_item.getItem()});
        }
    }

    public void saveConfig(FileConfiguration config) {
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + _p.getUniqueId() + ".yml"));
        }
        catch (IOException e) {
            _plugin.getLogger().warning("save de " + _p.getName() + " impossible !");
        }
    }

    public void setReloading(Long Ticks) {
        _Reloading = true;
        final Long temp = Ticks;
        _p.setExp(0.0f);
        final int infoxp = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                float xp = _p.getExp();
                if ((xp += getincr(temp)) >= 1.0f) {
                    xp = 1.0f;
                }
                _p.setExp(xp);
            }
        }, 0, 2);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                APlayer.access$1(APlayer.this, false);
                _p.setExp(1.0f);
                _plugin.getServer().getScheduler().cancelTask(infoxp);
            }
        }, Ticks.longValue());
    }

    public void setReloading(Boolean t) {
        _Reloading = t;
    }

    public void setinvincible(Boolean t) {
        _Invincible = t;
    }

    public boolean isReloading() {
        return _Reloading;
    }

    public boolean isInvincible() {
        return _Invincible;
    }

    public boolean isVIP() {
        return _VIP;
    }

    public void setInvincible(Long Ticks) {
        _Invincible = true;
        final Long temp = Ticks;
        _p.setExp(0.0f);
        final int infoxp = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                float xp = _p.getExp();
                if ((xp += getincr(temp)) >= 1.0f) {
                    xp = 1.0f;
                }
                _p.setExp(xp);
            }
        }, 0, 2);
        
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                APlayer.access$3(APlayer.this, false);
                _p.setExp(1.0f);
                _plugin.getServer().getScheduler().cancelTask(infoxp);
            }
        }, Ticks);
    }

    public float getincr(Long time) {
        float result = 0.0f;
        float temp = time;
        result = 100.0f / (temp / 2.0f) / 100.0f;
        return result;
    }

    public Arena getGame() {
        return _game;
    }

    public Player getPlayer() {
        return _p;
    }

    public void tell(String message) {
        _p.sendMessage(message);
    }

    public void setLevel(int xp) {
        _p.setLevel(xp);
    }

    public String getName() {
        return _p.getName();
    }

    public String getDisplayName() {
        return _p.getDisplayName();
    }

    public Location getLocation() {
        return _p.getLocation();
    }

    public Location getEyeLocation() {
        return _p.getEyeLocation();
    }

    public boolean isDead() {
        return _p.isDead();
    }

    static /* synthetic */ void access$1(APlayer aPlayer, Boolean bl) {
        aPlayer._Reloading = bl;
    }

    static /* synthetic */ void access$3(APlayer aPlayer, Boolean bl) {
        aPlayer._Invincible = bl;
    }

}

