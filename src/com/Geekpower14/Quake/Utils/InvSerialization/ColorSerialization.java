package com.Geekpower14.Quake.Utils.InvSerialization;

import org.bukkit.Color;
import org.json.JSONException;
import org.json.JSONObject;

public class ColorSerialization {
    protected ColorSerialization() {
    }

    public static JSONObject serializeColor(Color color) {
        try {
            JSONObject root = new JSONObject();
            root.put("red", color.getRed());
            root.put("green", color.getGreen());
            root.put("blue", color.getBlue());
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Color getColor(String color) {
        try {
            return ColorSerialization.getColor(new JSONObject(color));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Color getColor(JSONObject color) {
        try {
            int r = 0;
            int g = 0;
            int b = 0;
            if (color.has("red")) {
                r = color.getInt("red");
            }
            if (color.has("green")) {
                g = color.getInt("green");
            }
            if (color.has("blue")) {
                b = color.getInt("blue");
            }
            return Color.fromRGB((int)r, (int)g, (int)b);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeColorAsString(Color color) {
        return ColorSerialization.serializeColorAsString(color, false);
    }

    public static String serializeColorAsString(Color color, boolean pretty) {
        return ColorSerialization.serializeColorAsString(color, pretty, 5);
    }

    public static String serializeColorAsString(Color color, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return ColorSerialization.serializeColor(color).toString(indentFactor);
            }
            return ColorSerialization.serializeColor(color).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}

