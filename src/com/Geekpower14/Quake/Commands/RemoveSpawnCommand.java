package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveSpawnCommand implements BasicCommand {
    private final Quake _plugin;

    public RemoveSpawnCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            Arena arena = null;
            if (_plugin._am.exist(args[0])) {
                arena = _plugin._am.getArenabyName(args[0]);
            } else if (args[0].matches("^\\d*$")) {
                arena = _plugin._am.getArenabyID(Integer.valueOf(args[0]));
            }
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Please type a good arena name ! !");
                return true;
            }
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Please type a number !");
                return true;
            }
            if (arena instanceof SArena) {
                SArena sa = (SArena)arena;
                sa.removespawn(args[1]);
                player.sendMessage(ChatColor.GREEN + "Spawn number " + (sa._spawns.size() - 1) + " removed !");
            } else if (arena instanceof TArena) {
                TArena ta = (TArena)arena;
                if (args.length >= 3) {
                    int nb = ta.removespawn(args[1], args[2]);
                    player.sendMessage(ChatColor.GREEN + "Spawn number " + (nb - 1) + " removed !");
                } else {
                    player.sendMessage(ChatColor.RED + "Please type a team name ! !");
                }
            }
            arena.saveConfig();
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake removespawn [Arena] [Number] - Remove a spawn of the arena.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }
}

