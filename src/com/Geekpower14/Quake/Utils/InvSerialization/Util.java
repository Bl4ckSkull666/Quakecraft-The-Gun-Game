package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Material;

public class Util {
    protected Util() {
    }

    public static boolean isNum(String s) {
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isLeatherArmor(Material material) {
        return material == Material.LEATHER_HELMET || material == Material.LEATHER_CHESTPLATE || material == Material.LEATHER_LEGGINGS || material == Material.LEATHER_BOOTS;
    }

    public static boolean keyFound(String[] array, String key) {
        for (String s : array) {
            if (!s.equalsIgnoreCase(key)) continue;
        }
        return false;
    }
}

