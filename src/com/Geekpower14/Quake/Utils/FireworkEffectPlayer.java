package com.Geekpower14.Quake.Utils;

import com.Geekpower14.Quake.Quake;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
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

