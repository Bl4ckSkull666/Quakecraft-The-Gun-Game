package com.Geekpower14.Quake.Stuff.Armor;

import com.Geekpower14.Quake.Quake;
import org.bukkit.inventory.ItemStack;

public abstract class ArmorBasic {
    public Quake _plugin = Quake.getPlugin();
    public int _price = 0;
    public String _name = "Unknown";
    public String _needPerm = "";
    public String _givePerm = "Quake.admin";
    public String _needToBuy = "";

    public abstract ItemStack getItem();

    public String getNeededPerm() {
        return _needPerm;
    }

    public String getGivePerm() {
        return _givePerm;
    }

    public abstract void disable();

    public abstract void saveConfig();
}

