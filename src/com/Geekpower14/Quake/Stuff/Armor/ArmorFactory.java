/*
 * Decompiled with CFR 0_114.
 */
package com.Geekpower14.Quake.Stuff.Armor;

public class ArmorFactory {
    public static ArmorBasic getInstance(String hatName, String path) {
        if (hatName.equals("ArmorRED")) {
            return new ArmorRED();
        }
        if (hatName.equals("ArmorCustomFile")) {
            return new ArmorCustomFile(path);
        }
        return null;
    }
}

