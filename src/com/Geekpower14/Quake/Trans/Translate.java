package com.Geekpower14.Quake.Trans;

import com.Geekpower14.Quake.Quake;
import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Translate {
    Quake _plugin;
    FileConfiguration _config;

    private void setDefaultConfig(String key, String value) {
        if (!_config.isSet(key)) {
            _config.set(key, value);
        }
    }

    public void reloadConfig() {
        _config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Language.yml"));
    }

    public Translate(Quake pl) {
        _plugin = pl;
        _config = YamlConfiguration.loadConfiguration((File)new File(_plugin.getDataFolder(), "/Language.yml"));
        try {
            setDefaultConfig("Shop.item.name", "&aShop &6(Click to Open)");
            setDefaultConfig("Shop.item.description", "");
            setDefaultConfig("Game.hoe.diamond", "Amazing RailGun");
            setDefaultConfig("Game.hoe.gold", "GoldenGun");
            setDefaultConfig("Game.hoe.iron", "Boosted RailGun");
            setDefaultConfig("Game.hoe.stone", "Advanced RailGun");
            setDefaultConfig("Game.hoe.wood", "RailGun");
            setDefaultConfig("Game.item.leave", "&aExit &6(Click to Leave)");
            setDefaultConfig("Shop.Coins.name", "Coins");
            setDefaultConfig("ScoreBoard.Kills.name", "Kills");
            setDefaultConfig("ScoreBoard.Deaths.name", "Deaths");
            setDefaultConfig("ScoreBoard.Shots.name", "Shots");
            setDefaultConfig("ScoreBoard.Wins.name", "Wins");
            setDefaultConfig("ScoreBoard.name", "Scores");
            setDefaultConfig("Shop.price", "&r&o&6Price: [PRICE]");
            setDefaultConfig("Shop.needToBuy", "&r&o&6Need: [NEED]");
            setDefaultConfig("Shop.navigation.previousPage", "&aBack");
            setDefaultConfig("Shop.navigation.previousPageDesc", "Back to previous page !");
            setDefaultConfig("Shop.navigation.home", "&aHome");
            setDefaultConfig("Shop.navigation.homeDesc", "Back to Home !");
            setDefaultConfig("Shop.navigation.nextPage", "&aNext");
            setDefaultConfig("Shop.navigation.nextPageDesc", "Go to next page !");
            setDefaultConfig("Shop.youHave", "&aYou have :");
            setDefaultConfig("NoPermission", "&cYou have not the permissions.");
            setDefaultConfig("Shop.NoPermissionToBuy", "&cYou have not the permission to buy it !");
            setDefaultConfig("Shop.NotEnoughMoney", "&cYou have not enough money to buy it !");
            setDefaultConfig("Shop.AlreadyHaveItem", "&cYou have already this Item !");
            setDefaultConfig("Shop.YouHaveBoughtItem", "&aYou have bought [NAME] !");
            setDefaultConfig("Shop.AlreadyHaveThisHat", "&cYou have already this Hat !");
            setDefaultConfig("Shop.AlreadyHaveThisArmor", "&cYou have already this Armor !");
            setDefaultConfig("Shop.SelectHoe", "Select your Hoe !");
            setDefaultConfig("Shop.SelectArmor", "Select your Armor !");
            setDefaultConfig("Shop.SelectHat", "Select your Hat !");
            setDefaultConfig("Shop.BuyNewStuff", "Buy new stuff !");
            setDefaultConfig("Shop.ExitDesc", "Exit !");
            setDefaultConfig("Shop.ManagerHoe", "&aHoe Manager");
            setDefaultConfig("Shop.ManagerArmor", "&aArmor Manager");
            setDefaultConfig("Shop.ManagerHat", "&aHat Manager");
            setDefaultConfig("Shop.Shop", "&aShop");
            setDefaultConfig("Shop.Exit", "&aExit");
            setDefaultConfig("Shop.Selection.Hoe", "&aHoe successful selected !");
            setDefaultConfig("Shop.Selection.Hat", "&aHat successful selected");
            setDefaultConfig("Shop.Selection.Armor", "&aArmor successful selected !");
            setDefaultConfig("Shop.YouNeed", "&cYou need [NEED] !");
            setDefaultConfig("Shop.Hoe.Desc1", "&lRight-Click to Instant-Kill !");
            setDefaultConfig("Shop.Hoe.Desc2", "&lRecharge in [TIME] s");
            setDefaultConfig("", "");
            setDefaultConfig("", "");
            setDefaultConfig("", "");
            setDefaultConfig("", "");
            setDefaultConfig("", "");
            setDefaultConfig("", "");
            setDefaultConfig("Shop.message.error.Unknown-Item", "&cError Unknown Item !");
            setDefaultConfig("Shop.message.error.NoEnought-Coin", "&cYou have not enought coins !");
            setDefaultConfig("Shop.message.error.HaveAlreadyHoe", "&cYou have already this hoe !");
            setDefaultConfig("Shop.message.error.HaveBetterHoe", "&cYou have a better hoe !");
            setDefaultConfig("Shop.message.error.MustHaveLowerHoe", "You must have the lower hoe !");
            setDefaultConfig("Shop.message.success.NewHoe", "&aCongratulation, You have a new hoe !");
            setDefaultConfig("Game.Arena.Message.Join", "[PLAYER]&e joined the arena ([NUMBER]/[MAX])");
            setDefaultConfig("Game.Arena.Message.Team-Join", "[PLAYER]&e joined as [TEAM] ([NUMBER]/[MAX])");
            setDefaultConfig("Game.Arena.Message.RemainTime", "&eGame Starting in [TIME] sec");
            setDefaultConfig("Game.Arena.Message.ChangeTeam", "&aYou are now [TEAM] &a!");
            setDefaultConfig("Game.Arena.Message.Aborde-Game", "&eA player leave the arena ! Starting Aborded!");
            setDefaultConfig("Game.Arena.Message.Start", "&eLet's go ! May the Odds be ever in your favour ..");
            setDefaultConfig("Game.Arena.Message.Start-Info", "&eRigh-Click with your gun to Instant-Kill other players !");
            setDefaultConfig("Game.Arena.Message.Shot", "&c[SHOOTER] &8shot [KILLED]");
            setDefaultConfig("Game.Arena.Message.Void", "&c[KILLED] fell out of the world !");
            setDefaultConfig("Game.Arena.Message.Won", "&7[NAME] won the Game !");
            setDefaultConfig("Game.Arena.Message.Team-Won", "&7[TEAM] &7won the Game !");
            setDefaultConfig("Game.Arena.Message.ChangeTeamInGame", "&cYou can't change Team in Game !");
            setDefaultConfig("Game.Arena.Message.NotInAGame", "&cYou are not in game !");
            setDefaultConfig("Game.Arena.error.NotInTeamGame", "&cYou are not in a Team Game !");
            setDefaultConfig("Game.Arena.error.BadTeamName", "&cBad Team Name !");
            setDefaultConfig("Game.Arena.error.inGame", "&cGame in progress !");
            setDefaultConfig("Game.Arena.error.NoSpawn", "&cWhere are no spawn !");
            setDefaultConfig("Game.Arena.error.full", "&cGame is Full !");
            setDefaultConfig("Game.Arena.error.VIP", "&cGame is VIP !");
            setDefaultConfig("Game.Arena.error.command", "&cCommands not allowed in the arena !");
            setDefaultConfig("Game.Arena.Kill.Double", "&cDouble Kill !!");
            setDefaultConfig("Game.Arena.Kill.Triple", "&cTriple Kill !!!");
            setDefaultConfig("Game.Arena.Kill.More", "&cEpic Kill !!!!");
            _config.save(new File(_plugin.getDataFolder(), "/Language.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String path) {
        String result = _config.getString(path);
        if (result == null) {
            return ChatColor.RED + path + " Undefined in config";
        }
        return Translate.replaceColors(result);
    }

    public static String replaceColors(String message) {
        String s = message;
        ChatColor[] arrchatColor = ChatColor.values();
        int n = arrchatColor.length;
        int n2 = 0;
        while (n2 < n) {
            ChatColor color = arrchatColor[n2];
            s = s.replaceAll("(?i)&" + color.getChar(), color + "");
            n2++;
        }
        return s;
    }
}

