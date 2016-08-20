package com.Geekpower14.Quake.Utils.InvSerialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;

public class Serializer {
    public static String toString(JSONObject object) {
        return Serializer.toString(object, true);
    }

    public static String toString(JSONObject object, boolean pretty) {
        return Serializer.toString(object, pretty, 5);
    }

    public static String toString(JSONObject object, boolean pretty, int tabSize) {
        try {
            if (pretty) {
                return object.toString(tabSize);
            }
            return object.toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getObjectFromFile(File file) throws FileNotFoundException, JSONException {
        return Serializer.getObjectFromStream(new FileInputStream(file));
    }

    public static JSONObject getObjectFromStream(InputStream stream) throws JSONException {
        return new JSONObject(Serializer.getStringFromStream(stream));
    }

    public static String getStringFromFile(File file) throws FileNotFoundException {
        return Serializer.getStringFromStream(new FileInputStream(file));
    }

    public static String getStringFromStream(InputStream stream) {
        Scanner x = new Scanner(stream);
        String str = "";
        while (x.hasNextLine()) {
            str = str + x.nextLine() + "\n";
        }
        x.close();
        return str.trim();
    }
}

