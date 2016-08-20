package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.GameMode;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

public class PlayerStatsSerialization {
    protected PlayerStatsSerialization() {
    }

    public static JSONObject serializePlayerStats(Player player) {
        try {
            JSONObject root = new JSONObject();
            if (PlayerStatsSerialization.shouldSerialize("can-fly")) {
                root.put("can-fly", player.getAllowFlight());
            }
            if (PlayerStatsSerialization.shouldSerialize("display-name")) {
                root.put("display-name", player.getDisplayName());
            }
            if (PlayerStatsSerialization.shouldSerialize("exhaustion")) {
                root.put("exhaustion", player.getExhaustion());
            }
            if (PlayerStatsSerialization.shouldSerialize("exp")) {
                root.put("exp", player.getExp());
            }
            if (PlayerStatsSerialization.shouldSerialize("flying")) {
                root.put("flying", player.isFlying());
            }
            if (PlayerStatsSerialization.shouldSerialize("food")) {
                root.put("food", player.getFoodLevel());
            }
            if (PlayerStatsSerialization.shouldSerialize("gamemode")) {
                root.put("gamemode", player.getGameMode().ordinal());
            }
            if (PlayerStatsSerialization.shouldSerialize("health")) {
                root.put("health", player.getHealthScale());
            }
            if (PlayerStatsSerialization.shouldSerialize("level")) {
                root.put("level", player.getLevel());
            }
            if (PlayerStatsSerialization.shouldSerialize("potion-effects")) {
                root.put("potion-effects", PotionEffectSerialization.serializeEffects(player.getActivePotionEffects()));
            }
            if (PlayerStatsSerialization.shouldSerialize("saturation")) {
                root.put("saturation", player.getSaturation());
            }
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializePlayerStatsAsString(Player player) {
        return PlayerStatsSerialization.serializePlayerStatsAsString(player, false);
    }

    public static String serializePlayerStatsAsString(Player player, boolean pretty) {
        return PlayerStatsSerialization.serializePlayerStatsAsString(player, pretty, 5);
    }

    public static String serializePlayerStatsAsString(Player player, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return PlayerStatsSerialization.serializePlayerStats(player).toString(indentFactor);
            }
            return PlayerStatsSerialization.serializePlayerStats(player).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void applyPlayerStats(Player player, String stats) {
        try {
            PlayerStatsSerialization.applyPlayerStats(player, new JSONObject(stats));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void applyPlayerStats(Player player, JSONObject stats) {
        try {
            if (stats.has("can-fly")) {
                player.setAllowFlight(stats.getBoolean("can-fly"));
            }
            if (stats.has("display-name")) {
                player.setDisplayName(stats.getString("display-name"));
            }
            if (stats.has("exhaustion")) {
                player.setExhaustion((float)stats.getDouble("exhaustion"));
            }
            if (stats.has("exp")) {
                player.setExp((float)stats.getDouble("exp"));
            }
            if (stats.has("flying")) {
                player.setFlying(stats.getBoolean("flying"));
            }
            if (stats.has("food")) {
                player.setFoodLevel(stats.getInt("food"));
            }
            if (stats.has("health")) {
                player.setHealth(stats.getDouble("health"));
            }
            if (stats.has("gamemode")) {
                player.setGameMode(GameMode.getByValue((int)stats.getInt("gamemode")));
            }
            if (stats.has("level")) {
                player.setLevel(stats.getInt("level"));
            }
            if (stats.has("potion-effects")) {
                PotionEffectSerialization.setPotionEffects(stats.getString("potion-effects"), (LivingEntity)player);
            }
            if (stats.has("saturation")) {
                player.setSaturation((float)stats.getDouble("saturation"));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static boolean shouldSerialize(String key) {
        return SerializationConfig.getShouldSerialize("player-stats." + key);
    }
}

