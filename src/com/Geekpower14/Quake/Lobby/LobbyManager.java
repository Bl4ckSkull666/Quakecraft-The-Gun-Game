package com.Geekpower14.Quake.Lobby;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Quake;
import com.sk89q.worldedit.bukkit.selections.Selection;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class LobbyManager {
    public Quake _plugin;
    public HashMap<String, Location> _locmin = new HashMap();
    public HashMap<String, Location> _locmax = new HashMap();
    public Location _lobbyspawn = null;
    public List<Lobby> _LOBBYS = new ArrayList<>();
    public List<Lobby_Sign> _LOBBYS_SIGN = new ArrayList<>();

    public LobbyManager(Quake pl) {
        _plugin = pl;
        loadconfig();
    }

    public final void loadconfig() {
        String nom;
        File fichier_config = new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)fichier_config);
        _lobbyspawn = str2loc(config.getString("LobbySpawn"));
        int nombre = config.getInt("Nombre");
        int i = 0;
        while (i < nombre) {
            nom = config.getString("min.lobby" + i);
            _locmin.put("lobby" + i, str2loc(nom));
            ++i;
        }
        i = 0;
        while (i < nombre) {
            nom = config.getString("max.lobby" + i);
            _locmax.put("lobby" + i, str2loc(nom));
            ++i;
        }
        _plugin.getLogger().info("load des Lobby ");
    }

    public void saveconfig() {
        Location loc;
        File fichier_config = new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(fichier_config);
        if(_lobbyspawn != null) {
            config.set("LobbySpawn", (String.valueOf(_lobbyspawn.getWorld().getName()) + ", " + _lobbyspawn.getX() + ", " + _lobbyspawn.getY() + ", " + _lobbyspawn.getZ() + ", " + _lobbyspawn.getYaw() + ", " + _lobbyspawn.getPitch()));
        }
        
        config.set("Nombre", _locmin.size());
        for(String l2 : _locmin.keySet()) {
            loc = _locmin.get(l2);
            config.set("min." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        for(String l2 : _locmax.keySet()) {
            loc = _locmax.get(l2);
            config.set("max." + l2, (String.valueOf(loc.getWorld().getName()) + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch()));
        }
        
        try {
            config.save(new File(_plugin.getDataFolder(), String.valueOf(File.separator) + "lobby.yml"));
            _plugin.saveConfig();
        } catch (IOException l) {
            // empty catch block
        }
        _plugin.getLogger().info("save des Lobby ");
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

    public void setspawn(Player player) {
        _lobbyspawn = player.getLocation();
    }

    public Boolean removeLobby(String lobby) {
        _locmin.remove(lobby);
        _locmax.remove(lobby);
        HashMap<String, Location> tempmin = new HashMap<>();
        HashMap<String, Location> tempmax = new HashMap<>();
        int i = 0;
        for (String s : _locmin.keySet()) {
            tempmin.put("lobby" + i, _locmin.get(s));
            tempmax.put("lobby" + i, _locmax.get(s));
            ++i;
        }
        _locmin = tempmin;
        _locmax = tempmax;
        return true;
    }

    public Boolean addLobby(Player player) {
        String lobby = "lobby" + _locmin.size();
        Selection selection = _plugin._worldEdit.getSelection(player);
        if (selection != null) {
            //World world = selection.getWorld();
            Location un = selection.getMinimumPoint();
            Location deux = selection.getMaximumPoint();
            /*String direction = "";
            if (un.getX() == deux.getX()) {
                direction = "Z";
            } else if (un.getZ() == deux.getZ()) {
                direction = "X";
            } else {
                _plugin.log.warning("Pas m\u00eame X ou Z pour : " + lobby);
                return false;
            }*/
            Location tempmin = un;
            Location tempmax = deux;
            _locmin.put(lobby, tempmin);
            _locmax.put(lobby, tempmax);
            player.sendMessage(ChatColor.YELLOW + "Lobby " + _locmin.size() + " cr\u00e9e avec succ\u00e9s");
            initsign();
            return true;
        }
        player.sendMessage(ChatColor.RED + "Veuillez faire une s\u00e9lection d'abord !");
        return false;
    }

    public Boolean initsign() {
        int index = 0;
        int i = 0;
        while(i < _locmin.size()) {
            int x;
            int y;
            Arena arena;
            String lobby = "lobby" + i;
            Location min = _locmin.get(lobby);
            Location max = _locmax.get(lobby);
            int xmax = 0;
            int xmin = 0;
            int z = 0;
            World w = min.getWorld();
            if (w == null) {
                return false;
            }
            if (min.getBlockX() == max.getBlockX()) {
                xmax = max.getBlockZ();
                xmin = min.getBlockZ();
                z = min.getBlockX();
                x = xmin;
                while (x <= xmax) {
                    y = min.getBlockY();
                    while (y <= max.getBlockY()) {
                        arena = _plugin._am.getArenabyID(index);
                        if (arena != null) {
                            updateSign(w, z, y, x, arena);
                        } else {
                            clearSign(w, z, y, x);
                        }
                        ++index;
                        ++y;
                    }
                    ++x;
                }
            } else if (min.getBlockZ() == max.getBlockZ()) {
                xmax = max.getBlockX();
                xmin = min.getBlockX();
                z = min.getBlockZ();
                x = xmin;
                while (x <= xmax) {
                    y = max.getBlockY();
                    while (y >= min.getBlockY()) {
                        arena = _plugin._am.getArenabyID(index);
                        if (arena != null) {
                            updateSign(w, x, y, z, arena);
                        } else {
                            clearSign(w, x, y, z);
                        }
                        ++index;
                        --y;
                    }
                    ++x;
                }
            }
            ++i;
        }
        return true;
    }

    public Boolean isinLobby(Location loc) {
        int oui = _locmin.size();
        int i = 0;
        while (i < _locmin.size()) {
            String lobby = "lobby" + i;
            Location min = _locmin.get(lobby);
            Location max = _locmax.get(lobby);
            if (min.getWorld() != loc.getWorld()) {
                --oui;
            } else if (loc.getX() < min.getX()) {
                --oui;
            } else if (loc.getX() > max.getX()) {
                --oui;
            } else if (loc.getZ() < min.getZ()) {
                --oui;
            } else if (loc.getZ() > max.getZ()) {
                --oui;
            } else if (loc.getY() < min.getY()) {
                --oui;
            } else if (loc.getY() > max.getY()) {
                --oui;
            }
            ++i;
        }
        if (oui <= 0) {
            return false;
        }
        return true;
    }

    public void updateSign(World w, int x, int y, int z, Arena arena) {
        Location bloc = new Location(w, (double)x, (double)y, (double)z);
        if(bloc.getBlock() == null || bloc.getBlock().getType().equals(Material.AIR))
            return;

        Block block = bloc.getBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            String ligne0 = arena._etat <= arena._pregame ? (arena.getplayers() == arena._maxplayer ? ChatColor.DARK_PURPLE + "[FULL]" : (arena._VIP ? ChatColor.AQUA + "[VIP]" : (arena._etat <= arena._starting ? ChatColor.GOLD + "[Starting]" : ChatColor.GREEN + "[Join]"))) : ChatColor.RED + "[InGame]";
            String tmp = "";
            if (arena instanceof SArena) {
                tmp = "S-";
            }
            if (arena instanceof TArena) {
                tmp = "T-";
            }
            String ligne1 = tmp + arena._ID;
            String ligne2 = (arena.getplayers() >= (arena._maxplayer * 0.75))? "" + ChatColor.RED + arena.getplayers() + "/" + arena._maxplayer : "" + arena.getplayers() + "/" + arena._maxplayer;
            String ligne3 = arena._map;
            sign.setLine(0, ligne0);
            sign.setLine(1, ligne1);
            sign.setLine(2, ligne2);
            sign.setLine(3, ligne3);
            sign.update(true);
        }
    }

    public void clearSign(World w, int x, int y, int z) {
        Location bloc = new Location(w, (double)x, (double)y, (double)z);
        Block block = bloc.getBlock();
        if (block.getState() instanceof Sign) {
            Sign sign = (Sign)block.getState();
            sign.setLine(0, ChatColor.GRAY + "[" + ChatColor.RED + "Quake" + ChatColor.GRAY + "]");
            sign.setLine(1, "");
            sign.setLine(2, ChatColor.GRAY + "Offline");
            sign.setLine(3, "");
            sign.update(true);
        }
    }
}

