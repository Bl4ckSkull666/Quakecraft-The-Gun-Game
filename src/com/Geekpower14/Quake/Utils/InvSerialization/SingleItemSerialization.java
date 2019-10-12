package com.Geekpower14.Quake.Utils.InvSerialization;

import java.util.ArrayList;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.Repairable;
import org.bukkit.inventory.meta.SkullMeta;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleItemSerialization {
    /*protected SingleItemSerialization() {
    }

    public static JSONObject serializeItemInInventory(ItemStack items, int index) {
        return SingleItemSerialization.serializeItems(items, true, index);
    }

    public static JSONObject serializeItem(ItemStack items) {
        return SingleItemSerialization.serializeItems(items, false, 0);
    }

    private static JSONObject serializeItems(ItemStack items, boolean useIndex, int index) {
        try {
            JSONObject values = new JSONObject();
            if (items == null) {
                return null;
            }
            int amount = items.getAmount();
            short data = items.getDurability();
            boolean hasMeta = items.hasItemMeta();
            String name = null;
            String enchants = null;
            String[] lore = null;
            int repairPenalty = 0;
            Material mat = items.getType();
            JSONObject bookMeta = null;
            JSONObject armorMeta = null;
            JSONObject skullMeta = null;
            JSONObject fwMeta = null;
            if (mat == Material.BOOK_AND_QUILL || mat == Material.WRITTEN_BOOK) {
                bookMeta = BookSerialization.serializeBookMeta((BookMeta)items.getItemMeta());
            } else if (mat == Material.ENCHANTED_BOOK) {
                bookMeta = BookSerialization.serializeEnchantedBookMeta((EnchantmentStorageMeta)items.getItemMeta());
            } else if (Util.isLeatherArmor(mat)) {
                armorMeta = LeatherArmorSerialization.serializeArmor((LeatherArmorMeta)items.getItemMeta());
            } else if (mat == Material.SKULL_ITEM) {
                skullMeta = SkullSerialization.serializeSkull((SkullMeta)items.getItemMeta());
            } else if (mat == Material.FIREWORK) {
                fwMeta = FireworkSerialization.serializeFireworkMeta((FireworkMeta)items.getItemMeta());
            }
            if (hasMeta) {
                Repairable rep;
                ItemMeta meta = items.getItemMeta();
                if (meta.hasDisplayName()) {
                    name = meta.getDisplayName();
                }
                if (meta.hasLore()) {
                    lore = meta.getLore().toArray(new String[0]);
                }
                if (meta.hasEnchants()) {
                    enchants = EnchantmentSerialization.serializeEnchantments(meta.getEnchants());
                }
                if (meta instanceof Repairable && (rep = (Repairable)meta).hasRepairCost()) {
                    repairPenalty = rep.getRepairCost();
                }
            }
            values.put("id", mat.name());
            values.put("amount", amount);
            values.put("data", data);
            if (useIndex) {
                values.put("index", index);
            }
            if (name != null) {
                values.put("name", name);
            }
            if (enchants != null) {
                values.put("enchantments", enchants);
            }
            if (lore != null) {
                values.put("lore", lore);
            }
            if (repairPenalty != 0) {
                values.put("repairPenalty", repairPenalty);
            }
            if (bookMeta != null && bookMeta.length() > 0) {
                values.put("book-meta", bookMeta);
            }
            if (armorMeta != null && armorMeta.length() > 0) {
                values.put("armor-meta", armorMeta);
            }
            if (skullMeta != null && skullMeta.length() > 0) {
                values.put("skull-meta", skullMeta);
            }
            if (fwMeta != null && fwMeta.length() > 0) {
                values.put("firework-meta", fwMeta);
            }
            return values;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getItem(String item) {
        return SingleItemSerialization.getItem(item, 0);
    }

    public static ItemStack getItem(String item, int index) {
        try {
            return SingleItemSerialization.getItem(new JSONObject(item), index);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ItemStack getItem(JSONObject item) {
        return SingleItemSerialization.getItem(item, 0);
    }

    public static ItemStack getItem(JSONObject item, int index) {
        try{
            String id = item.getString("id");
            int amount = item.getInt("amount");
            int data = item.getInt("data");
            String name = null;
            Map<Enchantment, Integer> enchants = null;
            ArrayList<String> lore = null;
            int repairPenalty = 0;
            if (item.has("name")) {
                name = item.getString("name");
            }
            if (item.has("enchantments")) {
                enchants = EnchantmentSerialization.getEnchantments(item.getString("enchantments"));
            }
            if (item.has("lore")) {
                JSONArray l = item.getJSONArray("lore");
                lore = new ArrayList<>();
                for (int j = 0; j < l.length(); ++j) {
                    lore.add(l.getString(j));
                }
            }
            if (item.has("repairPenalty")) {
                repairPenalty = item.getInt("repairPenalty");
            }
            if (!Util.isNum(id) && Material.getMaterial((String)id) == null) {
                throw new IllegalArgumentException("Item " + index + " - No Material found with Material of " + id);
            }
            if (Util.isNum(id) && Material.getMaterial((int)Integer.parseInt(id)) == null) {
                throw new IllegalArgumentException("Item " + index + " - No Material found with id of " + id);
            }
            Material mat = Util.isNum(id) ? Material.getMaterial((int)Integer.parseInt(id)) : Material.getMaterial((String)id);
            ItemStack stuff = new ItemStack(mat, amount, (short)data);
            ItemMeta meta;
            if ((mat == Material.BOOK_AND_QUILL || mat == Material.WRITTEN_BOOK) && item.has("book-meta")) {
                meta = BookSerialization.getBookMeta(item.getJSONObject("book-meta"));
                stuff.setItemMeta((ItemMeta)meta);
            } else if (mat == Material.ENCHANTED_BOOK && item.has("book-meta")) {
                meta = BookSerialization.getEnchantedBookMeta(item.getJSONObject("book-meta"));
                stuff.setItemMeta((ItemMeta)meta);
            } else if (Util.isLeatherArmor(mat) && item.has("armor-meta")) {
                meta = LeatherArmorSerialization.getLeatherArmorMeta(item.getJSONObject("armor-meta"));
                stuff.setItemMeta((ItemMeta)meta);
            } else if (mat == Material.SKULL_ITEM && item.has("skull-meta")) {
                meta = SkullSerialization.getSkullMeta(item.getJSONObject("skull-meta"));
                stuff.setItemMeta((ItemMeta)meta);
            } else if (mat == Material.FIREWORK && item.has("firework-meta")) {
                meta = FireworkSerialization.getFireworkMeta(item.getJSONObject("firework-meta"));
                stuff.setItemMeta((ItemMeta)meta);
            }
            meta = stuff.getItemMeta();
            if (name != null) {
                meta.setDisplayName(name);
            }
            if (lore != null) {
                meta.setLore(lore);
            }
            stuff.setItemMeta((ItemMeta)meta);
            if (repairPenalty != 0) {
                Repairable rep = (Repairable)meta;
                rep.setRepairCost(repairPenalty);
                stuff.setItemMeta((ItemMeta)rep);
            }
            if(enchants != null) {
                stuff.addUnsafeEnchantments(enchants);
            }
            return stuff;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeItemInInventoryAsString(ItemStack items, int index) {
        return SingleItemSerialization.serializeItemInInventoryAsString(items, index, false);
    }

    public static String serializeItemInInventoryAsString(ItemStack items, int index, boolean pretty) {
        return SingleItemSerialization.serializeItemInInventoryAsString(items, index, pretty, 5);
    }

    public static String serializeItemInInventoryAsString(ItemStack items, int index, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return SingleItemSerialization.serializeItemInInventory(items, index).toString(indentFactor);
            }
            return SingleItemSerialization.serializeItemInInventory(items, index).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String serializeItemAsString(ItemStack items) {
        return SingleItemSerialization.serializeItemAsString(items, false);
    }

    public static String serializeItemAsString(ItemStack items, boolean pretty) {
        return SingleItemSerialization.serializeItemAsString(items, pretty, 5);
    }

    public static String serializeItemAsString(ItemStack items, boolean pretty, int indentFactor) {
        try {
            if (pretty) {
                return SingleItemSerialization.serializeItem(items).toString(indentFactor);
            }
            return SingleItemSerialization.serializeItem(items).toString();
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }*/
}

