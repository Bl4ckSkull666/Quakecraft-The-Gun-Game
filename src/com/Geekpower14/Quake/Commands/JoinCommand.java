package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinCommand implements BasicCommand {
    private final Quake _plugin;

    public JoinCommand(Quake pl) {
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
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Please type a number !");
                return true;
            }
            if (arena.isingame(player)) {
                player.sendMessage(ChatColor.RED + "You are already in game !");
                return true;
            }
            arena.joinArena(player);
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake join [Arena] - Join an arena.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.player";
    }
}

