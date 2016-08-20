package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.entity.Player;

public class LeaveCommand implements BasicCommand {
    private final Quake _plugin;

    public LeaveCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            Arena arena = _plugin._am.getArenabyPlayer(player);
            if (arena == null) {
                player.sendMessage(_plugin._trad.get("Game.Arena.Message.NotInAGame"));
                return true;
            }
            arena.leaveArena(player);
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return false;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake leave - Leave an arena.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.player";
    }
}

