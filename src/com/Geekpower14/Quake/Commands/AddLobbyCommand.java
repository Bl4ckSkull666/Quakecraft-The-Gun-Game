package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.entity.Player;

public class AddLobbyCommand
implements BasicCommand {
    private final Quake _plugin;

    public AddLobbyCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            _plugin._lobby.addLobby(player);
            _plugin._lobby.saveconfig();
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake addlobby - Add a sign wall lobby.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.lobby";
    }
}

