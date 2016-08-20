package com.Geekpower14.Quake.Stuff;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Stuff.Armor.ArmorBasic;
import com.Geekpower14.Quake.Stuff.Armor.ArmorFactory;
import com.Geekpower14.Quake.Stuff.Hat.HatBasic;
import com.Geekpower14.Quake.Stuff.Hat.HatFactory;
import com.Geekpower14.Quake.Stuff.Item.DiamondHoe;
import com.Geekpower14.Quake.Stuff.Item.GoldHoe;
import com.Geekpower14.Quake.Stuff.Item.IronHoe;
import com.Geekpower14.Quake.Stuff.Item.ItemBasic;
import com.Geekpower14.Quake.Stuff.Item.StoneHoe;
import com.Geekpower14.Quake.Stuff.Item.WoodenHoe;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class StuffManager {
    private final Quake _plugin;
    private ArrayList<ArmorBasic> _armors = new ArrayList();
    private ArrayList<HatBasic> _hats = new ArrayList();
    private ArrayList<ItemBasic> _item = new ArrayList();

    public StuffManager(Quake pl) {
        _plugin = pl;
        loadItem();
        loadHat();
        loadArmor();
    }

    public void reloadConfig() {
        loadItem();
        loadHat();
        loadArmor();
    }

    public final void loadItem() {
        _item = new ArrayList();
        _item.add(new WoodenHoe());
        _item.add(new StoneHoe());
        _item.add(new IronHoe());
        _item.add(new GoldHoe());
        _item.add(new DiamondHoe());
    }

    public final void loadHat() {
        File[] files;
        _hats = new ArrayList();
        File f = new File(_plugin.getDataFolder(), "/Stuff/Hats");
        if (!f.exists()) {
            YamlConfiguration nhat = YamlConfiguration.loadConfiguration((File)new File(""));
            nhat.set("Implementation", "HatCustomFile");
            nhat.set("Price", 50);
            nhat.set("Name", "JackOHat");
            nhat.set("Material", Material.JACK_O_LANTERN.toString());
            nhat.set("Description", (String.valueOf(ChatColor.BOLD.toString()) + "It's cool !"));
            try {
                nhat.save(new File(_plugin.getDataFolder(), "/Stuff/Hats/JackOHat.yml"));
            }
            catch (IOException e) {
                _plugin.getLogger().warning("save JackOHat impossible !");
            }
            YamlConfiguration nhatt = YamlConfiguration.loadConfiguration((File)new File(""));
            nhatt.set("Implementation", "HatCustomFile");
            nhatt.set("Price", 500000);
            nhatt.set("Name", "Geekpower14");
            nhatt.set("Material", "Player:geekpower14");
            nhatt.set("Description", (String.valueOf(ChatColor.BOLD.toString()) + "He's cool !"));
            try {
                nhatt.save(new File(_plugin.getDataFolder(), "/Stuff/Hats/Geekpower14.yml"));
            }
            catch (IOException e) {
                _plugin.getLogger().warning("save JackOHat impossible !");
            }
            f.mkdirs();
        }
        File[] arrfile = files = new File(_plugin.getDataFolder(), "/Stuff/Hats").listFiles();
        int n = arrfile.length;
        int e = 0;
        while (e < n) {
            YamlConfiguration config;
            File file = arrfile[e];
            _plugin.getLogger().warning(file.getPath());
            if (file.isFile() && file.getPath().endsWith(".yml") && (config = YamlConfiguration.loadConfiguration((File)file)).contains("Implementation")) {
                _plugin.getLogger().info(String.valueOf(file.getPath()) + " found implementation");
                HatBasic hat = HatFactory.getInstance(config.getString("Implementation"), file.getPath());
                if (hat == null) {
                    _plugin.getLogger().severe(String.valueOf(file.getPath()) + " invalid implementation");
                } else {
                    _hats.add(hat);
                }
            }
            ++e;
        }
    }

    public final void loadArmor() {
        File[] files;
        _armors = new ArrayList();
        File f = new File(_plugin.getDataFolder(), "/Stuff/Armors");
        if (!f.exists()) {
            YamlConfiguration nhat = YamlConfiguration.loadConfiguration((File)new File(""));
            nhat.set("Implementation", "ArmorCustomFile");
            nhat.set("Price", 100);
            nhat.set("Name", "ArmorRED");
            ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
            meta.setDisplayName(ChatColor.RED + "ArmorRED");
            ArrayList<String> l = new ArrayList<String>();
            l.add(ChatColor.RESET + "A beautiful leather dress!");
            meta.setLore(l);
            meta.setColor(Color.RED);
            item.setItemMeta((ItemMeta)meta);
            nhat.set("Material", item);
            try {
                nhat.save(new File(_plugin.getDataFolder(), "/Stuff/Armors/ArmorRED.yml"));
            }
            catch (IOException e) {
                _plugin.getLogger().warning("save ArmorRED impossible !");
            }
            f.mkdirs();
        }
        File[] e = files = new File(_plugin.getDataFolder(), "/Stuff/Armors").listFiles();
        int l = e.length;
        int meta = 0;
        while (meta < l) {
            YamlConfiguration config;
            File file = e[meta];
            _plugin.getLogger().warning(file.getPath());
            if (file.isFile() && file.getPath().endsWith(".yml") && (config = YamlConfiguration.loadConfiguration((File)file)).contains("Implementation")) {
                _plugin.getLogger().info(file.getPath() + " found implementation");
                ArmorBasic armor = ArmorFactory.getInstance(config.getString("Implementation"), file.getPath());
                if (armor == null) {
                    _plugin.getLogger().severe(file.getPath() + " invalid implementation");
                } else {
                    _armors.add(armor);
                }
            }
            ++meta;
        }
    }

    public ArrayList<ItemBasic> getItems() {
        return _item;
    }

    public ItemBasic getSelectedItem(APlayer player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + player.getPlayer().getName() + ".yml"));
        String id = config.getString("Hoe.Selected");
        if (id == null) {
            id = "RailGun";
        }
        return getItem(id);
    }

    public ItemBasic getSelectedItem(Player player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + player.getName() + ".yml"));
        String id = config.getString("Hoe.Selected");
        if (id == null) {
            id = "RailGun";
        }
        return getItem(id);
    }

    public Boolean setSelectedItem(Player p, String name) {
        if (getItem(name) == null) {
            return false;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        config.set("Hoe.Selected", name);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean hasItem(Player p, String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Hoe.Bought");
        if (list.contains(name)) {
            return true;
        }
        
        ArrayList<String> result = new ArrayList<>();
        for (ItemBasic it : _item) {
            if (!Quake.hasPermission(p, it._givePerm)) continue;
            result.add(it._name);
        }
        if (result.contains(name)) {
            return true;
        }
        return false;
    }

    public List<String> getItems(Player p) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List<String> list = config.getStringList("Hoe.Bought");
        ArrayList<String> result = new ArrayList<>();
        for (String l : list) {
            ItemBasic ite = getItem(l);
            if (ite == null || !Quake.hasPermission(p, ite._needPerm) || result.contains(l)) 
                continue;
            result.add(l);
        }
        for (ItemBasic it : _item) {
            if (!Quake.hasPermission(p, it._givePerm) || result.contains(it._name)) continue;
            result.add(it._name);
        }
        return result;
    }

    public List<String> getHats(Player p) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List<String> list = config.getStringList("Hat.Bought");
        ArrayList<String> result = new ArrayList<>();
        for (String l : list) {
            HatBasic ite = getHat(l);
            if (ite == null || !Quake.hasPermission(p, ite._needPerm) || result.contains(l))
                continue;
            result.add(l);
        }
        for(HatBasic it : _hats) {
            if (!Quake.hasPermission(p, it._givePerm) || result.contains(it._name))
                continue;
            result.add(it._name);
        }
        return result;
    }

    public List<String> getArmors(Player p) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List<String> list = config.getStringList("Armor.Bought");
        ArrayList<String> result = new ArrayList<>();
        for (String l : list) {
            ArmorBasic ite = getArmor(l);
            if (ite == null || !Quake.hasPermission(p, ite._needPerm) || result.contains(l))
                continue;
            result.add(l);
        }
        for (ArmorBasic it : _armors) {
            if (!Quake.hasPermission(p, it._givePerm) || result.contains(it._name))
                continue;
            result.add(it._name);
        }
        return result;
    }

    public void addItem(Player p, String name) {
        if (getItem(name) == null) {
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Hoe.Bought");
        list.add(name);
        config.set("Hoe.Bought", list);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addHat(Player p, String name) {
        if (getHat(name) == null) {
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Hat.Bought");
        list.add(name);
        config.set("Hat.Bought", list);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addArmor(Player p, String name) {
        if (getArmor(name) == null) {
            return;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Armor.Bought");
        list.add(name);
        config.set("Armor.Bought", list);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Boolean setSelectedHat(Player p, String name) {
        if (getHat(name) == null) {
            return false;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        config.set("Hat.Selected", name);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean setSelectedArmor(Player p, String name) {
        if (getArmor(name) == null) {
            return false;
        }
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        config.set("Armor.Selected", name);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Boolean hasHat(Player p, String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Hat.Bought");
        if (list.contains(name)) {
            return true;
        }
        return false;
    }

    public HatBasic getSelectedHat(APlayer player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(player.getConfig()));
        String id = config.getString("Hat.Selected");
        return getHat(id);
    }

    public ArmorBasic getSelectedArmor(APlayer player) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(player.getConfig()));
        String id = config.getString("Armor.Selected");
        return getArmor(id);
    }

    public Boolean hasArmor(Player p, String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Players/" + p.getName() + ".yml"));
        List list = config.getStringList("Armor.Bought");
        if (list.contains(name)) {
            return true;
        }
        return false;
    }

    public ItemBasic getItem(String name) {
        for (ItemBasic it : _item) {
            if (!it._name.equalsIgnoreCase(name)) continue;
            return it;
        }
        return null;
    }

    public HatBasic getHat(String name) {
        for (HatBasic it : _hats) {
            if (!it._name.equalsIgnoreCase(name)) continue;
            return it;
        }
        return null;
    }

    public ArmorBasic getArmor(String name) {
        for (ArmorBasic it : _armors) {
            if (!it._name.equalsIgnoreCase(name)) continue;
            return it;
        }
        return null;
    }

    public ArrayList<HatBasic> getHats() {
        return _hats;
    }

    public ArrayList<ArmorBasic> getArmors() {
        return _armors;
    }

    public void disable() {
        for (ItemBasic it22 : _item) {
            it22.disable();
        }
        for (HatBasic it : _hats) {
            it.disable();
        }
        for (ArmorBasic it2 : _armors) {
            it2.disable();
        }
    }
}

