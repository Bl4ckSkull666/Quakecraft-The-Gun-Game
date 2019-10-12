/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.configuration.ConfigurationSection
 *  org.bukkit.configuration.file.YamlConfiguration
 *  org.bukkit.configuration.file.YamlConfigurationOptions
 */
package com.Geekpower14.Quake.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bukkit.Color;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    private YamlConfiguration _cfg = new YamlConfiguration();
    private File _configFile;
    private Map<String, Boolean> _booleans;
    private Map<String, Integer> _ints;
    private Map<String, Double> _doubles;
    private Map<String, String> _strings;

    public Config(File configFile) {
        _configFile = configFile;
        _booleans = new HashMap<>();
        _ints = new HashMap<>();
        _doubles = new HashMap<>();
        _strings = new HashMap<>();
    }

    public void createDefaults() {
        _cfg.options().indent(4);
        ACFG[] arraCFG = ACFG.values();
        int n = arraCFG.length;
        int n2 = 0;
        while (n2 < n) {
            ACFG cfg = arraCFG[n2];
            _cfg.addDefault(cfg.getNode(), cfg.getValue());
            ++n2;
        }
        save();
    }

    public boolean load() {
        try {
            _cfg.load(_configFile);
            reloadMaps();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void reloadMaps() {
        for (String s : _cfg.getKeys(true)) {
            Object object = _cfg.get(s);
            if (object instanceof Boolean) {
                _booleans.put(s, (Boolean)object);
                continue;
            }
            if (object instanceof Integer) {
                _ints.put(s, (Integer)object);
                continue;
            }
            if (object instanceof Double) {
                _doubles.put(s, (Double)object);
                continue;
            }
            if (!(object instanceof String)) continue;
            _strings.put(s, (String)object);
        }
    }

    public boolean save() {
        try {
            _cfg.save(_configFile);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        return _configFile.delete();
    }

    public void setHeader(String header) {
        _cfg.options().header(header);
    }

    public YamlConfiguration getYamlConfiguration() {
        return _cfg;
    }

    public Object getUnsafe(String string) {
        return _cfg.get(string);
    }

    public boolean getBoolean(ACFG cfg) {
        return getBoolean(cfg, (Boolean)cfg.getValue());
    }

    private boolean getBoolean(ACFG cfg, boolean def) {
        String path = cfg.getNode();
        Boolean result = _booleans.get(path);
        return result == null ? def : result;
    }

    public int getInt(ACFG cfg) {
        return getInt(cfg, (Integer)cfg.getValue());
    }

    public int getInt(ACFG cfg, int def) {
        String path = cfg.getNode();
        Integer result = _ints.get(path);
        return result == null ? def : result;
    }

    public double getDouble(ACFG cfg) {
        return getDouble(cfg, (Double)cfg.getValue());
    }

    public double getDouble(ACFG cfg, double def) {
        String path = cfg.getNode();
        Double result = _doubles.get(path);
        return result == null ? def : result;
    }

    public String getString(ACFG cfg) {
        return getString(cfg, (String)cfg.getValue());
    }

    public String getString(ACFG cfg, String def) {
        String path = cfg.getNode();
        String result = _strings.get(path);
        return result == null ? def : result;
    }

    public Set<String> getKeys(String path) {
        if (_cfg.get(path) == null) {
            return null;
        }
        ConfigurationSection section = _cfg.getConfigurationSection(path);
        return section.getKeys(false);
    }

    public List<String> getStringList(String path, List<String> def) {
        if (_cfg.get(path) == null) {
            return def == null ? new LinkedList() : def;
        }
        return _cfg.getStringList(path);
    }

    public void setManually(String path, Object value) {
        if (value instanceof Boolean) {
            _booleans.put(path, (Boolean)value);
        } else if (value instanceof Integer) {
            _ints.put(path, (Integer)value);
        } else if (value instanceof Double) {
            _doubles.put(path, (Double)value);
        } else if (value instanceof String) {
            _strings.put(path, (String)value);
        }
        if (value == null) {
            _booleans.remove(value);
            _ints.remove(value);
            _doubles.remove(value);
            _strings.remove(value);
        }
        _cfg.set(path, value);
    }

    public void set(ACFG cfg, Object value) {
        setManually(cfg.getNode(), value);
    }

    public static enum ACFG {
        Z("configversion", "v0.9.0.0"),  
        CHAT_COLORNICK("chat.colorNick", true),  
        CHAT_DEFAULTTEAM("chat.defaultTeam", false),  
        MODULES_WORLDEDIT_AUTOLOAD("modules.worldedit.autoload", false),  
        MODULES_WORLDEDIT_AUTOSAVE("modules.worldedit.autosave", false);
        
        private String _node;
        private Object _value;
        private String _type;

        public static ACFG getByNode(String node) {
            ACFG[] arraCFG = ACFG.values();
            int n = arraCFG.length;
            int n2 = 0;
            while (n2 < n) {
                ACFG m = arraCFG[n2];
                if (m.getNode().equals(node)) {
                    return m;
                }
                ++n2;
            }
            return null;
        }

        private ACFG(String node, String value) {
            _node = node;
            _value = value;
            _type = "string";
        }

        private ACFG(String node, Boolean value) {
            _node = node;
            _value = value;
            _type = "boolean";
        }

        private ACFG(String node, Integer value) {
            _node = node;
            _value = value;
            _type = "int";
        }

        private ACFG(String node, Double value) {
            _node = node;
            _value = value;
            _type = "double";
        }

        private ACFG(String node, List<String> value) {
            _node = node;
            _value = value;
            _type = "list";
        }

        public String getNode() {
            return _node;
        }

        public void setNode(String value) {
            _node = value;
        }

        @Override
        public String toString() {
            return String.valueOf(_value);
        }

        public Object getValue() {
            return _value;
        }

        public static ACFG[] getValues() {
            return ACFG.values();
        }

        public String getType() {
            return _type;
        }
    }

    public static Color getColor(int i) {
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
}

