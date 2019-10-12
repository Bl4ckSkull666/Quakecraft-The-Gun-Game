package com.Geekpower14.Quake.Utils.InvSerialization;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantmentSerialization {
    /*protected EnchantmentSerialization() {
    }

    public static String serializeEnchantments(Map<Enchantment, Integer> enchantments) {
        String serialized = "";
        for (Enchantment e : enchantments.keySet()) {
            serialized = serialized + e.getKey().getNamespace() + ":" + enchantments.get(e) + ";";
        }
        return serialized;
    }

    public static Map<Enchantment, Integer> getEnchantments(String serializedEnchants) {
        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        if (serializedEnchants.isEmpty()) {
            return enchantments;
        }
        String[] enchants = serializedEnchants.split(";");
        for (int i = 0; i < enchants.length; ++i) {
            String[] ench = enchants[i].split(":");
            if (ench.length < 2) {
                throw new IllegalArgumentException(serializedEnchants + " - Enchantment " + i + " (" + enchants[i] + "): split must at least have a length of 2");
            }
            if (!Util.isNum(ench[1])) {
                throw new IllegalArgumentException(serializedEnchants + " - Enchantment " + i + " (" + enchants[i] + "): level is not an integer");
            }
            String id = ench[0];
            int level = Integer.parseInt(ench[1]);
            Enchantment e = Enchantment.getByKey(NamespacedKey.minecraft(id));
            if (e == null) {
                throw new IllegalArgumentException(serializedEnchants + " - Enchantment " + i + " (" + enchants[i] + "): no Enchantment with id of " + id);
            }
            enchantments.put(e, level);
        }
        return enchantments;
    }

    /*public static Map<Enchantment, Integer> getEnchantsFromOldFormat(String oldFormat) {
        HashMap<Enchantment, Integer> enchants = new HashMap<>();
        if (oldFormat.length() == 0) {
            return enchants;
        }
        String nums = "" + Long.parseLong(oldFormat, 32) + "";
        System.out.println(nums);
        for (int i = 0; i < nums.length(); i += 3) {
            int enchantId = Integer.parseInt(nums.substring(i, i + 2));
            int enchantLevel = Integer.parseInt("" + nums.charAt(i + 2) + "");
            Enchantment ench = Enchantment.getByKey(NamespacedKey.minecraft(enchantId));
            enchants.put(ench, enchantLevel);
        }
        return enchants;
    }

    public static String convert(String oldFormat) {
        Map<Enchantment, Integer> enchants = EnchantmentSerialization.getEnchantsFromOldFormat(oldFormat);
        return EnchantmentSerialization.serializeEnchantments(enchants);
    }

    public static Map<Enchantment, Integer> convertAndGetEnchantments(String oldFormat) {
        String newFormat = EnchantmentSerialization.convert(oldFormat);
        return EnchantmentSerialization.getEnchantments(newFormat);
    }

    public static void addEnchantments(String code, ItemStack items) {
        items.addUnsafeEnchantments(EnchantmentSerialization.getEnchantments(code));
    }*/
}

