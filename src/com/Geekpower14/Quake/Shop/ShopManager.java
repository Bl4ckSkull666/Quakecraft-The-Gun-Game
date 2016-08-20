package com.Geekpower14.Quake.Shop;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Eco.EcoManager;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Stuff.Armor.ArmorBasic;
import com.Geekpower14.Quake.Stuff.Hat.HatBasic;
import com.Geekpower14.Quake.Stuff.Item.ItemBasic;
import com.Geekpower14.Quake.Utils.IconMenu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ShopManager {
    public Quake _plugin;
    public HashMap<String, Item> _objects = new HashMap();

    public ShopManager(Quake b) {
        _plugin = b;
    }

    public void getMainShop(Player p) {
        _plugin._imm.create(p, "Quake Manager", 54, new IconMenu.OptionClickEventHandler() {

            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if (event.getName() == null) {
                    return;
                }
                Quake.getPlugin()._shop.Main_Manager(event.getPlayer(), event.getName());
            }
        });
        _plugin._imm.setOption(p, 0, new ItemStack(Material.DIAMOND_HOE, 1), _plugin._trad.get("Shop.ManagerHoe"), "Hoe_manager", new String[]{_plugin._trad.get("Shop.SelectHoe")});
        _plugin._imm.setOption(p, 3, new ItemStack(Material.LEATHER_CHESTPLATE, 1), _plugin._trad.get("Shop.ManagerArmor"), "Armor_manager", new String[]{_plugin._trad.get("Shop.SelectArmor")});
        _plugin._imm.setOption(p, 5, new ItemStack(Material.JACK_O_LANTERN, 1), _plugin._trad.get("Shop.ManagerHat"), "Hat_manager", new String[]{_plugin._trad.get("Shop.SelectHat")});
        _plugin._imm.setOption(p, 8, new ItemStack(Material.EMERALD, 1), _plugin._trad.get("Shop.Shop"), "Shop", new String[]{_plugin._trad.get("Shop.BuyNewStuff")});
        _plugin._imm.setOption(p, 49, new ItemStack(Material.BED, 1), _plugin._trad.get("Shop.Exit"), "Exit", new String[]{_plugin._trad.get("Shop.ExitDesc")});
    }

    public void getHoeManager(Player player, int page) {
        APlayer.createConfig(player);
        _plugin._imm.create(player, "Hoe Manager", 54, new IconMenu.OptionClickEventHandler(){

            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if (event.getName() == null) {
                    return;
                }
                event.setWillDestroy(true);
                Quake.getPlugin()._shop.Hoe_Manager(event.getPlayer(), event.getName());
            }
        });
        List<String> Hoes = _plugin._stuff.getItems(player);
        int i = 0;
        i = page;
        while (i <= (page >= 1 ? 44 * page : 44)) {
            if (Hoes.size() < i + 1) break;
            ItemBasic item = _plugin._stuff.getItem(Hoes.get(i));
            String[] stockArr = new String[item.getItem().getItemMeta().getLore().size()];
            stockArr = item.getItem().getItemMeta().getLore().toArray(stockArr);
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), item._name, stockArr);
            ++i;
        }
        _plugin._imm.setOption(player, 49, new ItemStack(Material.BED, 1), _plugin._trad.get("Shop.navigation.home"), "Home", new String[]{_plugin._trad.get("Shop.navigation.homeDesc")});
    }

    public void getHatManager(Player player, int page) {
        _plugin._imm.create(player, "Hat Manager", 54, new IconMenu.OptionClickEventHandler(){

            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if (event.getName() == null) {
                    return;
                }
                event.setWillDestroy(true);
                Quake.getPlugin()._shop.Hat_Manager(event.getPlayer(), event.getName());
            }
        });
        List<String> Hat = _plugin._stuff.getHats(player);
        int i = 0;
        i = page;
        while (i <= (page >= 1 ? 44 * page : 44)) {
            if (Hat.size() < i + 1) break;
            HatBasic item = _plugin._stuff.getHat(Hat.get(i));
            String[] stockArr = new String[item.getItem().getItemMeta().getLore().size()];
            stockArr = item.getItem().getItemMeta().getLore().toArray(stockArr);
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), item._name, stockArr);
            ++i;
        }
        _plugin._imm.setOption(player, 49, new ItemStack(Material.BED, 1), _plugin._trad.get("Shop.navigation.home"), "Home", new String[]{_plugin._trad.get("Shop.navigation.homeDesc")});
    }

    public void getArmorManager(Player player, int page) {
        _plugin._imm.create(player, "Armor Manager", 54, new IconMenu.OptionClickEventHandler(){

            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if (event.getName() == null) {
                    return;
                }
                event.setWillDestroy(true);
                Quake.getPlugin()._shop.Armor_Manager(event.getPlayer(), event.getName());
            }
        });
        List<String> Armor = _plugin._stuff.getArmors(player);
        int i = 0;
        i = page;
        while (i <= (page >= 1 ? 44 * page : 44)) {
            if (Armor.size() < i + 1) break;
            ArmorBasic item = _plugin._stuff.getArmor(Armor.get(i));
            String[] stockArr = new String[item.getItem().getItemMeta().getLore().size()];
            stockArr = item.getItem().getItemMeta().getLore().toArray(stockArr);
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), item._name, stockArr);
            ++i;
        }
        _plugin._imm.setOption(player, 49, new ItemStack(Material.BED, 1), _plugin._trad.get("Shop.navigation.home"), "Home", new String[]{_plugin._trad.get("Shop.navigation.homeDesc")});
    }

    public void getShopMenu(Player player, int page) {
        int slot;
        String name = "Shop";
        if (page > 0) {
            name = "Shop - Page " + page;
        }
        _plugin._imm.create(player, name, 54, new IconMenu.OptionClickEventHandler(){

            @Override
            public void onOptionClick(IconMenu.OptionClickEvent event) {
                if (event.getName() == null) {
                    return;
                }
                Quake.getPlugin()._shop.Shop_Manager(event.getPlayer(), event.getName());
                if (!event.getName().equalsIgnoreCase("Home")) {
                    openShop(event.getPlayer());
                }
            }
        });
        ArrayList<ItemBasic> Hoes = _plugin._stuff.getItems();
        ArrayList<ArmorBasic> Armor = _plugin._stuff.getArmors();
        ArrayList<HatBasic> Hat = _plugin._stuff.getHats();
        int i = slot = page >= 1 ? 44 * page : 0;
        int tmp = 0;
        tmp = 0;
        while (i <= slot + 44 && tmp < Hoes.size()) {
            if (Hoes.size() < tmp + 1) break;
            ItemBasic item = Hoes.get(tmp);
            String[] arrstring = new String[2];
            arrstring[0] = _plugin._trad.get("Shop.price").replace("[PRICE]", Integer.toString(item._price));
            arrstring[1] = !item._needToBuy.equals("") ? _plugin._trad.get("Shop.needToBuy").replace("[NEED]", item._needToBuy) : "";
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), "HOE_" + item._name, arrstring);
            tmp++;
            i++;
        }
        tmp = 0;
        while (i <= slot + 44 && tmp < Hat.size()) {
            if (Hat.size() < tmp + 1) break;
            HatBasic item = Hat.get(tmp);
            String[] arrstring = new String[2];
            arrstring[0] = _plugin._trad.get("Shop.price").replace("[PRICE]", Integer.toString(item._price));
            arrstring[1] = !item._needToBuy.equals("") ? _plugin._trad.get("Shop.needToBuy").replace("[NEED]", item._needToBuy) : "";
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), "HAT_" + item._name, arrstring);
            tmp++;
            i++;
        }
        tmp = 0;
        while (i <= slot + 44 && tmp < Armor.size()) {
            if (Armor.size() < tmp + 1) break;
            ArmorBasic item = Armor.get(tmp);
            String[] arrstring = new String[2];
            arrstring[0] = _plugin._trad.get("Shop.price").replace("[PRICE]", Integer.toString(item._price));
            arrstring[1] = !item._needToBuy.equals("") ? _plugin._trad.get("Shop.needToBuy").replace("[NEED]", item._needToBuy) : "";
            _plugin._imm.setOption(player, i, item.getItem(), item.getItem().getItemMeta().getDisplayName(), "ARMOR_" + item._name, arrstring);
            tmp++;
            i++;
        }
        if (page > 0) {
            _plugin._imm.setOption(player, 45, new ItemStack(Material.SIGN, 1), _plugin._trad.get("Shop.navigation.previousPage"), "Page-" + (page - 1), new String[]{_plugin._trad.get("Shop.navigation.previousPageDesc")});
        }
        _plugin._imm.setOption(player, 49, new ItemStack(Material.BED, 1), _plugin._trad.get("Shop.navigation.home"), "Home", new String[]{_plugin._trad.get("Shop.navigation.homeDesc")});
        Item so = getItemShop(player);
        _plugin._imm.setOption(player, 50, so.getIcon(), so.getName(), "Nothing", so.getDescription());
        if (i == slot + 44) {
            _plugin._imm.setOption(player, 53, new ItemStack(Material.SIGN, 1), _plugin._trad.get("Shop.navigation.nextPage"), "Page-" + (page + 1), new String[]{_plugin._trad.get("Shop.navigation.nextPageDesc")});
        }
    }

    public void getShopItem(Player player) {
        PlayerInventory i = player.getInventory();
        ItemStack coucou = new ItemStack(Material.EMERALD, 1);
        ItemMeta coucou_meta = coucou.getItemMeta();
        coucou_meta.setDisplayName(_plugin._trad.get("Shop.item.name"));
        coucou.setItemMeta(coucou_meta);
        i.addItem(new ItemStack[]{coucou});
    }

    public ItemStack getShop() {
        ItemStack coucou = new ItemStack(_plugin._shopId, 1);
        ItemMeta coucou_meta = coucou.getItemMeta();
        coucou_meta.setDisplayName(_plugin._trad.get("Shop.item.name"));
        coucou.setItemMeta(coucou_meta);
        return coucou;
    }

    public String getMeta() {
        return _plugin._trad.get("Shop.item.name");
    }

    public Item getItemShop(Player player) {
        EcoManager eco = _plugin._eco;
        int coins = eco.getPlayerMoney(player);
        return new Item("Shop", new ItemStack(Material.EMERALD, 1), "shop", new String[] {_plugin._trad.get("Shop.youHave"), "" + ChatColor.GOLD + coins + " " + _plugin._trad.get("Shop.Coins.name")}, 0, 0);
    }

    public void openShop(final Player p) {
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

            @Override
            public void run() {
                Quake.getPlugin()._shop.getShopMenu(p, 0);
                Quake.getPlugin()._imm.show(p);
            }
        }, 2);
    }

    public void Main_Manager(final Player p, String id) {
        if (id.equalsIgnoreCase("Hoe_manager")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getHoeManager(p, 0);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (id.equalsIgnoreCase("Hat_manager")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getHatManager(p, 0);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (id.equalsIgnoreCase("Armor_manager")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getArmorManager(p, 0);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (id.equalsIgnoreCase("Shop")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getShopMenu(p, 0);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
    }

    public void Hoe_Manager(final Player p, String id) {
        if (id.equalsIgnoreCase("Home")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getMainShop(p);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (_plugin._stuff.setSelectedItem(p, id)) {
            p.sendMessage(_plugin._trad.get("Shop.Selection.Hoe"));
        } else {
            p.sendMessage(ChatColor.RED + " Error: Hoe not successful selected !");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                Quake.getPlugin()._shop.getHoeManager(p, 0);
                Quake.getPlugin()._imm.show(p);
            }
        }, 2);
    }

    public void Hat_Manager(final Player p, String id) {
        if (id.equalsIgnoreCase("Home")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getMainShop(p);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (_plugin._stuff.setSelectedHat(p, id)) {
            p.sendMessage(_plugin._trad.get("Shop.Selection.Hat"));
        } else {
            p.sendMessage(ChatColor.RED + " Error: Hat not successful selected !");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                Quake.getPlugin()._shop.getHatManager(p, 0);
                Quake.getPlugin()._imm.show(p);
            }
        }, 2);
    }

    public void Armor_Manager(final Player p, String id) {
        if (id.equalsIgnoreCase("Home")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getMainShop(p);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (_plugin._stuff.setSelectedArmor(p, id)) {
            p.sendMessage(_plugin._trad.get("Shop.Selection.Armor"));
        } else {
            p.sendMessage(ChatColor.RED + " Error: Armor not successful selected !");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {

            @Override
            public void run() {
                Quake.getPlugin()._shop.getArmorManager(p, 0);
                Quake.getPlugin()._imm.show(p);
            }
        }, 2);
    }

    public void Shop_Manager(final Player p, String id) {
        if (id.startsWith("Page")) {
            final String[] pa = id.split("-");
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getShopMenu(p, Integer.valueOf(pa[1]));
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (id.equalsIgnoreCase("Home")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

                @Override
                public void run() {
                    Quake.getPlugin()._shop.getMainShop(p);
                    Quake.getPlugin()._imm.show(p);
                }
            }, 2);
            return;
        }
        if (id.startsWith("HOE")) {
            ItemBasic need;
            String[] pa = id.split("_");
            String ide = pa[1];
            ItemBasic item = _plugin._stuff.getItem(ide);
            if (!item._needToBuy.equals("") && (need = _plugin._stuff.getItem(item._needToBuy)) != null && !_plugin._stuff.hasItem(p, item._needToBuy)) {
                p.sendMessage(_plugin._trad.get("Shop.YouNeed").replace("[NEED]", item._needToBuy));
                return;
            }
            if (!Quake.hasPermission(p, item._needPerm)) {
                p.sendMessage(_plugin._trad.get("Shop.NoPermissionToBuy"));
                return;
            }
            if (!_plugin._eco.has(p, item._price)) {
                p.sendMessage(_plugin._trad.get("Shop.NotEnoughMoney"));
                return;
            }
            if (_plugin._stuff.hasItem(p, item._name)) {
                p.sendMessage(_plugin._trad.get("Shop.AlreadyHaveItem"));
                return;
            }
            _plugin._eco.soustrairePlayerMoney(p, item._price);
            _plugin._stuff.addItem(p, item._name);
            p.sendMessage(_plugin._trad.get("Shop.YouHaveBoughtItem").replace("[NAME]", item._name));
            return;
        }
        if (id.startsWith("HAT")) {
            HatBasic need;
            String[] pa = id.split("_");
            String ide = pa[1];
            HatBasic item = _plugin._stuff.getHat(ide);
            if (!item._needToBuy.equals("") && (need = _plugin._stuff.getHat(item._needToBuy)) != null && !_plugin._stuff.hasHat(p, item._needToBuy)) {
                p.sendMessage(_plugin._trad.get("Shop.YouNeed").replace("[NEED]", item._needToBuy));
                return;
            }
            if (!Quake.hasPermission(p, item._needPerm)) {
                p.sendMessage(_plugin._trad.get("Shop.NoPermissionToBuy"));
                return;
            }
            if (!_plugin._eco.has(p, item._price)) {
                p.sendMessage(_plugin._trad.get("Shop.NotEnoughMoney"));
                return;
            }
            if (_plugin._stuff.hasHat(p, item._name)) {
                p.sendMessage(_plugin._trad.get("Shop.AlreadyHaveThisHat"));
                return;
            }
            _plugin._eco.soustrairePlayerMoney(p, item._price);
            _plugin._stuff.addHat(p, item._name);
            p.sendMessage(_plugin._trad.get("Shop.YouHaveBoughtItem").replace("[NAME]", item._name));
            return;
        }
        if (id.startsWith("ARMOR")) {
            ArmorBasic need;
            String[] pa = id.split("_");
            String ide = pa[1];
            ArmorBasic item = _plugin._stuff.getArmor(ide);
            if (!item._needToBuy.equals("") && (need = _plugin._stuff.getArmor(item._needToBuy)) != null && !_plugin._stuff.hasArmor(p, item._needToBuy)) {
                p.sendMessage(_plugin._trad.get("Shop.YouNeed").replace("[NEED]", item._needToBuy));
                return;
            }
            if (!Quake.hasPermission(p, item._needPerm)) {
                p.sendMessage(_plugin._trad.get("Shop.NoPermissionToBuy"));
                return;
            }
            if (!_plugin._eco.has(p, item._price)) {
                p.sendMessage(_plugin._trad.get("Shop.NotEnoughMoney"));
                return;
            }
            if (_plugin._stuff.hasArmor(p, item._name)) {
                p.sendMessage(_plugin._trad.get("Shop.AlreadyHaveThisArmor"));
                return;
            }
            _plugin._eco.soustrairePlayerMoney(p, item._price);
            _plugin._stuff.addArmor(p, item._name);
            p.sendMessage(_plugin._trad.get("Shop.YouHaveBoughtItem").replace("[NAME]", item._name));
            return;
        }
    }

}

