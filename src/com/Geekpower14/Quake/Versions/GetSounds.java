/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Geekpower14.Quake.Versions;

import com.Geekpower14.Quake.Quake;
import org.bukkit.Sound;

/**
 *
 * @author Bl4ckSkull666
 */
public class GetSounds {
    private static Sound _pling = null;
    public static Sound GetNotePling() {
        if(_pling == null) {
            if(Quake.getPlugin()._config.isString("sounds.pling")) {
                String search = Quake.getPlugin()._config.getString("sounds.pling");
                for(Sound s: Sound.values()) {
                    if(s.name().equalsIgnoreCase(search)) {
                        _pling = s;
                        break;
                    }
                }
            }
            
            if(_pling == null) {
                for(Sound s: Sound.values()) {
                    if(s.name().equalsIgnoreCase("NOTE_PLING") || 
                            s.name().equalsIgnoreCase("BLOCK_NOTE_PLING") || 
                            s.name().equalsIgnoreCase("BLOCK_NOTE_BLOCK_PLING")) {
                        _pling = s;
                        break;
                    }
                }
            }
        }
        return _pling;
    }
}
