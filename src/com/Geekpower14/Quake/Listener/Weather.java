/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.World
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.weather.WeatherChangeEvent
 */
package com.Geekpower14.Quake.Listener;

import com.Geekpower14.Quake.Quake;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class Weather implements Listener {
    public final Quake _plugin;

    public Weather(Quake pl) {
        _plugin = pl;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        World w = event.getWorld();
        if(_plugin._am.isArenaWorld(w) && event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}

