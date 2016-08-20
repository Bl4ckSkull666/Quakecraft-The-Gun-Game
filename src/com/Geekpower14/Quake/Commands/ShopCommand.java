/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import org.bukkit.entity.Player;

public class ShopCommand implements BasicCommand {
    private final Quake _plugin;

    public ShopCommand(Quake pl) {
        _plugin = pl;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (Quake.hasPermission(player, getPermission())) {
            _plugin._shop.getMainShop(player);
            _plugin._imm.show(player);
        } else {
            player.sendMessage(_plugin._trad.get("NoPermission"));
        }
        return false;
    }

    @Override
    public String help(Player p) {
        if (Quake.hasPermission(p, getPermission())) {
            return "/quake shop - Open the shop.";
        }
        return "";
    }

    @Override
    public String getPermission() {
        return "Quake.player";
    }
}

