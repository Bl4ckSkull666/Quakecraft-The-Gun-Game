/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.entity.Player;

public class UtilsCommand implements BasicCommand {
    private final Quake _plugin;
    private String _cmd;

    public UtilsCommand(Quake pl, String cmd) {
        _plugin = pl;
        _cmd = cmd;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            if (_cmd.equals("kill")) {
                player.setHealth(0.0);
            }
            if (_cmd.equals("add")) {
                Arena aren = _plugin._am.getArenabyPlayer(player);
                APlayer ap = aren.getAPlayer(player);
                ap._score++;
                aren.updateScore();
            }
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return false;
    }

    @Override
    public String help(Player p) {
        if(Quake.hasPermission(p, getPermission())) {
            return "/quake " + _cmd + " - Do something.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.admin";
    }
}

