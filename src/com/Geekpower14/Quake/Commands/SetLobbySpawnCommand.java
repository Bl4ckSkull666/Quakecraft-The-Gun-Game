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

public class SetLobbySpawnCommand implements BasicCommand {
    private Quake _plugin;

    public SetLobbySpawnCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            _plugin._lobby.setspawn(player);
            player.sendMessage(ChatColor.YELLOW + "Lobby's spawn define with success !");
            _plugin._lobby.saveconfig();
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake setlobbyspawn - Set the spawn of the lobby.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.lobby";
    }
}

