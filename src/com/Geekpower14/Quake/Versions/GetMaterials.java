/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Geekpower14.Quake.Versions;

import com.Geekpower14.Quake.Quake;
import org.bukkit.Material;

/**
 *
 * @author Bl4ckSkull666
 */
public class GetMaterials {
    private static Material _sign = null;
    private static Material _bed = null;
    private static Material _hoeDiamand = null;
    private static Material _leatherChestPlate = null;
    private static Material _jackOLantern = null;
    private static Material _emerald = null;
    
    public static Material GetSign() {
        if(_sign == null) {
            if(Quake.getPlugin()._config.isString("materials.sign")) {
                String mat = Quake.getPlugin()._config.getString("materials.sign");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _sign = m;
                        break;
                    }
                }
            }
            
            if(_sign == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("SIGN") || m.name().equalsIgnoreCase("OAK_SIGN")) {
                        _sign = m;
                        break;
                    }
                }
            }
        }
        return _sign;
    }
    
    public static Material GetBed() {
        if(_bed == null) {
            if(Quake.getPlugin()._config.isString("materials.bed")) {
                String mat = Quake.getPlugin()._config.getString("materials.bed");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _bed = m;
                        break;
                    }
                }
            }
            
            if(_bed == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("BED") || m.name().equalsIgnoreCase("RED_BED")) {
                        _bed = m;
                        break;
                    }
                }
            }
        }
        return _bed;
    }
    
    public static Material GetDiamandHoe() {
        if(_hoeDiamand == null) {
            if(Quake.getPlugin()._config.isString("materials.diamond_hoe")) {
                String mat = Quake.getPlugin()._config.getString("materials.diamond_hoe");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _hoeDiamand = m;
                        break;
                    }
                }
            }
            
            if(_hoeDiamand == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("DIAMOND_HOE")) {
                        _hoeDiamand = m;
                        break;
                    }
                }
            }
        }
        return _hoeDiamand;
    }
    
    public static Material GetLeatherChestplate() {
        if(_leatherChestPlate == null) {
            if(Quake.getPlugin()._config.isString("materials.leather_chestplate")) {
                String mat = Quake.getPlugin()._config.getString("materials.leather_chestplate");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _leatherChestPlate = m;
                        break;
                    }
                }
            }
            
            if(_leatherChestPlate == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("LEATHER_CHESTPLATE")) {
                        _leatherChestPlate = m;
                        break;
                    }
                }
            }
        }
        return _leatherChestPlate;
    }
    
    public static Material GetJackOLantern() {
        if(_jackOLantern == null) {
            if(Quake.getPlugin()._config.isString("materials.jack_o_lantern")) {
                String mat = Quake.getPlugin()._config.getString("materials.jack_o_lantern");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _jackOLantern = m;
                        break;
                    }
                }
            }
            
            if(_jackOLantern == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("JACK_O_LANTERN")) {
                        _jackOLantern = m;
                        break;
                    }
                }
            }
        }
        return _jackOLantern;
    }
    
    public static Material GetEmerald() {
        if(_emerald == null) {
            if(Quake.getPlugin()._config.isString("materials.emerald")) {
                String mat = Quake.getPlugin()._config.getString("materials.emerald");
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase(mat)) {
                        _emerald = m;
                        break;
                    }
                }
            }
            
            if(_emerald == null) {
                for(Material m: Material.values()) {
                    if(m.name().equalsIgnoreCase("EMERALD")) {
                        _emerald = m;
                        break;
                    }
                }
            }
        }
        return _emerald;
    }
}
