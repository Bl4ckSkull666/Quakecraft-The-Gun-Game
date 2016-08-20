package com.Geekpower14.Quake.Arena;

import com.Geekpower14.Quake.Quake;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

public class SArena extends Arena {
    public SArena(Quake pl, String n, int ID) {
        super(pl, n, ID);
    }

    @Override
    public Boolean reloadConfig() {
        if (_etat != _pregame) {
            stop();
        }
        testConfig();
        loadConfig();
        return true;
    }

    @Override
    public Boolean loadConfig() {
        File fichier_config = new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)fichier_config);
        if (config.contains("Nombre")) {
            int nombre = config.getInt("Nombre");
            for (int i = 0; i < nombre; i++) {
                String nom = config.getString("spawn" + i);
                _spawns.add(i, str2loc(nom));
            }
        } else {
            List<Location> s = new ArrayList();
            for(String spawn : config.getStringList("Spawns")) {
                s.add(str2loc(spawn));
            }
            _spawns = s;
        }
        _map = config.getString("Map");
        _maxplayer = config.getInt("MaxPlayers");
        _minplayer = config.getInt("MinPlayers");
        _Active = config.getBoolean("Active");
        _NaturalDeath = config.getBoolean("Natural-Death");
        _Auto_Respawn = config.getBoolean("Auto-Respawn");
        _VIP = config.getBoolean("VIP");
        _goal = config.getInt("Goal");
        _starting = config.getInt("Time-Before");
        _after = config.getLong("Time-After");
        _Coins_Win = config.getInt("Coins-per-Win");
        _Coins_Kill = config.getInt("Coins-per-Kill");
        _VIP_M = config.getDouble("VIP-Multiplicator");
        _VIPP_M = config.getDouble("VIP+-Multiplicator");
        _Global_Chat = config.getBoolean("Global-Chat");
        _Sneak = config.getBoolean("Sneak");
        ArrayList<PotionEffect> l = new ArrayList<>();
        for (String popo : config.getStringList("Potions"))
            l.add(StrToPo(popo));
        _potions = l;
        if(config.contains("Nombre"))
            saveConfig();
        return true;
    }

    @Override
    public Boolean testConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        setDefaultConfig((FileConfiguration)config, "Version", Quake._version);
        setDefaultConfig((FileConfiguration)config, "Name", _name);
        setDefaultConfig((FileConfiguration)config, "Type", "Solo");
        setDefaultConfig((FileConfiguration)config, "Map", "Unknown");
        setDefaultConfig((FileConfiguration)config, "Active", true);
        setDefaultConfig((FileConfiguration)config, "Natural-Death", false);
        setDefaultConfig((FileConfiguration)config, "Auto-Respawn", false);
        setDefaultConfig((FileConfiguration)config, "VIP", false);
        setDefaultConfig((FileConfiguration)config, "Goal", 25);
        setDefaultConfig((FileConfiguration)config, "Time-Before", 30);
        setDefaultConfig((FileConfiguration)config, "Time-After", 15);
        setDefaultConfig((FileConfiguration)config, "Coins-per-Win", 20);
        setDefaultConfig((FileConfiguration)config, "Coins-per-Kill", 1);
        setDefaultConfig((FileConfiguration)config, "VIP-Multiplicator", 2);
        setDefaultConfig((FileConfiguration)config, "VIP+-Multiplicator", 3);
        setDefaultConfig((FileConfiguration)config, "Global-Chat", false);
        ArrayList<String> l = new ArrayList<>();
        l.add("SPEED:2");
        l.add("JUMP:1");
        setDefaultConfig((FileConfiguration)config, "Potions", l);
        setDefaultConfig((FileConfiguration)config, "MaxPlayers", 11);
        setDefaultConfig((FileConfiguration)config, "MinPlayers", 2);
        setDefaultConfig((FileConfiguration)config, "Sneak", true);
        setDefaultConfig((FileConfiguration)config, "Spawns", new ArrayList());
        try {
            config.save(new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        } catch (IOException e) {
            _plugin.getLogger().warning("save default de " + _name + " impossible !");
            disable();
        }
        return true;
    }

    @Override
    public Boolean saveConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        config.set("Name", _name);
        config.set("Type", "Solo");
        config.set("Map", _map);
        config.set("Active", _Active);
        config.set("VIP", _VIP);
        config.set("Global-Chat", _Global_Chat);
        config.set("Natural-Death", _NaturalDeath);
        config.set("Auto-Respawn", _Auto_Respawn);
        config.set("Sneak", _Sneak);
        ArrayList<String> l = new ArrayList<>();
        for(PotionEffect popo : _potions)
            l.add(PoToStr(popo));
        
        config.set("Potions", l);
        config.set("MaxPlayers", _maxplayer);
        config.set("MinPlayers", _minplayer);
        config.set("Sneak", _Sneak);
        ArrayList<String> s = new ArrayList<>();
        for(Location loc : _spawns)
            s.add(loc2str(loc));
        
        config.set("Spawns", s);
        try {
            config.save(new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
            _plugin.saveConfig();
        } catch (IOException e) {
            _plugin.getLogger().warning("save de " + _name + "impossible !");
            disable();
            return false;
        }
        return true;
    }

    @Override
    public void joinArena(Player player) {
        if (_spawns.isEmpty()) {
            player.sendMessage(_plugin._trad.get("Game.Arena.error.NoSpawn"));
            return;
        }
        if (_etat > _pregame && !Quake.hasPermission(player, "Quake.JoinInGame")) {
            player.sendMessage(_plugin._trad.get("Game.Arena.error.inGame"));
            return;
        }
        if (_VIP && !Quake.hasPermission(player, "Quake.VIP")) {
            player.sendMessage(_plugin._trad.get("Game.Arena.error.VIP"));
            return;
        }
        if (_full && !Quake.hasPermission(player, "Quake.VIP")) {
            player.sendMessage(_plugin._trad.get("Game.Arena.error.full"));
            return;
        }
        if (_players.size() > _maxplayer - 1) {
            _full = true;
        }
        APlayer ap = new APlayer(_plugin, this, player);
        _players.put(player.getName(), ap);
        broadcast(_plugin._trad.get("Game.Arena.Message.Join").replace("[PLAYER]", player.getName()).replace("[NUMBER]", "" + _players.size()).replace("[MAX]", "" + _maxplayer));
        if (_players.size() >= _minplayer && _etat == _pregame) {
            startDelayed();
        }
        if (_etat > _pregame && Quake.hasPermission(player, "Quake.JoinInGame")) {
            tp(player);
            cleaner(player);
            updateScore();
            ap.setInvincible(40L);
            final Player p = player;
            Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {

                @Override
                public void run() {
                    giveStuff(p);
                }
            }, 20);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    giveEffect(p);
                    giveEffect(p);
                }
            }, 35);
        } else {
            tp(player);
            cleaner(player);
            player.getInventory().setItem(8, getLeaveDoor());
            player.getInventory().setHeldItemSlot(0);
            try {
                player.updateInventory();
            }
            catch (Exception p) {
                // empty catch block
            }
        }
    }

    @Override
    public void leaveArena(Player player) {
        APlayer ap = getAPlayer(player);
        SArena.quitcleaner(player);
        player.setScoreboard(_scoremanager.getNewScoreboard());
        if (_players.size() <= 2 && !_stopping && _etat == _ingame) {
            stop();
        }
        if (_players.size() < _minplayer && !_stopping && _etat <= _starting) {
            resetCountdown();
        }
        if (_plugin._lobby._lobbyspawn != null) {
            player.teleport(_plugin._lobby._lobbyspawn);
        } else {
            Location wo = (Location)_spawns.get(0);
            if (wo != null) {
                player.teleport(wo.getWorld().getSpawnLocation());
            }
        }
        ap.RestoreInventory();
        _players.remove(player.getName());
    }

    @Override
    public void CrashLeaveArena(Player player) {
        _players.remove(player.getName());
        if (_players.size() <= 1 && !_stopping && _etat == _ingame) {
            stop();
        }
        if (_players.size() < _minplayer && !_stopping && _etat <= _starting) {
            resetCountdown();
        }
    }

    @Override
    public void updateScore() {
        if(_players.size() <= 0)
            return;
        
        for(APlayer player2 : _players.values())
            _objective.getScore(player2.getName()).setScore(player2._score);
        
        for(APlayer player2 : _players.values())
            player2.getPlayer().setScoreboard(_objective.getScoreboard());
    }

    @Override
    public void stop() {
        _stopping = true;
        _plugin.getServer().getScheduler().cancelTask(_timer);
        for(APlayer play : _players.values()) {
            Player player = play.getPlayer();
            SArena.quitcleaner(player);
            player.setScoreboard(_scoremanager.getNewScoreboard());
            if(_plugin._lobby._lobbyspawn != null)
                player.teleport(_plugin._lobby._lobbyspawn);
            play.RestoreInventory();
        }
        _stopping = false;
        resetArena();
    }

    @Override
    public void start() {
        _etat = _ingame;
        if (_objective != null) {
            _objective.unregister();
        }
        _objective = _board.registerNewObjective(_name, "dummy");
        _objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        _objective.setDisplayName("Score");
        for(APlayer play : _players.values()) {
            Player player = play.getPlayer();
            cleaner(player);
            giveStuff(player);
            tp(player);
            updateScore();
            giveEffect(player);
        }
    }

    public void addspawn(Location loc) {
        _spawns.add(loc);
    }

    public void removespawn(String args) {
        _spawns.remove(Integer.parseInt(args));
    }

    @Override
    public void win(final Player player) {
        getGainWin(player);
        _finished = true;
        nbroadcast(ChatColor.GOLD + "#" + ChatColor.GRAY + "--------------------" + ChatColor.GOLD + "#");
        nbroadcast("" + ChatColor.GRAY);
        nbroadcast(_plugin._trad.get("Game.Arena.Message.Won").replace("[NAME]", player.getName()));
        nbroadcast("" + ChatColor.GRAY);
        nbroadcast(ChatColor.GOLD + "#" + ChatColor.GRAY + "--------------------" + ChatColor.GOLD + "#");
        _plugin.getLogger().log(Level.INFO, _plugin._trad.get("Game.Arena.Message.Won").replace("[NAME]", player.getName()));
        final int nb = (int)((double)_after * 1.5);
        final int infoxp = Bukkit.getScheduler().scheduleSyncRepeatingTask(_plugin, new Runnable() {

            @Override
            public void run() {
                if(_compteur >= nb)
                    return;
                
                Firework fw = (Firework)player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                FireworkMeta fwm = fw.getFireworkMeta();
                Random r = new Random();
                int rt = r.nextInt(4) + 1;
                FireworkEffect.Type type = FireworkEffect.Type.BALL;
                if (rt == 1) {
                    type = FireworkEffect.Type.BALL;
                }
                if (rt == 2) {
                    type = FireworkEffect.Type.BALL_LARGE;
                }
                if (rt == 3) {
                    type = FireworkEffect.Type.BURST;
                }
                if (rt == 4) {
                    type = FireworkEffect.Type.CREEPER;
                }
                if (rt == 5) {
                    type = FireworkEffect.Type.STAR;
                }
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
        }, 5, 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

            @Override
            public void run() {
                _plugin.getServer().getScheduler().cancelTask(infoxp);
                stop();
            }
        }, _after * 20);
    }

    @Override
    public Location getTp(Player player) {
        int higher = _spawns.size() - 1;
        if (_spawns.size() < 0) {
            higher = 0;
        }
        int random = (int)(Math.random() * (double)higher);
        Location loc = (Location)_spawns.get(random);
        return loc;
    }

}