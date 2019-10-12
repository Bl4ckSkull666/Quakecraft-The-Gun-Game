package com.Geekpower14.Quake.Utils;

import com.Geekpower14.Quake.Quake;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkEffectPlayer {
    public Quake _plugin;
    private Method _world_getHandle = null;
    private Method _nms_world_broadcastEntityEffect = null;
    private Method _firework_getHandle = null;

    public FireworkEffectPlayer(Quake pl) {
        _plugin = pl;
    }

    public void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
        Firework fw = (Firework)world.spawn(loc, (Class)Firework.class);
        Object nms_world = null;
        Object nms_firework = null;
        if (_world_getHandle == null) {
            _world_getHandle = FireworkEffectPlayer.getMethod(world.getClass(), "getHandle");
            _firework_getHandle = FireworkEffectPlayer.getMethod(fw.getClass(), "getHandle");
        }
        
        nms_world = _world_getHandle.invoke(world, (Object[])null);
        nms_firework = _firework_getHandle.invoke(fw, (Object[])null);
        if (_nms_world_broadcastEntityEffect == null) {
            _nms_world_broadcastEntityEffect = FireworkEffectPlayer.getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        FireworkMeta data = fw.getFireworkMeta();
        data.clearEffects();
        data.setPower(1);
        data.addEffect(fe);
        fw.setFireworkMeta(data);
        _nms_world_broadcastEntityEffect.invoke(nms_world, nms_firework, (byte)17);
        fw.remove();
    }

    public void playFirework(World world, Location loc) throws Exception {
        Firework fw = (Firework)world.spawn(loc, (Class)Firework.class);
        if (_world_getHandle == null) {
            _world_getHandle = FireworkEffectPlayer.getMethod(world.getClass(), "getHandle");
            _firework_getHandle = FireworkEffectPlayer.getMethod(fw.getClass(), "getHandle");
        }
        
        Object nms_world = _world_getHandle.invoke(world, (Object[])null);
        Object nms_firework = _firework_getHandle.invoke(fw, (Object[])null);
        if (_nms_world_broadcastEntityEffect == null) {
            _nms_world_broadcastEntityEffect = FireworkEffectPlayer.getMethod(nms_world.getClass(), "broadcastEntityEffect");
        }
        
        FireworkMeta data = fw.getFireworkMeta();
        data.clearEffects();
        data.setPower(1);
        fw.setFireworkMeta(data);
        try {
            _nms_world_broadcastEntityEffect.invoke(nms_world, nms_firework, (byte)17);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        fw.remove();
    }
    
    public static void playFirework(Location loc) {
        loc.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc, 1);
    }
    
    public static void playFirework(Player p) {
        Location loc = p.getLocation();
        Random r = new Random();
        Builder fweBuilder = FireworkEffect.builder();
        switch(r.nextInt(5)) {
            case 0:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.BALL);
                break;
            case 1:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.BALL_LARGE);
                break;
            case 2:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.BURST);
                break;
            case 3:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.CREEPER);
                break;
            case 4:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.STAR);
                break;
            default:
                fweBuilder = fweBuilder.with(FireworkEffect.Type.STAR);
        }
        fweBuilder = fweBuilder.flicker(r.nextBoolean());
        fweBuilder = fweBuilder.withColor(Config.getColor(r.nextInt(17) + 1));
        fweBuilder = fweBuilder.withFade(Config.getColor(r.nextInt(17) + 1));
        fweBuilder = fweBuilder.trail(true);
        
        Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        fwm.addEffect(fweBuilder.build());
        fwm.setPower(r.nextInt(5) + 2);
        fw.setFireworkMeta(fwm);
    }

    private static Method getMethod(Class<?> cl, String method) {
        Method[] arrmethod = cl.getMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method m = arrmethod[n2];
            if (m.getName().equals(method)) {
                return m;
            }
            ++n2;
        }
        return null;
    }
}

