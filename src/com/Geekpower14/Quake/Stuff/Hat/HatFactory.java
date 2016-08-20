/*
 * Decompiled with CFR 0_114.
 */
package com.Geekpower14.Quake.Stuff.Hat;

import com.Geekpower14.Quake.Stuff.Hat.HatBasic;
import com.Geekpower14.Quake.Stuff.Hat.HatCustomFile;
import com.Geekpower14.Quake.Stuff.Hat.HatJACK;

public class HatFactory {
    public static HatBasic getInstance(String hatName, String path) {
        if (hatName.equals("HatJACK")) {
            return new HatJACK();
        }
        if (hatName.equals("HatCustomFile")) {
            return new HatCustomFile(path);
        }
        return null;
    }
}

