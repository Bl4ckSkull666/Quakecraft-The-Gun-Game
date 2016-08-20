package com.Geekpower14.Quake.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Area {
    private Location _min = new Location(null, 0.0, 0.0, 0.0);
    private Location _max = new Location(null, 0.0, 0.0, 0.0);

    public Area(Location un, Location deux) {
        if (un == null || deux == null) {
            return;
        }
        if (un.getX() >= deux.getX()) {
            _max.setX(un.getX());
            _min.setX(deux.getX());
        } else {
            _max.setX(deux.getX());
            _min.setX(un.getX());
        }
        if (un.getY() >= deux.getY()) {
            _max.setY(un.getY());
            _min.setY(deux.getY());
        } else {
            _max.setY(deux.getY());
            _min.setY(un.getY());
        }
        if (un.getZ() >= deux.getZ()) {
            _max.setZ(un.getZ());
            _min.setZ(deux.getZ());
        } else {
            _max.setZ(deux.getZ());
            _min.setZ(un.getZ());
        }
    }

    public Area(List<String> src) {
        if (src == null) {
            return;
        }
        if (src.size() < 1 || src.size() > 2) {
            return;
        }
        Location un = str2loc(src.get(0));
        Location deux = str2loc(src.get(1));
        if (un.getX() >= deux.getX()) {
            _max.setX(un.getX());
            _min.setX(deux.getX());
        } else {
            _max.setX(deux.getX());
            _min.setX(un.getX());
        }
        if (un.getY() >= deux.getY()) {
            _max.setY(un.getY());
            _min.setY(deux.getY());
        } else {
            _max.setY(deux.getY());
            _min.setY(un.getY());
        }
        if (un.getZ() >= deux.getZ()) {
            _max.setZ(un.getZ());
            _min.setZ(deux.getZ());
        } else {
            _max.setZ(deux.getZ());
            _min.setZ(un.getZ());
        }
    }

    public Location getMin() {
        return _min;
    }

    public Location getMax() {
        return _max;
    }

    public Boolean isInArea(Location loc) {
        if (loc == null) {
            return false;
        }
        if (loc.getX() > _max.getX() || _min.getX() > loc.getX()) {
            return false;
        }
        if (loc.getY() > _max.getY() || _min.getY() > loc.getY()) {
            return false;
        }
        return !(loc.getZ() > _max.getZ() || _min.getZ() > loc.getZ());
    }

    public Boolean isInLimit(Location loc, int range) {
        if (loc == null) {
            return false;
        }
        if (loc.getX() > _max.getX() - (double)range || _min.getX() + (double)range > loc.getX()) {
            return true;
        }
        return loc.getZ() > _max.getZ() - (double)range || _min.getZ() + (double)range > loc.getZ();
    }

    public List<String> ToString() {
        ArrayList<String> result = new ArrayList<>();
        result.add(loc2str(_min));
        result.add(loc2str(_max));
        return result;
    }

    private Location str2loc(String loc) {
        if (loc == null) {
            return null;
        }
        Location res = null;
        String[] loca = loc.split(", ");
        res = new Location(Bukkit.getServer().getWorld(loca[0]), Double.parseDouble(loca[1]), Double.parseDouble(loca[2]), Double.parseDouble(loca[3]), Float.parseFloat(loca[4]), Float.parseFloat(loca[5]));
        return res;
    }

    private String loc2str(Location loc) {
        return loc.getWorld().getName() + ", " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch();
    }
}

