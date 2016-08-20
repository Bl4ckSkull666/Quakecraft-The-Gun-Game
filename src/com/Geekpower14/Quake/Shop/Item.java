package com.Geekpower14.Quake.Shop;

import org.bukkit.inventory.ItemStack;

public class Item {
    public String _name;
    public ItemStack _icon;
    public String[] _description;
    public String _reg;
    public int _position;
    public int _price;

    public Item(String n, ItemStack ic, String id, String[] desc, int prix, int pos) {
        _name = n;
        _icon = ic;
        _reg = id;
        _description = desc;
        _position = pos;
        _price = prix;
    }

    public void setName(String n) {
        _name = n;
    }

    public void setIcon(ItemStack ic) {
        _icon = ic;
    }

    public void setID(String id) {
        _reg = id;
    }

    public void setDescription(String[] desc) {
        _description = desc;
    }

    public void setPosition(int pos) {
        _position = pos;
    }

    public void setPrice(int prix) {
        _price = prix;
    }

    public String getName() {
        return _name;
    }

    public ItemStack getIcon() {
        return _icon;
    }

    public String getID() {
        return _reg;
    }

    public String[] getDescription() {
        return _description;
    }

    public int getPos() {
        return _position;
    }

    public int getPrice() {
        return _price;
    }
}

