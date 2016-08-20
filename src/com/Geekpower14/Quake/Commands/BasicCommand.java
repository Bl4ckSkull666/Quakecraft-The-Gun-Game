/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package com.Geekpower14.Quake.Commands;

import org.bukkit.entity.Player;

public interface BasicCommand {
    public boolean onCommand(Player var1, String[] var2);

    public String help(Player var1);

    public String getPermission();
}

