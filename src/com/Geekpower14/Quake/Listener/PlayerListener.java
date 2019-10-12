package com.Geekpower14.Quake.Listener;

import com.Geekpower14.Quake.Arena.APlayer;
import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Utils.FireworkEffectPlayer;
import com.Geekpower14.Quake.Utils.ScoreB;
import com.Geekpower14.Quake.Versions.SelectVersion;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerListener implements Listener {
    Quake _plugin;
    FireworkEffectPlayer _fw;

    public PlayerListener(Quake pl) {
        _plugin = pl;
        _fw = new FireworkEffectPlayer(_plugin);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack hand = player.getInventory().getItemInMainHand();
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if(arena == null) {
            Block block = event.getClickedBlock();
            if ((action == Action.LEFT_CLICK_BLOCK || action == Action.RIGHT_CLICK_BLOCK) && _plugin._lobby.isinLobby(event.getClickedBlock().getLocation()) && block.getState() instanceof Sign) {
                Sign sign = (Sign)block.getState();
                if (sign.getLine(1).equals("")) {
                    return;
                }
                Arena aren = _plugin._am.getArenabyID(Integer.valueOf(sign.getLine(1).replace("T-", "").replace("S-", "")));
                if (aren == null) {
                    return;
                }
                aren.joinArena(player);
                event.setCancelled(true);
                return;
            }
            if (event.getItem() != null && event.getItem().getType().equals(_plugin._shopId) && _plugin._shopWorlds.contains(event.getPlayer().getWorld().getName()) && Quake.hasPermission(player, "Quake.Shop")) {
                _plugin._shop.getMainShop(player);
                _plugin._imm.show(player);
                event.setCancelled(true);
            }
            return;
        }
        
        if(hand.getType() == Material.DARK_OAK_DOOR && hand.getItemMeta().hasDisplayName() && hand.getItemMeta().getDisplayName().toLowerCase().contains("exit")) {
            if(arena != null) {
                arena.leaveArena(player);
            } else {
                if (_plugin._lobby._lobbyspawn != null) {
                    player.teleport(_plugin._lobby._lobbyspawn);
                } else {
                    if(player.getBedLocation() != null)
                        player.teleport(player.getBedLocation());
                    else if(player.getBedSpawnLocation() != null)
                        player.teleport(player.getBedSpawnLocation());
                    else
                        player.teleport(player.getWorld().getSpawnLocation());
                }
            }
            event.setCancelled(true);
        }
    }

    public float getincr(Long time) {
        float result = 0.0f;
        float temp = time;
        result = 100.0f / (temp / 2.0f) / 100.0f;
        return result;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        Player p = event.getPlayer();
        Arena arena = _plugin._am.getArenabyPlayer(p);
        if (arena == null) {
            return;
        }
        if(arena._Sneak) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerFellOutOfWorld(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Arena arena = _plugin._am.getArenabyPlayer(p);
        if(arena == null)
            return;
        
        if(p.getLocation().getY() <= 0.0) {
            p.teleport(arena.getTp(p));
            arena.broadcast(_plugin._trad.get("Game.Arena.Message.Void").replace("[KILLED]", p.getName()));
            arena.kill(p);
            return;
        }
    }

    public void giveScoreBoard(Player p) {
        if (_plugin._scores.containsKey(p.getName())) {
            _plugin._scores.get(p.getName()).updateScore();
        } else {
            _plugin._scores.put(p.getName(), new ScoreB(_plugin, p));
        }
    }

    public Boolean isScoreWorld(String name) {
        if (_plugin._ScoreWorlds.contains(name)) {
            return true;
        }
        return false;
    }

    private Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }
        return c;
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onEntityDamaged(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player)event.getEntity();
        Arena arena = _plugin._am.getArenabyPlayer(p);
        if (arena == null) {
            return;
        }
        if (arena._NaturalDeath && arena._etat == arena._ingame) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if (arena == null) {
            return;
        }
        if (event.getMessage().startsWith("/")) {
            if (player.isOp()) {
                return;
            }
            if (event.getMessage().startsWith("/quake")) {
                return;
            }
            if (event.getMessage().startsWith("/q")) {
                return;
            }
            player.sendMessage(_plugin._trad.get("Game.Arena.error.command"));
            event.setCancelled(true);
        } else if (!arena._Global_Chat) {
            event.setCancelled(true);
            arena.chat(player.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player p = event.getPlayer();
        if (isScoreWorld(event.getFrom().getWorld().getName()) && !isScoreWorld(event.getTo().getWorld().getName())) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            return;
        }
        if (!isScoreWorld(event.getFrom().getWorld().getName()) && isScoreWorld(event.getTo().getWorld().getName())) {
            if (_plugin._scores.containsKey(p.getName())) {
                p.setScoreboard(_plugin._scores.get(p.getName()).getScoreBoard());
            } else {
                _plugin._scores.put(p.getName(), new ScoreB(_plugin, p));
            }
            return;
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        Arena arena = _plugin._am.getArenabyPlayer(player);
        if (arena == null) {
            return;
        }
        if (!arena._Global_Chat) {
            event.setCancelled(true);
            arena.chat(player.getDisplayName() + ChatColor.GRAY + ": " + event.getMessage());
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final Arena arena = _plugin._am.getArenabyPlayer(event.getEntity());
        if (arena == null) {
            return;
        }
        APlayer ap = arena.getAPlayer(player);
        ap.setinvincible(true);
        ap.setReloading(false);
        event.setDeathMessage("");
        event.getDrops().clear();
        event.setDroppedExp(0);
        _plugin.getServer().getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {

            @Override
            public void run() {
                arena.getScoreDeath(player);
            }
        });
        if(arena._Auto_Respawn) {
            _plugin.getServer().getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable() {
                @Override
                public void run() {
                    try {
                        SelectVersion.Respawn(player);
                        //PacketPlayInClientCommand ppicc = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
                        //((CraftPlayer)player).getHandle().playerConnection.a(ppicc);
                    } catch (Exception t) {
                        t.printStackTrace();
                    }
                }
            }, 20L);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        final Arena arena = _plugin._am.getArenabyPlayer(event.getPlayer());
        if (arena == null) {
            return;
        }
        final Player player = event.getPlayer();
        APlayer ap = arena.getAPlayer(player);
        arena.giveStuff(player);
        event.setRespawnLocation(arena.getTp(event.getPlayer()));
        ap.setInvincible(50L);
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable() {
            @Override
            public void run() {
                arena.giveEffect(player);
            }
        }, 10);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerDrop(PlayerDropItemEvent event) {
        Arena arena = _plugin._am.getArenabyPlayer(event.getPlayer());
        if (arena == null) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerFood(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Arena arena = _plugin._am.getArenabyPlayer((Player)event.getEntity());
            if (arena == null) {
                return;
            }
            event.setCancelled(true);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Arena arena = _plugin._am.getArenabyPlayer(event.getPlayer());
        Player player = event.getPlayer();
        _plugin._scores.remove(player.getName());
        if (arena == null) {
            return;
        }
        _plugin.getLogger().warning("Player : " + player.getName() + " Rage Quit !");
        arena.CrashLeaveArena(event.getPlayer());
    }

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onPlayerLogin(PlayerJoinEvent event) {
        Arena arena;
        final Player player = event.getPlayer();
        File f = new File(Quake.getPlugin().getDataFolder() + "/Save_Players/" + player.getName() + ".yml");
        if (f.exists()) {
            player.teleport(_plugin._lobby._lobbyspawn);
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)_plugin, new Runnable(){

                @Override
                public void run() {
                    player.teleport(Quake.getPlugin()._lobby._lobbyspawn);
                }
            }, 30);
            _plugin.getLogger().warning("Player : " + player.getName() + " rejoin after Rage Quit !");
            Arena.RejoinAfterCrash(player);
        }
        if ((arena = _plugin._am.getArenabyPlayer(event.getPlayer())) != null) {
            arena.leaveArena(event.getPlayer());
        }
    }

}

