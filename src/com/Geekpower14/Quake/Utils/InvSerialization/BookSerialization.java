package com.Geekpower14.Quake.Utils.InvSerialization;

import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BookSerialization {
    /*protected BookSerialization() {
    }

    public static BookMeta getBookMeta(String json) {
        try {
            return BookSerialization.getBookMeta(new JSONObject(json));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BookMeta getBookMeta(JSONObject json) {
        try {
            ItemStack dummyItems = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta meta = (BookMeta)dummyItems.getItemMeta();
            String title = null;
            String author = null;
            JSONArray pages = null;
            if (json.has("title")) {
                title = json.getString("title");
            }
            if (json.has("author")) {
                author = json.getString("author");
            }
            if (json.has("pages")) {
                pages = json.getJSONArray("pages");
            }
            if (title != null) {
                meta.setTitle(title);
            }
            if (author != null) {
                meta.setAuthor(author);
            }
            if (pages != null) {
                String[] allPages = new String[pages.length()];
                for (int i = 0; i < pages.length(); ++i) {
                    String page = pages.getString(i);
                    if (page.isEmpty() || page == null) {
                        page = "";
                    }
                    allPages[i] = page;
                }
                meta.setPages(allPages);
            }
            return meta;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject serializeBookMeta(BookMeta meta) {
        try {
            JSONObject root = new JSONObject();
            if (meta.hasTitle()) {
                root.put("title", meta.getTitle());
            }
            if (meta.hasAuthor()) {
                root.put("author", meta.getAuthor());
            }
            if (meta.hasPages()) {
                String[] pages = meta.getPages().toArray(new String[0]);
                root.put("pages", pages);
            }
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeBookMetaAsString(BookMeta meta) {
        return BookSerialization.serializeBookMetaAsString(meta, false);
    }

    public static String serializeBookMetaAsString(BookMeta meta, boolean pretty) {
        return BookSerialization.serializeBookMetaAsString(meta, pretty, 5);
    }

    public static String serializeBookMetaAsString(BookMeta meta, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return BookSerialization.serializeBookMeta(meta).toString(indentFactor);
            }
            return BookSerialization.serializeBookMeta(meta).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EnchantmentStorageMeta getEnchantedBookMeta(String json) {
        try {
            return BookSerialization.getEnchantedBookMeta(new JSONObject(json));
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static EnchantmentStorageMeta getEnchantedBookMeta(JSONObject json) {
        try {
            ItemStack dummyItems = new ItemStack(Material.ENCHANTED_BOOK, 1);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta)dummyItems.getItemMeta();
            if (json.has("enchantments")) {
                Map<Enchantment, Integer> enchants = EnchantmentSerialization.getEnchantments(json.getString("enchantments"));
                for (Enchantment e : enchants.keySet()) {
                    meta.addStoredEnchant(e, enchants.get((Object)e).intValue(), true);
                }
            }
            return meta;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject serializeEnchantedBookMeta(EnchantmentStorageMeta meta) {
        try {
            JSONObject root = new JSONObject();
            String enchants = EnchantmentSerialization.serializeEnchantments(meta.getStoredEnchants());
            root.put("enchantments", enchants);
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeEnchantedBookMetaAsString(EnchantmentStorageMeta meta) {
        return BookSerialization.serializeEnchantedBookMetaAsString(meta, false);
    }

    public static String serializeEnchantedBookMetaAsString(EnchantmentStorageMeta meta, boolean pretty) {
        return BookSerialization.serializeEnchantedBookMetaAsString(meta, pretty, 5);
    }

    public static String serializeEnchantedBookMetaAsString(EnchantmentStorageMeta meta, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return BookSerialization.serializeEnchantedBookMeta(meta).toString(indentFactor);
            }
            return BookSerialization.serializeEnchantedBookMeta(meta).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}

