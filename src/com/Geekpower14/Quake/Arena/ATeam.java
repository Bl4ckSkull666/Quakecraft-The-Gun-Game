package com.Geekpower14.Quake.Arena;

import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.Set;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scoreboard.Team;

public class ATeam {
    private Team _team;
    public String _name;
    public ChatColor _color;
    public Color _Scolor;
    public Arena _aren;
    public int _Score = 0;

    public ATeam(Quake pl, Arena aren, String name, ChatColor color, Color scolor) {
        _name = name;
        _color = color;
        _aren = aren;
        _Scolor = scolor;
        createTeam();
    }

    public void createTeam() {
        _team = _aren._board.registerNewTeam(_name);
        _team.setPrefix(String.valueOf(_color));
        _team.setCanSeeFriendlyInvisibles(true);
    }

    public void setScore(int s) {
        _Score = s;
    }

    public int getScore() {
        return _Score;
    }

    public void addScore(int s) {
        _Score += s;
    }

    public String getName() {
        return _name;
    }

    public void addPlayer(Player p) {
        _team.addEntry(p.getName());
    }

    public void removePlayer(Player p) {
        _team.removeEntry(p.getName());
    }

    public Boolean hasPlayer(Player p) {
        return _team.hasEntry(p.getName());
    }

    public void giveChestplate(Player p) {
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta)item.getItemMeta();
        meta.setDisplayName(_color + "Team " + _name);
        ArrayList<String> l = new ArrayList<>();
        l.add(ChatColor.RESET + "A beautiful leather dress!");
        meta.setLore(l);
        meta.setColor(_Scolor);
        item.setItemMeta((ItemMeta)meta);
        p.getInventory().setChestplate(item);
    }

    public Set<String> getPlayers() {
        return _team.getEntries();
    }

    public int getSize() {
        return _team.getEntries().size();
    }

    public void reset() {
        _Score = 0;
        _team.unregister();
        createTeam();
    }

    public ChatColor getColor() {
        return _color;
    }
}

