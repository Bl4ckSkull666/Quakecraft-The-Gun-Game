package com.Geekpower14.Quake.Eco;

import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Trans.Score;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EcoManager {
    private Quake _plugin;
    public static Economy _economy = null;
    public Boolean _useVault = false;

    public EcoManager(Quake pl) {
        _plugin = pl;
        _useVault = _plugin._useVault;
        if(_useVault) {
            try {
                setupEconomy();
            } catch (Exception var2_2) {
                // empty catch block
            }
        }
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider economyProvider = _plugin.getServer().getServicesManager().getRegistration((Class)Economy.class);
        if(economyProvider != null) 
            _economy = (Economy)economyProvider.getProvider();

        return _economy != null;
    }

    public int getPlayerMoney(Player player) {
        int Money = 0;
        if (_useVault) {
            Money = (int)_economy.getBalance(player);
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(_plugin.getDataFolder(), "/Eco/" + player.getName() + ".yml"));
            Money = config.getInt("Money");
        }
        return Money;
    }

    public void setPlayerMoney(Player player, int money) {
        if(_useVault) {
            Economy eco = _economy;
            if(getPlayerMoney(player) == money) {
                return;
            }
            if (getPlayerMoney(player) < money) {
                eco.depositPlayer(player, (double)(money - getPlayerMoney(player)));
                return;
            }
            if (getPlayerMoney(player) > money) {
                eco.withdrawPlayer(player, (double)(getPlayerMoney(player) - money));
                return;
            }
        } else {
            YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Eco/" + player.getName() + ".yml"));
            config.set("Name", player.getName());
            config.set("Money", money);
            try {
                config.save(new File(_plugin.getDataFolder(), "/Eco/" + player.getName() + ".yml"));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void soustrairePlayerMoney(Player player, int price) {
        int coins = getPlayerMoney(player);
        setPlayerMoney(player, coins -= price);
    }

    public void addPlayerMoney(Player player, int price) {
        int coins = getPlayerMoney(player);
        setPlayerMoney(player, coins += price);
    }

    public boolean has(Player player, int price) {
        int account = getPlayerMoney(player);
        if(account >= price) {
            return true;
        }
        return false;
    }

    public int getScore(Player p, Score.Type type) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Eco/" + p.getName() + ".yml"));
        return config.getInt(type.toString());
    }

    public void setScore(Player p, Score.Type type, int score) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Eco/" + p.getName() + ".yml"));
        config.set("Name", p.getName());
        config.set(type.toString(), score);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Eco/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addScore(Player p, Score.Type type, int score) {
        int k = getScore(p, type);
        setScore(p, type, k += score);
    }

    public void subtractScore(Player p, Score.Type type, int score) {
        int k = getScore(p, type);
        setScore(p, type, k -= score);
    }

    public void convertToUUID() {
        File folder = new File(_plugin.getDataFolder(), "/eco/");
        if (!folder.exists()) {
            folder.mkdir();
        }
        
        ArrayList<String> Maps = new ArrayList<>();
        for(File f: folder.listFiles()) {
            String MapName = f.getName();
            String name = f.getName().replaceAll(".yml", "");
            Maps.add(name);
            _plugin.getLogger().info("Found arena : " + name);
        }
        
        if(Maps.isEmpty())
            _plugin.getLogger().info(ChatColor.RED + "No Arena found in folder ");
    }
}

