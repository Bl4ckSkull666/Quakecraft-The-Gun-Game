/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MoneyCommand implements BasicCommand {
    private final Quake _plugin;

    public MoneyCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            int new_ = 0;
            if (args.length < 2) {
                player.sendMessage(ChatColor.RED + "Please write good command.");
                player.sendMessage(help(player));
                return true;
            }
            if (args[0].equalsIgnoreCase("get")) {
                Player lol = Bukkit.getPlayer((String)args[1]);
                new_ = _plugin._eco.getPlayerMoney(lol);
                player.sendMessage(ChatColor.GREEN + lol.getName() + " have " + new_);
                return true;
            }
            if (args.length < 3) {
                player.sendMessage(ChatColor.RED + "Please write good command.");
                player.sendMessage(help(player));
                return true;
            }
            int amount = Integer.valueOf(args[2]);
            if (args[0].equalsIgnoreCase("set")) {
                Player lol = Bukkit.getPlayer((String)args[1]);
                _plugin._eco.setPlayerMoney(Bukkit.getPlayer((String)args[1]), amount);
                new_ = _plugin._eco.getPlayerMoney(lol);
                player.sendMessage(ChatColor.GREEN + lol.getName() + " have now " + new_);
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                Player lol = Bukkit.getPlayer((String)args[1]);
                _plugin._eco.addPlayerMoney(Bukkit.getPlayer((String)args[1]), amount);
                new_ = _plugin._eco.getPlayerMoney(lol);
                player.sendMessage(ChatColor.GREEN + lol.getName() + " have now " + new_);
                return true;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                Player lol = Bukkit.getPlayer((String)args[1]);
                _plugin._eco.soustrairePlayerMoney(Bukkit.getPlayer((String)args[1]), amount);
                new_ = _plugin._eco.getPlayerMoney(lol);
                player.sendMessage(ChatColor.GREEN + lol.getName() + " have now " + new_);
                return true;
            }
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return true;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake money [set/add/remove/get] [Player] [Amount] - Set/Add/Remove/Get money of a player.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.edit";
    }
}

