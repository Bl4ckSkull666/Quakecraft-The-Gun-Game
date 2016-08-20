package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class AddSpawnCommand implements BasicCommand {
    private final Quake _plugin;

    public AddSpawnCommand(Quake pl) {
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
            if (arena instanceof SArena) {
                SArena sa = (SArena)arena;
                sa.addspawn(player.getLocation());
                player.sendMessage(ChatColor.GREEN + "Spawn number " + (sa._spawns.size() - 1) + " defined !");
            } else if (arena instanceof TArena) {
                TArena ta = (TArena)arena;
                if (args.length >= 2) {
                    int nb = ta.addspawn(player.getLocation(), args[1]);
                    player.sendMessage(ChatColor.GREEN + "Spawn number " + (nb - 1) + " defined !");
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
        if(Quake.hasPermission(p, getPermission()))
            return "/quake addspawn [Arena] [TEAM|Only for team arena] - Add spawn to an arena.";
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }
}

