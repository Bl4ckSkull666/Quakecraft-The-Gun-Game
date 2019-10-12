package com.Geekpower14.Quake.Utils.InvSerialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InventorySerialization {
    /*protected InventorySerialization() {
    }

    public static JSONArray serializeInventory(Inventory inv) {
        JSONArray inventory = new JSONArray();
        for (int i = 0; i < inv.getSize(); ++i) {
            JSONObject values = SingleItemSerialization.serializeItemInInventory(inv.getItem(i), i);
            if (values == null)
                continue;
            inventory.put(values);
        }
        return inventory;
    }

    public static JSONObject serializePlayerInventory(PlayerInventory inv) {
        try {
            JSONObject root = new JSONObject();
            JSONArray inventory = InventorySerialization.serializeInventory((Inventory)inv);
            JSONArray armor = InventorySerialization.serializeInventory(inv.getArmorContents());
            root.put("inventory", inventory);
            root.put("armor", armor);
            return root;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializePlayerInventoryAsString(PlayerInventory inv) {
        return InventorySerialization.serializePlayerInventoryAsString(inv, false);
    }

    public static String serializePlayerInventoryAsString(PlayerInventory inv, boolean pretty) {
        return InventorySerialization.serializePlayerInventoryAsString(inv, pretty, 5);
    }

    public static String serializePlayerInventoryAsString(PlayerInventory inv, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return InventorySerialization.serializePlayerInventory(inv).toString(indentFactor);
            }
            return InventorySerialization.serializePlayerInventory(inv).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeInventoryAsString(Inventory inventory) {
        return InventorySerialization.serializeInventoryAsString(inventory, false);
    }

    public static String serializeInventoryAsString(Inventory inventory, boolean pretty) {
        return InventorySerialization.serializeInventoryAsString(inventory, pretty, 5);
    }

    public static String serializeInventoryAsString(Inventory inventory, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return InventorySerialization.serializeInventory(inventory).toString(indentFactor);
            }
            return InventorySerialization.serializeInventory(inventory).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeInventoryAsString(ItemStack[] contents) {
        return InventorySerialization.serializeInventoryAsString(contents, false);
    }

    public static String serializeInventoryAsString(ItemStack[] contents, boolean pretty) {
        return InventorySerialization.serializeInventoryAsString(contents, pretty, 5);
    }

    public static String serializeInventoryAsString(ItemStack[] contents, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return InventorySerialization.serializeInventory(contents).toString(indentFactor);
            }
            return InventorySerialization.serializeInventory(contents).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONArray serializeInventory(ItemStack[] contents) {
        JSONArray inventory = new JSONArray();
        for (int i = 0; i < contents.length; ++i) {
            JSONObject values = SingleItemSerialization.serializeItemInInventory(contents[i], i);
            if (values == null) continue;
            inventory.put(values);
        }
        return inventory;
    }

    public static ItemStack[] getInventory(String json, int size) {
        try {
            return InventorySerialization.getInventory(new JSONArray(json), size);
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack[] getInventory(JSONArray inv, int size) {
        try {
            ItemStack[] contents = new ItemStack[size];
            for (int i = 0; i < inv.length(); ++i) {
                ItemStack stuff;
                JSONObject item = inv.getJSONObject(i);
                int index = item.getInt("index");
                if (index > size) {
                    throw new IllegalArgumentException("index found is greator than expected size (" + index + ">" + size + ")");
                }
                if (index > contents.length || index < 0) {
                    throw new IllegalArgumentException("Item " + i + " - Slot " + index + " does not exist in this inventory");
                }
                contents[index] = stuff = SingleItemSerialization.getItem(item);
            }
            return contents;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack[] getInventory(File jsonFile, int size) {
        String source = "";
        try {
            Scanner x = new Scanner(jsonFile);
            while (x.hasNextLine()) {
                source = source + x.nextLine() + "\n";
            }
            x.close();
            return InventorySerialization.getInventory(source, size);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setInventory(InventoryHolder holder, String inv) {
        InventorySerialization.setInventory(holder.getInventory(), inv);
    }

    public static void setInventory(InventoryHolder holder, JSONArray inv) {
        InventorySerialization.setInventory(holder.getInventory(), inv);
    }

    public static void setInventory(Inventory inventory, String inv) {
        try {
            InventorySerialization.setInventory(inventory, new JSONArray(inv));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setInventory(Inventory inventory, JSONArray inv) {
        ItemStack[] items = InventorySerialization.getInventory(inv, inventory.getSize());
        inventory.clear();
        for (int i = 0; i < items.length; ++i) {
            ItemStack item = items[i];
            if (item == null) continue;
            inventory.setItem(i, item);
        }
    }

    public static void setPlayerInventory(Player player, String inv) {
        try {
            InventorySerialization.setPlayerInventory(player, new JSONObject(inv));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerInventory(Player player, JSONObject inv) {
        try {
            PlayerInventory inventory = player.getInventory();
            ItemStack[] armor = InventorySerialization.getInventory(inv.getJSONArray("armor"), 4);
            inventory.clear();
            inventory.setArmorContents(armor);
            InventorySerialization.setInventory((InventoryHolder)player, inv.getJSONArray("inventory"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}

