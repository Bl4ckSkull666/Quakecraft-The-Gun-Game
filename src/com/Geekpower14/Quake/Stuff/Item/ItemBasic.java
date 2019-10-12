package com.Geekpower14.Quake.Stuff.Item;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Utils.FireworkEffectPlayer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public abstract class ItemBasic implements Listener {
    public Quake _plugin = Quake.getPlugin();
    public int _price = 0;
    public String _name = "Unknown";
    public String _alias = "";
    public String _needPerm = "";
    public String _givePerm = "Quake.admin";
    public String _needToBuy = "";
    public Long _Time;
    FireworkEffectPlayer _fw;

    public abstract ItemStack getItem();

    public ItemStack defaultItem(Material material, Long time) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + _alias);
        ArrayList<String> arg0 = new ArrayList<>();
        arg0.add(_plugin._trad.get("Shop.Hoe.Desc1"));
        float t = (float)time;
        arg0.add(_plugin._trad.get("Shop.Hoe.Desc2").replace("[TIME]", "" + t / 20.0f));
        meta.setLore(arg0);
        item.setItemMeta(meta);
        return item;
    }

    public ItemBasic() {
        loadConfig();
        _fw = new FireworkEffectPlayer(_plugin);
    }

    public ItemBasic(Arena aren, String name) {
        loadConfig();
        _name = name;
        _fw = new FireworkEffectPlayer(_plugin);
    }

    public ItemBasic(Arena aren, String name, int price) {
        loadConfig();
        _price = price;
        _name = name;
        _fw = new FireworkEffectPlayer(_plugin);
    }

    public String getNeededPerm() {
        return _needPerm;
    }

    public String getGivePerm() {
        return _givePerm;
    }

    protected void BasicShot(Player player, FireworkEffect effect) {
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if (arena == null) {
            return;
        }
        APlayer ap = arena.getAPlayer(player);
        if (ap.isReloading()) {
            return;
        }
        ap.setReloading(true);
        int compte = 0;
        List<Player> victims = getTargetV3(arena, player, 100, 1.2, false);
        for(Player victim : victims) {
            arena.shotplayer(player, victim, effect);
        }
        compte = victims.size();

        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1f, 2.0f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1f, 1.5f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1f, 1.4f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1f, 1.3f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.1f, 1.2f);

        if (compte == 2) {
            arena.broadcast(_plugin._trad.get("Game.Arena.Kill.Double"));
        } else if (compte == 3) {
            arena.broadcast(_plugin._trad.get("Game.Arena.Kill.Triple"));
        } else if (compte >= 4) {
            arena.broadcast(_plugin._trad.get("Game.Arena.Kill.More"));
        }
        arena.getScoreShot(player);
        ap.setReloading(_Time);
    }

    public List<Player> getTargetV3(Arena arena, Player player, int maxRange, double aiming, boolean wallHack) {
        ArrayList<Player> target = new ArrayList<>();
        Location playerEyes = player.getEyeLocation();
        Vector direction = playerEyes.getDirection().normalize();
        ArrayList<Player> targets = new ArrayList<>();
        for(Player online: player.getWorld().getPlayers()) {
            Arena oArena = Quake.getPlugin()._am.getArenabyPlayer(online);
            if(oArena == null || !oArena.equals(arena))
                continue;
            
            if(online != player && online.getLocation().distanceSquared(playerEyes) < (double)(maxRange * maxRange)) {
                targets.add(online);
            }
        }
        Location loc = playerEyes.clone();
        Vector progress = direction.clone().multiply(0.7);
        maxRange = 100 * maxRange / 70;
        int loop = 0;
        while(loop < maxRange) {
            loop++;
            loc.add(progress);
            Block block = loc.getBlock();
            if (!wallHack && block.getType().isSolid())
                break;
            
            double lx = loc.getX();
            double ly = loc.getY();
            double lz = loc.getZ();
            loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, loc, 0, 0.1D, 0.1D, 0.1D, 0.05D);
            for(Player possibleTarget : targets) {
                if(possibleTarget.getUniqueId() == player.getUniqueId())
                    continue;
                Location testLoc = possibleTarget.getLocation().add(0.0, 0.85, 0.0);
                double px = testLoc.getX();
                double py = testLoc.getY();
                double pz = testLoc.getZ();
                boolean dX = Math.abs(lx - px) < 0.7 * aiming;
                boolean dY = Math.abs(ly - py) < 1.7 * aiming;
                boolean dZ = Math.abs(lz - pz) < 0.7 * aiming;
                if(!dX || !dY || !dZ || target.contains(possibleTarget))
                    continue;
                target.add(possibleTarget);
            }
        }
        return target;
    }

    public Boolean istheSame(ItemStack it) {
        ItemStack item = getItem();
        ItemMeta meta = item.getItemMeta();
        ItemMeta met = it.getItemMeta();
        if (meta == null && met == null) {
            return true;
        }
        if (meta == null || met == null) {
            return false;
        }
        if (!meta.getDisplayName().equalsIgnoreCase(met.getDisplayName())) {
            return false;
        }
        return meta.getLore().equals(met.getLore());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = event.getPlayer();
        ItemStack hand = player.getItemInHand();
        Quake.debug("Debug Item Player Listener Interact Part 1");
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if (arena == null) {
            return;
        }
        Quake.debug("Debug Item Player Listener Interact Part 2");
        APlayer ap = arena.getAPlayer(player);
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            Quake.debug("Debug Item Player Listener Interact Part 3");
            if (!istheSame(hand)) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 4");
            if (arena._etat != arena._ingame || arena._finished) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 5");
            if (ap.isReloading()) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 6");
            eject(player);
            event.setCancelled(true);
            return;
        }
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            Quake.debug("Debug Item Player Listener Interact Part 7");
            if (!istheSame(hand)) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 8");
            if (arena._etat != arena._ingame || arena._finished) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 9");
            if (ap.isReloading()) {
                event.setCancelled(true);
                return;
            }
            Quake.debug("Debug Item Player Listener Interact Part 10");
            shot(player);
            event.setCancelled(true);
        }
    }

    public abstract void eject(Player var1);

    public abstract void shot(Player var1);

    public void disable() {
        HandlerList.unregisterAll((Listener)this);
    }

    public final void loadConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Stuff/Hoes/" + _name + ".yml"));
        if (config.contains("Name")) {
            _name = config.getString("Name");
        }
        _alias = _name;
        if (config.contains("Alias")) {
            _alias = config.getString("Alias");
        }
        if (config.contains("Reload-Time")) {
            _Time = config.getLong("Reload-Time");
        }
        if (config.contains("Price")) {
            _price = config.getInt("Price");
        }
        if (config.contains("Needed-Permissions")) {
            _needPerm = config.getString("Needed-Permissions");
        }
        if (config.contains("Permissions-To-Give")) {
            _givePerm = config.getString("Permissions-To-Give");
        }
    }

    public final void saveConfig() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration((File)new File(""));
        config.set("Name",_name);
        config.set("Alias", _alias);
        config.set("Reload-Time", _Time);
        config.set("Price", _price);
        config.set("Needed-Permissions", _needPerm);
        config.set("Permissions-To-Give", _givePerm);
        try {
            config.save(new File(_plugin.getDataFolder(), "/Stuff/Hoes/" + _name + ".yml"));
        }
        catch (IOException e) {
            _plugin.getLogger().warning("save de " + _name + " impossible !");
        }
    }
}

