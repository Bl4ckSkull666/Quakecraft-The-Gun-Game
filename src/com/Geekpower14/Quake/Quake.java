package com.Geekpower14.Quake;

import com.Geekpower14.Quake.Arena.ArenaManager;
import com.Geekpower14.Quake.Commands.MyCommandExecutor;
import com.Geekpower14.Quake.Eco.EcoManager;
import com.Geekpower14.Quake.Listener.PlayerListener;
import com.Geekpower14.Quake.Listener.Weather;
import com.Geekpower14.Quake.Lobby.LobbyManager;
import com.Geekpower14.Quake.Shop.IconMenuManager;
import com.Geekpower14.Quake.Shop.ShopManager;
import com.Geekpower14.Quake.Stuff.StuffManager;
import com.Geekpower14.Quake.Trans.Translate;
import com.Geekpower14.Quake.Utils.ScoreB;
import com.Geekpower14.Quake.Utils.Version;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

public class Quake
extends JavaPlugin {
    public static boolean _debug = false;
    public static String _version = "3.4.0";
    public static Version _ver = new Version(_version);
    public ArenaManager _am = null;
    public LobbyManager _lobby = null;
    public int _threadlob = 0;
    public int _thread = 0;
    public int _compteur = 0;
    public Boolean _geeklove = true;
    public static Quake _instance = null;
    public WorldEditPlugin _worldEdit;
    public ShopManager _shop;
    public Translate _trad;
    public StuffManager _stuff;
    public IconMenuManager _imm;
    public EcoManager _eco;
    public FileConfiguration _config;
    public Boolean _useVault = false;
    public Material _shopId = Material.EMERALD;
    public List<String> _shopWorlds = new ArrayList<>();
    public List<String> _ScoreWorlds = new ArrayList<>();
    public HashMap<String, ScoreB> _scores = new HashMap();
    public Scoreboard _board;

    @Override
    public void onEnable() {
        _instance = this;

        _imm = new IconMenuManager((Plugin)this);
        _board = Bukkit.getScoreboardManager().getNewScoreboard();
        File confFile = new File(getDataFolder(), "config.yml");
        _config = YamlConfiguration.loadConfiguration(confFile);
        _shopWorlds.add("world");
        _ScoreWorlds.add("world");
        if(confFile.exists())
            loadConfig();
        else
            saveConfig();
        getDataFolder().mkdir();
        new File(getDataFolder().getPath() + "/arenas").mkdir();
        _worldEdit = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if(_worldEdit == null) {
            getLogger().warning("WorldEdit not found !!");
        }
        try {
            _eco = new EcoManager(this);
        } catch (Exception var1_2) {
            // empty catch block
        }
        _trad = new Translate(this);
        _shop = new ShopManager(this);
        _stuff = new StuffManager(this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new Weather(this), this);
        getCommand("Quake").setExecutor(new MyCommandExecutor(this));
        _am = new ArenaManager(this);
        _lobby = new LobbyManager(this);
        _threadlob = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            @Override
            public void run() {
                _lobby.initsign();
            }
        }, 0, 2);
        _thread = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new Task(this), 0, 20);
    }

    @Override
    public void onDisable() {
        _am.Disable();
        _stuff.disable();
        getServer().getScheduler().cancelTask(_threadlob);
        getServer().getScheduler().cancelTask(_thread);
        HandlerList.unregisterAll((Plugin)this);
    }

    public void loadConfig() {
        _debug = _config.getBoolean("debug", false);
        _useVault = _config.getBoolean("useVault", true);
        _shopId = Material.getMaterial(_config.getString("shop.id", "EMERALD"));
        if(_config.contains("shop.world")) {
            _shopWorlds = _config.getStringList("shop.world");
        }
        if(_config.contains("ScoreBoard.world")) {
            _ScoreWorlds = _config.getStringList("ScoreBoard.world");
        }
    }

    @Override
    public void saveConfig() {
        _config.set("debug", _debug);
        _config.set("useVault", _useVault);
        _config.set("shop.id", _shopId.name());
        if(!_shopWorlds.isEmpty())
            _config.set("shop.world", _shopWorlds);
        if(!_ScoreWorlds.isEmpty())
            _config.set("ScoreBoard.world", _ScoreWorlds);
        try {
            _config.save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().log(Level.WARNING, "Failed to save configuration!");
        }
    }

    public static Boolean hasPermission(Player p, String perm) {
        if(perm.equalsIgnoreCase(""))
            return true;
        if(p.isOp())
            return true;
        if(p.hasPermission("Quake.admin"))
            return true;
        return p.hasPermission(perm);
    }

    public void win(final Player player) {
            final int infoxp = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                if(_compteur >= 150)
                    return;
                
                Firework fw = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();
                Random r = new Random();
                int rt = r.nextInt(4) + 1;
                FireworkEffect.Type type = FireworkEffect.Type.BALL;
                if(rt == 1)
                    type = FireworkEffect.Type.BALL;
                if(rt == 2)
                    type = FireworkEffect.Type.BALL_LARGE;
                if(rt == 3)
                    type = FireworkEffect.Type.BURST;
                if(rt == 4)
                    type = FireworkEffect.Type.CREEPER;
                if(rt == 5)
                    type = FireworkEffect.Type.STAR;
                
                int r1i = r.nextInt(17) + 1;
                int r2i = r.nextInt(17) + 1;
                Color c1 = getColor(r1i);
                Color c2 = getColor(r2i);
                FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
                fwm.addEffect(effect);
                int rp = r.nextInt(2) + 1;
                fwm.setPower(rp);
                fw.setFireworkMeta(fwm);
                _compteur++;
            }
        }, 5, 1);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this, new Runnable() {

            @Override
            public void run() {
                getServer().getScheduler().cancelTask(infoxp);
            }
        }, 300);
    }

    private Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }
        return c;
    }

    public static Quake getPlugin() {
        return _instance;
    }

    public static void debug(Exception ex) {
        debug("An error was found:", ex);
    }
    
    public static void debug(String msg) {
        debug(msg, null);
    }
    
    public static void debug(String msg, Exception ex) {
        if(_instance._config.getBoolean("debug", false))
            _instance.getLogger().log(Level.INFO, "An error was found:", ex);
    }
}

