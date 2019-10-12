package com.Geekpower14.Quake.Arena;

import com.Geekpower14.Quake.Eco.EcoManager;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Trans.Score;
import com.Geekpower14.Quake.Utils.FireworkEffectPlayer;
import com.Geekpower14.Quake.Utils.PlayerSerializer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public abstract class Arena {
    public String _name;
    public int _ID;
    public int _timer;
    public Boolean _Active = false;
    public Quake _plugin;
    public int _ingame = 50;
    public int _pregame = 40;
    public int _starting = 30;
    public long _after = 15;
    public Boolean _finished = false;
    public Boolean _stopping = false;
    public int _compteur = 0;
    public int _etat = _pregame;
    public int _goal = 25;
    public Boolean _full = false;
    public Boolean _NaturalDeath = false;
    public Boolean _VIP = false;
    public Boolean _Sneak = true;
    public Boolean _Global_Chat = false;
    public Boolean _Auto_Respawn = false;
    public HashMap<String, APlayer> _players = new HashMap();
    public List<Location> _spawns_B = new ArrayList<>();
    public List<Location> _spawns_R = new ArrayList<>();
    public List<Location> _spawns = new ArrayList<>();
    public int _Coins_Win = 0;
    public int _Coins_Kill = 0;
    public double _VIP_M = 2.0;
    public double _VIPP_M = 3.0;
    public String _map;
    public int _maxplayer;
    public int _minplayer;
    public ScoreboardManager _scoremanager;
    public Scoreboard _board;
    public Objective _objective;
    public Objective _obj_side;
    FireworkEffectPlayer _fw;
    public List<PotionEffect> _potions = new ArrayList<>();

    public Arena(Quake pl, String n, int ID) {
        _plugin = pl;
        _name = n;
        _ID = ID;
        _maxplayer = 11;
        _minplayer = 2;
        _etat = _pregame;
        _map = "unknown";
        _fw = new FireworkEffectPlayer(_plugin);
        testConfig();
        loadConfig();
        _scoremanager = Bukkit.getScoreboardManager();
        _board = _scoremanager.getNewScoreboard();
        _Active = true;
    }

    public void resetArena() {
        _etat = _pregame;
        _full = false;
        _players.clear();
        _stopping = false;
        _finished = false;
        _compteur = 0;
    }

    public abstract Boolean reloadConfig();

    public abstract Boolean loadConfig();

    public abstract Boolean testConfig();

    public abstract Boolean saveConfig();

    public String PoToStr(PotionEffect popo) {
        return String.valueOf(popo.getType().getName()) + ":" + popo.getAmplifier();
    }

    public PotionEffect StrToPo(String popo) {
        String[] list = popo.split(":");
        return new PotionEffect(PotionEffectType.getByName((String)list[0]), Integer.MAX_VALUE, Integer.valueOf(list[1]).intValue());
    }

    protected void setDefaultConfig(FileConfiguration config, String key, Object value) {
        if (!config.isSet(key)) {
            config.set(key, value);
        }
    }

    public void broadcast(String message) {
        for(APlayer player : _players.values())
            player.tell(ChatColor.GRAY + "[" + ChatColor.RED + "Quake" + ChatColor.GRAY + "]: " + message);
    }

    public void chat(String message) {
        for(APlayer player : _players.values())
            player.tell(message);
    }

    public void nbroadcast(String message) {
        for(APlayer player : _players.values())
            player.tell(message);
    }

    public void broadcastXP(int xp) {
        for(APlayer player : _players.values())
            player.setLevel(xp);
    }

    public void playsound(Sound sound, float a, float b) {
        for(APlayer player : _players.values())
            player.getPlayer().playSound(player.getPlayer().getLocation(), sound, a, b);
    }

    public void getGainKill(Player player) {
        double m = 1.0;
        if(Quake.hasPermission(player, "Quake.vip"))
            m = _VIP_M;

        if(Quake.hasPermission(player, "Quake.vip+"))
            m = _VIPP_M;

        EcoManager eco = _plugin._eco;
        eco.addPlayerMoney(player, (int)((double)_Coins_Kill*m));
        eco.addScore(player, Score.Type.Kill, 1);
    }

    public void getScoreShot(Player p) {
        EcoManager eco = _plugin._eco;
        eco.addScore(p, Score.Type.Shot, 1);
    }

    public void getScoreDeath(Player p) {
        EcoManager eco = _plugin._eco;
        eco.addScore(p, Score.Type.Death, 1);
    }

    public void getGainWin(Player player) {
        double m = 1.0;
        if (Quake.hasPermission(player, "Quake.vip")) {
            m = _VIP_M;
        }
        if (Quake.hasPermission(player, "Quake.vip+")) {
            m = _VIPP_M;
        }
        double gain = (double)_Coins_Win * m;
        EcoManager eco = _plugin._eco;
        eco.addPlayerMoney(player, (int)gain);
        eco.addScore(player, Score.Type.Win, 1);
    }

    public void setActive(Boolean active) {
        _Active = active;
    }

    public void setmap(String map) {
        _map = map;
    }

    public void setmax(int max) {
        _maxplayer = max;
    }

    public void setmin(int min) {
        _minplayer = min;
    }

    public Location str2loc(String loc) {
        if (loc == null) {
            return null;
        }
        Location res = null;
        String[] loca = loc.split(", ");
        res = new Location(_plugin.getServer().getWorld(loca[0]), Double.parseDouble(loca[1]), Double.parseDouble(loca[2]), Double.parseDouble(loca[3]), Float.parseFloat(loca[4]), Float.parseFloat(loca[5]));
        return res;
    }

    protected String loc2str(Location loc) {
        return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
    }

    public abstract void joinArena(Player var1);

    public abstract void updateScore();

    public abstract void leaveArena(Player var1);

    public abstract void CrashLeaveArena(Player var1);

    public static void RejoinAfterCrash(Player p) {
        Arena.quitcleaner(p);
        if (Quake.getPlugin()._lobby._lobbyspawn != null) {
            p.teleport(Quake.getPlugin()._lobby._lobbyspawn);
        }
        PlayerSerializer.RetorePlayer(p);
    }

    public void resetCountdown() {
        broadcast(_plugin._trad.get("Game.Arena.Message.Aborde-Game"));
        _etat = _pregame;
        broadcastXP(_starting);
    }

    public void cleaner(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        player.setHealth(20.0);
        player.setSaturation(10.0f);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getInventory().setHelmet(new ItemStack(Material.AIR, 1));
        player.getInventory().setChestplate(new ItemStack(Material.AIR, 1));
        player.getInventory().setLeggings(new ItemStack(Material.AIR, 1));
        player.getInventory().setBoots(new ItemStack(Material.AIR, 1));
        player.getInventory().setHeldItemSlot(0);
        player.setExp(1.0f);
        player.setLevel(0);
        try {
            player.updateInventory();
        }
        catch (Exception var2_2) {
            // empty catch block
        }
    }

    public static void quitcleaner(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        try {
            player.updateInventory();
        }
        catch (Exception effect) {
            // empty catch block
        }
    }

    public abstract void stop();

    public void startDelayed() {
        broadcast(_plugin._trad.get("Game.Arena.Message.RemainTime").replace("[TIME]", "" + _starting));
        _etat = _starting;
        broadcastXP(_etat);
        _timer = _plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)_plugin, (Runnable)new Timer(_plugin, this), 0, 20);
    }

    public abstract void start();

    public void giveStuff(Player player) {
        APlayer ap = getAPlayer(player);
        ap.getKits();
        ap.giveHat();
        ap.giveItem();
        ap.giveArmor();
        player.getInventory().setItem(8, getLeaveDoor());
        player.getInventory().setHeldItemSlot(0);
    }

    public ItemStack getLeaveDoor() {
        ItemStack coucou = new ItemStack(Material.DARK_OAK_DOOR);
        ItemMeta coucou_meta = coucou.getItemMeta();
        coucou_meta.setDisplayName(_plugin._trad.get("Game.item.leave"));
        coucou.setItemMeta(coucou_meta);
        return coucou;
    }

    public void giveEffect(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        for (PotionEffect popo : _potions) {
            player.addPotionEffect(popo);
        }
    }

    public void shotplayer(final Player shooter, Player victim, FireworkEffect effect) {
        APlayer ashooter = getAPlayer(shooter);
        APlayer avictim = getAPlayer(victim);
        if (victim != shooter && !avictim.isInvincible()) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {

                @Override
                public void run() {
                    getGainKill(shooter);
                }
            }, 2);
            
            FireworkEffectPlayer.playFirework(victim.getLocation());
            /*try {
                _fw.playFirework(victim.getWorld(), victim.getLocation(), effect);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            
            kill(victim);
            broadcast(_plugin._trad.get("Game.Arena.Message.Shot").replace("[SHOOTER]", shooter.getName()).replace("[KILLED]", victim.getName()));
            ashooter._score++;
            if (ashooter._score == _goal) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {
                    @Override
                    public void run() {
                        win(shooter);
                    }
                }, 2);
            }
            updateScore();
        }
    }

    public void kill(Player p) {
        p.setHealth(0.0);
        _Auto_Respawn = true;
    }

    public abstract void win(Player var1);

    public Color getColor(int i) {
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

    public void disable() {
        _Active = false;
        stop();
    }

    public void tp(Player player) {
        player.teleport(getTp(player));
    }

    public abstract Location getTp(Player var1);

    public Boolean isingame(Player player) {
        return _players.containsKey(player.getName());
    }

    public int getplayers() {
        return _players.size();
    }

    public APlayer getAPlayer(Player play) {
        for (APlayer p : _players.values()) {
            if (!p.getPlayer().getName().equals(play.getName())) continue;
            return p;
        }
        return null;
    }

}

