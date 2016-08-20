/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SetMapCommand implements BasicCommand {
    private final Quake _plugin;

    public SetMapCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            Arena arena = null;
            if (_plugin._am.exist(args[0])) {
                arena = _plugin._am.getArenabyName(args[0]);
            } else if (args[0].matches("^\\d*$")) {
                arena = _plugin._am.getArenabyID(Integer.parseInt(args[0]));
            }
            if (arena == null) {
                player.sendMessage(ChatColor.RED + "Please type a good arena name ! !");
                return true;
            }
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Please type a name !");
                return true;
            }
            arena.setmap(args[1]);
            arena.saveConfig();
            player.sendMessage(ChatColor.GREEN + "Set the map with success !");
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake setmap [Arena] [Name] - Set display name in the lobby.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }
}

