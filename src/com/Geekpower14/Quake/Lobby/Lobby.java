/*
 * Decompiled with CFR 0_114.
 */
package com.Geekpower14.Quake.Lobby;

import com.Geekpower14.Quake.Arena.Arena;
import com.Geekpower14.Quake.Quake;
import com.Geekpower14.Quake.Utils.Area;
import java.util.ArrayList;
import java.util.List;

public class Lobby {
    public final Quake _plugin;
    public final List<Lobby_Sign> _LOBBYS_SIGN = new ArrayList<>();

    public Lobby(Quake pl, Area area) {
        _plugin = pl;
    }

    public Boolean init(List<Arena> arena) {
        return true;
    }
}

