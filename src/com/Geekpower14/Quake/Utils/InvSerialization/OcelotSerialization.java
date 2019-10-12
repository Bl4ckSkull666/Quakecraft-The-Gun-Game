package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.json.JSONException;
import org.json.JSONObject;

public class OcelotSerialization {
    /*protected OcelotSerialization() {
    }

    public static JSONObject serializeOcelot(Ocelot ocelot) {
        try {
            JSONObject root = LivingEntitySerialization.serializeEntity((LivingEntity)ocelot);
            if (OcelotSerialization.shouldSerialize("type")) {
                root.put("type", ocelot.getCatType().name());
            }
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeOcelotAsString(Ocelot ocelot) {
        return OcelotSerialization.serializeOcelotAsString(ocelot, false);
    }

    public static String serializeOcelotAsString(Ocelot ocelot, boolean pretty) {
        return OcelotSerialization.serializeOcelotAsString(ocelot, pretty, 5);
    }

    public static String serializeOcelotAsString(Ocelot ocelot, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return OcelotSerialization.serializeOcelot(ocelot).toString(indentFactor);
            }
            return OcelotSerialization.serializeOcelot(ocelot).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Ocelot spawnOcelot(Location location, String stats) {
        try {
            return OcelotSerialization.spawnOcelot(location, new JSONObject(stats));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Ocelot spawnOcelot(Location location, JSONObject stats) {
        try {
            Ocelot ocelot = (Ocelot)LivingEntitySerialization.spawnEntity(location, stats);
            if (stats.has("type")) {
                ocelot.setCatType(Ocelot.Type.valueOf((String)stats.getString("type")));
            }
            return ocelot;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean shouldSerialize(String key) {
        return SerializationConfig.getShouldSerialize("ocelot." + key);
    }*/
}

