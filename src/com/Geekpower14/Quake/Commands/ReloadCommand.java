package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ReloadCommand implements BasicCommand {
    private final Quake _plugin;

    public ReloadCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            _plugin.loadConfig();
            _plugin._stuff.reloadConfig();
            _plugin._lobby.loadconfig();
            _plugin._am.reloadArenas();
            _plugin._trad.reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Plugin reloaded !");
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake reload - Reload config.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.admin";
    }
}

