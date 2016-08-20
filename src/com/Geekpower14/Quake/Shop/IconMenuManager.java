package com.Geekpower14.Quake.Shop;

import com.Geekpower14.Quake.Utils.IconMenu;
import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class IconMenuManager implements Listener {
    private final HashMap<Player, IconMenu> _menus = new HashMap();
    private final Plugin _plugin;

    public IconMenuManager(Plugin pl) {
        _plugin = pl;
        _plugin.getServer().getPluginManager().registerEvents((Listener)this, _plugin);
    }

    public void closeall() {
        for(Player p : _menus.keySet())
            destroy(p);
    }

    public void create(Player p, String name, int size, IconMenu.OptionClickEventHandler handler) {
        if(_menus.containsKey(p))
            destroy(p);
        IconMenu im = new IconMenu(name, size, handler, _plugin);
        _menus.put(p, im);
    }

    public void show(Player p) {
        if(!_menus.containsKey(p))
            return;
        _menus.get(p).open(p);
    }

    public void reopen(Player p) {
        if(!_menus.containsKey(p))
            return;
        _menus.get(p).reopen(p);
    }

    public void setOption(Player p, int position, ItemStack icon, String reg) {
        if(!_menus.containsKey(p))
            return;
        _menus.get(p).setOption(position, icon, reg);
    }

    public void setOption(Player p, int position, ItemStack icon, String name, String reg, String[] info) {
        if(!_menus.containsKey(p))
            return;
        _menus.get(p).setOption(position, icon, name, reg, info);
    }

    public void destroy(Player p) {
        if(!_menus.containsKey(p))
            return;

        _menus.get(p).destroy();
        _menus.remove(p);
        p.getOpenInventory().close();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player))
            return;
        
        Player p = (Player)event.getWhoClicked();
        if(_menus.containsKey(p))
            _menus.get(p).onInventoryClick(event);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player))
            return;
        
        Player p = (Player)event.getPlayer();
        if(_menus.containsKey(p))
            _menus.get(p).onInventoryClose(event);
    }
}

