/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RemoveLobbyCommand implements BasicCommand {
    private final Quake _plugin;

    public RemoveLobbyCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            if (args.length != 1) {
                player.sendMessage(ChatColor.RED + "Please type a good number of lobby !");
                return true;
            }
            int lobby = Integer.valueOf(args[0]);
            _plugin._lobby.removeLobby("lobby" + (lobby - 1));
            player.sendMessage(ChatColor.YELLOW + "Lobby number : " + (lobby - 1) + " removed with success");
            _plugin._lobby.saveconfig();
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake removelobby [ID of the lobby] - Remove a lobby wall.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.lobby";
    }
}

