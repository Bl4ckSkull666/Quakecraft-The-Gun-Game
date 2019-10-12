package com.Geekpower14.Quake.Versions;


import com.Geekpower14.Quake.Arena.Arena;
import net.minecraft.server.v1_14_R1.PacketPlayInClientCommand;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bl4ckSkull666
 */
public class R1_14_1 {
    public static void Respawn(Player p) throws Exception {
        PacketPlayInClientCommand ppicc = new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN);
        ((CraftPlayer)p).getHandle().playerConnection.a(ppicc);
    }
}
