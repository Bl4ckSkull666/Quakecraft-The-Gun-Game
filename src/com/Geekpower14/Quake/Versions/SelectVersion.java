/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Geekpower14.Quake.Versions;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author Bl4ckSkull666
 */
public class SelectVersion {
    private static String getVersion() {
        String p = Bukkit.getServer().getClass().getPackage().getName();
        return p.substring(p.lastIndexOf('.') + 1); 
    }
    
    public static void Respawn(Player p) throws Exception {
        switch(getVersion()) {
            case "v1_14_R1":
                R1_14_1.Respawn(p);
                break;
        }
    }
}
