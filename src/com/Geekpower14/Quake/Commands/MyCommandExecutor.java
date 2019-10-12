package com.Geekpower14.Quake.Commands;

import com.Geekpower14.Quake.Quake;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MyCommandExecutor implements CommandExecutor {
    public Quake _plugin;
    private final HashMap<String, BasicCommand> _commands = new HashMap<>();

    public MyCommandExecutor(Quake pl) {
        _plugin = pl;
        loadCommands();
    }

    private void loadCommands() {
        _commands.put("leave", new LeaveCommand(_plugin));
        _commands.put("addspawn", new AddSpawnCommand(_plugin));
        _commands.put("addlobby", new AddLobbyCommand(_plugin));
        _commands.put("create", new CreateCommand(_plugin));
        _commands.put("setlobbyspawn", new SetLobbySpawnCommand(_plugin));
        _commands.put("join", new JoinCommand(_plugin));
        _commands.put("removelobby", new RemoveLobbyCommand(_plugin));
        _commands.put("removespawn", new RemoveSpawnCommand(_plugin));
        _commands.put("save", new SaveCommand(_plugin));
        _commands.put("setmap", new SetMapCommand(_plugin));
        _commands.put("setmin", new SetMinCommand(_plugin));
        _commands.put("setmax", new SetMaxCommand(_plugin));
        _commands.put("start", new StartCommand(_plugin));
        _commands.put("stop", new StopCommand(_plugin));
        _commands.put("shop", new ShopCommand(_plugin));
        _commands.put("remove", new RemoveCommand(_plugin));
        _commands.put("kill", new UtilsCommand(_plugin, "kill"));
        _commands.put("add", new UtilsCommand(_plugin, "add"));
        _commands.put("lol", new UtilsCommand(_plugin, "lol"));
        _commands.put("reload", new ReloadCommand(_plugin));
        _commands.put("team", new ChangeTeamCommand(_plugin));
        _commands.put("money", new MoneyCommand(_plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = null;
        if (!(sender instanceof Player)) {
            sender.sendMessage("You need to be a player !");
            return true;
        }
        player = (Player)sender;
        if(cmd.getName().equalsIgnoreCase("quake")) {
            if(args == null || args.length < 1) {
                player.sendMessage(ChatColor.YELLOW + "Plugin By Geekpower14");
                player.sendMessage(ChatColor.YELLOW + "Reloaded by Bl4ckSkull666 ( wwww.Survival-Piraten.de )");
                player.sendMessage(ChatColor.YELLOW + "Version: " + _plugin.getDescription().getVersion());
                return true;
            }
            
            if(args[0].equalsIgnoreCase("help")) {
                help(player);
                return true;
            }
            
            String sub = args[0];
            ArrayList<String> l = new ArrayList<>();
            l.addAll(Arrays.asList(args));
            l.remove(0);
            args = l.toArray(new String[0]);
            
            if(!_commands.containsKey(sub)) {
                player.sendMessage(ChatColor.RED + "Command dosent exist.");
                player.sendMessage(ChatColor.GOLD + "Type /quake help for help");
                return true;
            }
            
            try {
                _commands.get(sub).onCommand(player, args);
            } catch(Exception e) {
                e.printStackTrace();
                player.sendMessage(ChatColor.RED + "An error occured while executing the command. Check the console");
                player.sendMessage(ChatColor.BLUE + "Type /quake help for help");
            }
            return true;
        }
        return true;
    }

    public void help(Player p) {
        p.sendMessage("/Quake <command> <args>");
        for (BasicCommand v : _commands.values()) {
            p.sendMessage(ChatColor.GRAY + "- " + v.help(p));
        }
    }
}

