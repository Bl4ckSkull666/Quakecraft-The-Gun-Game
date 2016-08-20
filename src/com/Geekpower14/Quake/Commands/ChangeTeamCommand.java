package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Arena.SArena;
import com.Geekpower14.Quake.Arena.TArena;
import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChangeTeamCommand implements BasicCommand {
    private final Quake _plugin;

    public ChangeTeamCommand(Quake pl) {
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
            if (arena instanceof SArena) {
                player.sendMessage(_plugin._trad.get("Game.Arena.error.NotInTeamGame"));
                return true;
            }
            if (arena instanceof TArena) {
                TArena ta = (TArena)arena;
                if (args.length >= 1) {
                    ta.changeTeam(player, args[0]);
                } else {
                    player.sendMessage(ChatColor.RED + "Please type a team name !");
                }
            }
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake team [Name] - Change your team.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.ChangeTeam";
    }
}

