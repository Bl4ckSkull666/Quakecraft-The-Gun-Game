package com.Geekpower14.Quake.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class IconMenu implements Listener {
    private String _name = null;
    private int _size;
    private OptionClickEventHandler _handler;
    private Plugin _plugin;
    private String[] _optionNames;
    private String[] _optionRegs;
    private ItemStack[] _optionIcons;
    private boolean _autodestroy = true;

    public IconMenu(String name, int size, OptionClickEventHandler handler, Plugin plugin) {
        _name = name;
        _size = size;
        _handler = handler;
        _plugin = plugin;
        _optionNames = new String[size];
        _optionRegs = new String[size];
        _optionIcons = new ItemStack[size];
        _autodestroy = true;
    }

    public IconMenu setOption(int position, ItemStack icon, String name, String reg, String[] info) {
        _optionRegs[position] = reg;
        _optionNames[position] = name;
        _optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }

    public IconMenu setOption(int position, ItemStack icon, String reg) {
        _optionRegs[position] = reg;
        _optionNames[position] = _name;
        _optionIcons[position] = icon;
        return this;
    }

    private List<UUID> _openInventories = new ArrayList<>();
    public void open(Player player) {
        if(_openInventories.contains(player.getUniqueId()))
            _openInventories.remove(player.getUniqueId());
        
        Inventory inventory = Bukkit.createInventory((InventoryHolder)player, (int)_size, (String)_name);
        int i = 0;
        while (i < _optionIcons.length) {
            if (_optionIcons[i] != null) {
                inventory.setItem(i, _optionIcons[i]);
            }
            ++i;
        }
        _openInventories.add(player.getUniqueId());
        player.openInventory(inventory);
    }

    public void reopen(Player p) {
        if(_openInventories.contains(p.getUniqueId()))
            _openInventories.remove(p.getUniqueId());
        
        int i = 0;
        while (i < _optionIcons.length) {
            if (_optionIcons[i] != null) {
                p.getOpenInventory().setItem(i, _optionIcons[i]);
            }
            ++i;
        }
        _openInventories.add(p.getUniqueId());
    }

    public void setAutoDestroy(boolean autodestroy) {
        _autodestroy = autodestroy;
    }

    public void destroy() {
        try {
            _handler = null;
            _plugin = null;
            _optionNames = null;
            _optionIcons = null;
            _optionRegs = null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player))
            return;
        
        Player p = (Player)event.getPlayer();
        if(!_openInventories.contains(p.getUniqueId()))
            return;

        if(_autodestroy) {
            destroy();
        }
        _openInventories.remove(p.getUniqueId());
    }

    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getInventory().getHolder() instanceof Player))
            return;
        
        Player p = (Player)event.getWhoClicked();
        if(p == null || !_openInventories.contains(p.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        
        int slot = event.getRawSlot();
        if (slot >= 0 && slot < _size && _optionNames[slot] != null) {
            if (_optionRegs[slot].equals("") || _optionRegs[slot].trim().equals("-cancel-")) {
                event.setCancelled(true);
                return;
            }
            Plugin plugin = _plugin;
            OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, _optionRegs[slot], this, event.isRightClick(), event.isLeftClick(), event.isShiftClick());
            _handler.onOptionClick(e);
            if (e.willClose()) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

                    @Override
                    public void run() {
                        p.closeInventory();
                    }
                }, 1);
            }
            if (e.willDestroy()) {
                destroy();
            }
            event.setCancelled(true);
        }
    }

    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
        ItemMeta im = item.getItemMeta();
        if (im == null) {
            return item;
        }
        if(!name.equals("")) {
            im.setDisplayName(name);
        }
        if (lore != null) {
            im.setLore(Arrays.asList(lore));
        }
        item.setItemMeta(im);
        return item;
    }

    public int size() {
        return _size;
    }

    public class OptionClickEvent {
        private final Player _player;
        private final int _position;
        private final String _name;
        private boolean _close;
        private boolean _destroy;
        private final IconMenu _im;
        private final boolean _rightClick;
        private final boolean _leftClick;
        private boolean _Shift;

        public OptionClickEvent(Player player, int position, String name, IconMenu im, boolean rightClick, boolean leftClick, boolean Shift) {
            _player = player;
            _position = position;
            _name = name;
            _close = true;
            _destroy = false;
            _im = im;
            _rightClick = rightClick;
            _leftClick = leftClick;
            _Shift = Shift;
        }

        public Player getPlayer() {
            return _player;
        }

        public int getPosition() {
            return _position;
        }

        public String getName() {
            return _name;
        }

        public IconMenu getIconMenu() {
            return _im;
        }

        public boolean willClose() {
            return _close;
        }

        public boolean willDestroy() {
            return _destroy;
        }

        public void setWillClose(boolean close) {
            _close = close;
        }

        public void setWillDestroy(boolean destroy) {
            _destroy = destroy;
        }

        public boolean isRightClick() {
            return _rightClick;
        }

        public boolean isLeftClick() {
            return _leftClick;
        }

        public boolean isShiftClick() {
            return _Shift;
        }
    }

    public static interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent var1);
    }

}

