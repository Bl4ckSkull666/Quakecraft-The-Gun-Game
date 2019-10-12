package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.json.JSONException;
import org.json.JSONObject;

public class WolfSerialization {
    /*protected WolfSerialization() {
    }

    public static JSONObject serializeWolf(Wolf wolf) {
        try {
            JSONObject root = LivingEntitySerialization.serializeEntity((LivingEntity)wolf);
            if (WolfSerialization.shouldSerialize("collar-color")) {
                root.put("collar-color", ColorSerialization.serializeColor(wolf.getCollarColor().getColor()));
            }
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeWolfAsString(Wolf wolf) {
        return WolfSerialization.serializeWolfAsString(wolf, false);
    }

    public static String serializeWolfAsString(Wolf wolf, boolean pretty) {
        return WolfSerialization.serializeWolfAsString(wolf, pretty, 5);
    }

    public static String serializeWolfAsString(Wolf wolf, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return WolfSerialization.serializeWolf(wolf).toString(indentFactor);
            }
            return WolfSerialization.serializeWolf(wolf).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Wolf spawnWolf(Location location, String stats) {
        try {
            return WolfSerialization.spawnWolf(location, new JSONObject(stats));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Wolf spawnWolf(Location location, JSONObject stats) {
        try {
            Wolf wolf = (Wolf)LivingEntitySerialization.spawnEntity(location, stats);
            if (stats.has("collar-color")) {
                wolf.setCollarColor(DyeColor.getByColor((Color)ColorSerialization.getColor(stats.getString("collar-color"))));
            }
            return wolf;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean shouldSerialize(String key) {
        return SerializationConfig.getShouldSerialize("wolf." + key);
    }*/
}

