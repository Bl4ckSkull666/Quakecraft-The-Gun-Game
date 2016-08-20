package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Location;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.json.JSONException;
import org.json.JSONObject;

public class HorseSerialization {
    protected HorseSerialization() {
    }

    public static JSONObject serializeHorse(Horse horse) {
        try {
            JSONObject root = LivingEntitySerialization.serializeEntity((LivingEntity)horse);
            if (HorseSerialization.shouldSerialize("color")) {
                root.put("color", horse.getColor().name());
            }
            if (HorseSerialization.shouldSerialize("inventory")) {
                root.put("inventory", InventorySerialization.serializeInventory((Inventory)horse.getInventory()));
            }
            if (HorseSerialization.shouldSerialize("jump-strength")) {
                root.put("jump-strength", horse.getJumpStrength());
            }
            if (HorseSerialization.shouldSerialize("style")) {
                root.put("style", (Object)horse.getStyle());
            }
            if (HorseSerialization.shouldSerialize("variant")) {
                root.put("variant", (Object)horse.getVariant());
            }
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeHorseAsString(Horse horse) {
        return HorseSerialization.serializeHorseAsString(horse, false);
    }

    public static String serializeHorseAsString(Horse horse, boolean pretty) {
        return HorseSerialization.serializeHorseAsString(horse, pretty, 5);
    }

    public static String serializeHorseAsString(Horse horse, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return HorseSerialization.serializeHorse(horse).toString(indentFactor);
            }
            return HorseSerialization.serializeHorse(horse).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Horse spawnHorse(Location location, String stats) {
        try {
            return HorseSerialization.spawnHorse(location, new JSONObject(stats));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Horse spawnHorse(Location location, JSONObject stats) {
        try {
            Horse horse = (Horse)LivingEntitySerialization.spawnEntity(location, stats);
            if (stats.has("color")) {
                horse.setColor(Horse.Color.valueOf((String)stats.getString("color")));
            }
            if (stats.has("jump-strength")) {
                horse.setCustomName(stats.getString("name"));
            }
            if (stats.has("style")) {
                horse.setStyle(Horse.Style.valueOf((String)stats.getString("style")));
            }
            if (stats.has("inventory")) {
                PotionEffectSerialization.addPotionEffects(stats.getString("potion-effects"), (LivingEntity)horse);
            }
            if (stats.has("variant")) {
                horse.setVariant(Horse.Variant.valueOf((String)stats.getString("variant")));
            }
            return horse;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean shouldSerialize(String key) {
        return SerializationConfig.getShouldSerialize("horse." + key);
    }
}

