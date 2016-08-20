package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CreateCommand implements BasicCommand {
    private final Quake _plugin;

    public CreateCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Please type a name and a type for the arena !");
                return true;
            }
            if (_plugin._am.exist(args[0])) {
                player.sendMessage(ChatColor.RED + "Arena " + args[0] + " already exist !");
                return true;
            }
            if (!args[1].equals("Team") && !args[1].equals("Solo")) {
                player.sendMessage(ChatColor.RED + "Type " + args[1] + " is not valid (Solo or Team) !");
                return true;
            }
            _plugin._am.createArena(args[0], args[1]);
            player.sendMessage(ChatColor.YELLOW + "Arena " + args[0] + " build with success");
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake create [Arena name] [Type] - Create an arena.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }
}

