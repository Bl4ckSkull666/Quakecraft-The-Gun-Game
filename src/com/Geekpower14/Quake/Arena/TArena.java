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
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

public class TArena extends Arena {
    public List<ATeam> _Teams = new ArrayList<>();

    public TArena(Quake pl, String n, int ID) {
        super(pl, n, ID);
        _Teams.add(new ATeam(pl, this, "Blue", ChatColor.BLUE, Color.BLUE));
        _Teams.add(new ATeam(pl, this, "Red", ChatColor.RED, Color.RED));
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
        ArrayList<Location> s = new ArrayList<>();
        for (String spawn : config.getStringList("Spawns_B")) {
            s.add(str2loc(spawn));
        }
        _spawns_B = s;
        ArrayList<Location> ss = new ArrayList<>();
        for (String spawn2 : config.getStringList("Spawns_R")) {
            ss.add(str2loc(spawn2));
        }
        _spawns_R = ss;
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
        ArrayList<PotionEffect> l = new ArrayList<PotionEffect>();
        for (String popo : config.getStringList("Potions")) {
            l.add(StrToPo(popo));
        }
        _potions = l;
        _plugin.getLogger().info("load de " + _name);
        return true;
    }

    @Override
    public Boolean testConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        setDefaultConfig((FileConfiguration)config, "Version", Quake._version);
        setDefaultConfig((FileConfiguration)config, "Name", _name);
        setDefaultConfig((FileConfiguration)config, "Type", "Team");
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
        setDefaultConfig((FileConfiguration)config, "Spawns_B", new ArrayList());
        setDefaultConfig((FileConfiguration)config, "Spawns_R", new ArrayList());
        try {
            config.save(new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        }
        catch (IOException e) {
            _plugin.getLogger().warning("save default de " + _name + " impossible !");
            disable();
        }
        return true;
    }

    @Override
    public Boolean saveConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/arenas/" + _name + ".yml"));
        _plugin.getLogger().info("save de " + _name);
        config.set("Version", Quake._version);
        config.set("Name", _name);
        config.set("Type", "Team");
        config.set("Map", _map);
        config.set("Active", _Active);
        config.set("Natural-Death", _NaturalDeath);
        config.set("Auto-Respawn", _Auto_Respawn);
        config.set("VIP", _VIP);
        config.set("Goal", _goal);
        config.set("Time-Before", _starting);
        config.set("Time-After", _after);
        config.set("Coins-per-Win", _Coins_Win);
        config.set("Coins-per-Kill", _Coins_Kill);
        config.set("VIP-Multiplicator", _VIP_M);
        config.set("VIP+-Multiplicator", _VIPP_M);
        config.set("Global-Chat", _Global_Chat);
        ArrayList<String> l = new ArrayList<>();
        for(PotionEffect popo : _potions) {
            l.add(PoToStr(popo));
        }
        config.set("Potions", l);
        config.set("MaxPlayers", _maxplayer);
        config.set("MinPlayers", _minplayer);
        config.set("Sneak", _Sneak);
        ArrayList<String> s = new ArrayList<>();
        for(Location loc : _spawns_B) {
            s.add(loc2str(loc));
        }
        config.set("Spawns_B", s);
        ArrayList<String> ss = new ArrayList<>();
        for(Location loc2 : _spawns_R) {
            ss.add(loc2str(loc2));
        }
        config.set("Spawns_R", ss);
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
        if (_spawns_B.isEmpty() || _spawns_R.isEmpty()) {
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
        addPlayerToTeam(player);
        if (_players.size() >= _minplayer && _etat == _pregame) {
            startDelayed();
        }
        if (_etat > _pregame && Quake.hasPermission(player, "Quake.JoinInGame")) {
            tp(player);
            cleaner(player);
            updateScore();
            ap.setInvincible(30L);
            final Player p = player;
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

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
        TArena.quitcleaner(player);
        player.setScoreboard(_scoremanager.getNewScoreboard());
        if (!(_Teams.get(0).getSize() > 1 && _Teams.get(1).getSize() > 1 || _stopping || _etat != _ingame)) {
            stop();
        }
        if (_players.size() < _minplayer && !_stopping && _etat <= _starting) {
            resetCountdown();
        }
        if (_plugin._lobby._lobbyspawn != null) {
            player.teleport(_plugin._lobby._lobbyspawn);
        } else {
            Location wo = (Location)_spawns_B.get(0);
            if (wo != null) {
                player.teleport(wo.getWorld().getSpawnLocation());
            }
        }
        ap.RestoreInventory();
        getTeam(player).removePlayer(player);
        _players.remove(player.getName());
    }

    @Override
    public void CrashLeaveArena(Player player) {
        _players.remove(player.getName());
        getTeam(player).removePlayer(player);
        if (_players.size() <= 1 && !_stopping && _etat == _ingame) {
            stop();
        }
        if (_players.size() < _minplayer && !_stopping && _etat <= _starting) {
            resetCountdown();
        }
    }

    @Override
    public void updateScore() {
        for (APlayer player2 : _players.values())
            _objective.getScore(player2.getName()).setScore(player2._score);
        for (ATeam t : _Teams)
            _obj_side.getScore(t.getColor() + t.getName()).setScore(t.getScore());
        for (APlayer player2 : _players.values())
            player2.getPlayer().setScoreboard(_objective.getScoreboard());
    }

    @Override
    public void shotplayer(final Player shooter, Player victim, FireworkEffect effect) {
        APlayer ashooter = getAPlayer(shooter);
        APlayer avictim = getAPlayer(victim);
        if (victim != shooter && !avictim.isInvincible() && !isSameTeam(victim, shooter)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    getGainKill(shooter);
                }
            }, 2);
            try {
                _fw.playFirework(victim.getWorld(), victim.getLocation(), effect);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            kill(victim);
            broadcast(_plugin._trad.get("Game.Arena.Message.Shot").replace("[SHOOTER]", shooter.getName()).replace("[KILLED]", victim.getName()));
            ashooter._score++;
            getTeam(ashooter).addScore(1);
            if (getTeam(ashooter).getScore() >= _goal) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {

                    @Override
                    public void run() {
                        win(getTeam(shooter));
                    }
                }, 2);
            }
            updateScore();
        }
    }

    @Override
    public void resetArena() {
        _etat = _pregame;
        _full = false;
        _players.clear();
        for (ATeam at : _Teams) {
            at.reset();
        }
        _stopping = false;
        _finished = false;
        _compteur = 0;
    }

    @Override
    public void stop() {
        _stopping = true;
        _plugin.getServer().getScheduler().cancelTask(_timer);
        for (APlayer play : _players.values()) {
            Player player = play.getPlayer();
            getTeam(player).removePlayer(player);
            TArena.quitcleaner(player);
            player.setScoreboard(_scoremanager.getNewScoreboard());
            if(_plugin._lobby._lobbyspawn != null) {
                player.teleport(_plugin._lobby._lobbyspawn);
            }
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
            _objective = null;
        }
        if (_obj_side != null) {
            _obj_side.unregister();
            _obj_side = null;
        }
        for (APlayer aPlayer : _players.values()) {
        }
        _obj_side = _board.registerNewObjective(String.valueOf(_name) + "_Team", "dummy");
        _obj_side.setDisplaySlot(DisplaySlot.SIDEBAR);
        _obj_side.setDisplayName("Score");
        _objective = _board.registerNewObjective(String.valueOf(_name) + "_Perso", "dummy");
        _objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        _objective.setDisplayName("Score_P");
        for(APlayer play : _players.values()) {
            Player player = play.getPlayer();
            cleaner(player);
            giveStuff(player);
            tp(player);
            giveEffect(player);
        }
        updateScore();
    }

    @Override
    public void giveStuff(Player player) {
        APlayer ap = getAPlayer(player);
        ap.getKits();
        ap.giveHat();
        ap.giveItem();
        getTeam(player).giveChestplate(player);
        player.getInventory().setItem(8, getLeaveDoor());
        player.getInventory().setHeldItemSlot(0);
    }

    private void addPlayerToTeam(Player p) {
        ATeam result = _Teams.get(0);
        for (ATeam t : _Teams) {
            if (t.getSize() >= result.getSize()) continue;
            result = t;
        }
        result.addPlayer(p);
        broadcast(_plugin._trad.get("Game.Arena.Message.Team-Join").replace("[TEAM]", result.getName()).replace("[PLAYER]", p.getName()).replace("[NUMBER]", "" + _players.size()).replace("[MAX]", "" + _maxplayer));
    }

    public void changeTeam(Player p, String steam) {
        ATeam nteam = getTeam(steam);
        ATeam oteam = getTeam(p);
        if (nteam == null) {
            p.sendMessage(_plugin._trad.get("Game.Arena.error.BadTeamName"));
            return;
        }
        if (_etat == _ingame && !Quake.hasPermission(p, "Quake.ChangeTeamInGame")) {
            p.sendMessage(_plugin._trad.get("Game.Arena.error.ChangeTeamInGame"));
            return;
        }
        oteam.removePlayer(p);
        if (_etat == _ingame) {
            kill(p);
        }
        nteam.addPlayer(p);
        p.sendMessage(_plugin._trad.get("Game.Arena.Message.ChangeTeam").replace("[TEAM]", nteam.getColor() + nteam.getName()));
    }

    public ATeam getTeam(Player p) {
        for(ATeam t : _Teams) {
            if (!t.hasPlayer(p))
                continue;
            return t;
        }
        return _Teams.get(0);
    }

    public ATeam getTeam(String name) {
        for (ATeam t : _Teams) {
            if (!t.getName().equalsIgnoreCase(name))
                continue;
            return t;
        }
        return null;
    }

    public ATeam getTeam(APlayer p) {
        return getTeam(p.getPlayer());
    }

    public Boolean isSameTeam(Player p, Player b) {
        return getTeam(p).hasPlayer(b);
    }

    public int addspawn(Location loc, String team) {
        if (team.equalsIgnoreCase("Blue")) {
            _spawns_B.add(loc);
            return _spawns_B.size();
        }
        if (team.equalsIgnoreCase("Red")) {
            _spawns_R.add(loc);
            return _spawns_R.size();
        }
        return 0;
    }

    public int removespawn(String args, String team) {
        if (team.equalsIgnoreCase("Blue")) {
            _spawns_B.remove(Integer.parseInt(args));
            return _spawns_B.size();
        }
        if (team.equalsIgnoreCase("Red")) {
            _spawns_R.remove(Integer.parseInt(args));
            return _spawns_R.size();
        }
        return 0;
    }

    public void win(ATeam team) {
        _finished = true;
        nbroadcast(ChatColor.GOLD + "#" + ChatColor.GRAY + "--------------------" + ChatColor.GOLD + "#");
        nbroadcast("" + ChatColor.GRAY);
        nbroadcast(_plugin._trad.get("Game.Arena.Message.Team-Won").replace("[TEAM]", team.getName()));
        nbroadcast("" + ChatColor.GRAY);
        nbroadcast(ChatColor.GOLD + "#" + ChatColor.GRAY + "--------------------" + ChatColor.GOLD + "#");
        _plugin.getLogger().log(Level.INFO, _plugin._trad.get("Game.Arena.Message.Won").replace("[TEAM]", team.getName()));
        for(String p : team.getPlayers()) {
            Player pl = Bukkit.getPlayer(p);
            if(pl != null)
                win(pl);
        }
        
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                stop();
            }
        }, _after * 20);
    }

    @Override
    public void win(final Player player) {
        getGainWin(player);
        final int nb = (int)((double)_after*1.5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {

            @Override
            public void run() {
                if(_compteur <= nb)
                    Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, this, 5);
                
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
        }, 5);
    }

    @Override
    public Location getTp(Player player) {
        List spawns = new ArrayList();
        spawns = getTeam(player).getName().equalsIgnoreCase("Blue") ? _spawns_B : _spawns_R;
        int higher = spawns.size() - 1;
        if (spawns.size() < 0) {
            higher = 0;
        }
        Random t = new Random();
        int random = t.nextInt(higher);
        Location loc = (Location)spawns.get(random);
        return loc;
    }

}

