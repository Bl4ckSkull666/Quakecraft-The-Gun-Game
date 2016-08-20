package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FireworkEffectSerialization {
    protected FireworkEffectSerialization() {
    }

    public static FireworkEffect getFireworkEffect(String json) {
        return FireworkEffectSerialization.getFireworkEffect(json);
    }

    public static FireworkEffect getFireworkEffect(JSONObject json) {
        try {
            FireworkEffect.Builder builder = FireworkEffect.builder();
            JSONArray colors = json.getJSONArray("colors");
            for (int j = 0; j < colors.length(); ++j) {
                builder.withColor(ColorSerialization.getColor(colors.getJSONObject(j)));
            }
            JSONArray fadeColors = json.getJSONArray("fade-colors");
            for (int j2 = 0; j2 < fadeColors.length(); ++j2) {
                builder.withFade(ColorSerialization.getColor(colors.getJSONObject(j2)));
            }
            if (json.getBoolean("flicker")) {
                builder.withFlicker();
            }
            if (json.getBoolean("trail")) {
                builder.withTrail();
            }
            builder.with(FireworkEffect.Type.valueOf((String)json.getString("type")));
            return builder.build();
        }
        catch (IllegalArgumentException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject serializeFireworkEffect(FireworkEffect effect) {
        try {
            JSONObject root = new JSONObject();
            JSONArray colors = new JSONArray();
            for (Object c : effect.getColors()) {
                colors.put(ColorSerialization.serializeColor((Color)c));
            }
            root.put("colors", colors);
            JSONArray fadeColors = new JSONArray();
            for (Color c2 : effect.getFadeColors()) {
                fadeColors.put(ColorSerialization.serializeColor(c2));
            }
            root.put("fade-colors", fadeColors);
            root.put("flicker", effect.hasFlicker());
            root.put("trail", effect.hasTrail());
            root.put("type", effect.getType().name());
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeFireworkEffectAsString(FireworkEffect effect) {
        return FireworkEffectSerialization.serializeFireworkEffectAsString(effect, false);
    }

    public static String serializeFireworkEffectAsString(FireworkEffect effect, boolean pretty) {
        return FireworkEffectSerialization.serializeFireworkEffectAsString(effect, false, 5);
    }

    public static String serializeFireworkEffectAsString(FireworkEffect effect, boolean pretty, int indentFactor) {
        return Serializer.toString(FireworkEffectSerialization.serializeFireworkEffect(effect), pretty, indentFactor);
    }
}

