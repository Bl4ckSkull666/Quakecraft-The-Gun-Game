/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Geekpower14.Quake.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
//1.8.8
//import net.minecraft.server.v1_9_R1.EnumParticle;
//1.9.2
//import net.minecraft.server.v1_9_R1.EnumParticle;
//1.9.4
//import net.minecraft.server.v1_9_R2.EnumParticle;
//1.10
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public enum ParticleEffects {
    HUGE_EXPLOSION("hugeexplosion", "EXPLOSION_HUGE"),
    LARGE_EXPLODE("largeexplode", "EXPLOSION_LARGE"), 
    BUBBLE("bubble", "WATER_BUBBLE"),  
    SUSPEND("suspended", "SUSPENDED"), 
    DEPTH_SUSPEND("depthsuspend", "SUSPENDED_DEPTH"), 
    MAGIC_CRIT("magicCrit", "CRIT_MAGIC"), 
    MOB_SPELL("mobSpell", "SPELL_MOB", true),
    MOB_SPELL_AMBIENT("mobSpellAmbient", "SPELL_MOB_AMBIENT"),  
    INSTANT_SPELL("instantSpell", "SPELL_INSTANT"), 
    WITCH_MAGIC("witchMagic", "SPELL_WITCH"),  
    EXPLODE("explode", "EXPLOSION_NORMAL"),  
    SPLASH("splash", "WATER_SPLASH"),  
    LARGE_SMOKE("largesmoke", "SMOKE_LARGE"),  
    RED_DUST("reddust", "REDSTONE", true),  
    SNOWBALL_POOF("snowballpoof", "SNOWBALL"),  
    ANGRY_VILLAGER("angryVillager", "VILLAGER_ANGRY"),  
    HAPPY_VILLAGER("happyVillager", "VILLAGER_HAPPY"),  
    EXPLOSION_NORMAL(EXPLODE.getName()),  
    EXPLOSION_LARGE(LARGE_EXPLODE.getName()),  
    EXPLOSION_HUGE(HUGE_EXPLOSION.getName()),  
    FIREWORKS_SPARK("fireworksSpark"),  
    WATER_BUBBLE(BUBBLE.getName()),  
    WATER_SPLASH(SPLASH.getName()),  
    WATER_WAKE("wake"),  
    SUSPENDED(SUSPEND.getName()),
    SUSPENDED_DEPTH(DEPTH_SUSPEND.getName()),  
    CRIT("crit"),  
    CRIT_MAGIC(MAGIC_CRIT.getName()),
    SMOKE_NORMAL("smoke"),
    SMOKE_LARGE(LARGE_SMOKE.getName()),
    SPELL("spell"),  
    SPELL_INSTANT(INSTANT_SPELL.getName()),
    SPELL_MOB(MOB_SPELL.getName(), true),
    SPELL_MOB_AMBIENT(MOB_SPELL_AMBIENT.getName()),
    SPELL_WITCH(WITCH_MAGIC.getName()),  
    DRIP_WATER("dripWater"), 
    DRIP_LAVA("dripLava"),  
    VILLAGER_ANGRY(ANGRY_VILLAGER.getName()), 
    VILLAGER_HAPPY(HAPPY_VILLAGER.getName()), 
    TOWN_AURA("townaura"),  NOTE("note", true),  
    PORTAL("portal"),
    ENCHANTMENT_TABLE("enchantmenttable"), 
    FLAME("flame"),  
    LAVA("lava"),  
    FOOTSTEP("footstep"),  
    CLOUD("cloud"),  
    REDSTONE("reddust", true),  
    SNOWBALL("snowballpoof"),  
    SNOW_SHOVEL("snowshovel"),  
    SLIME("slime"),  
    HEART("heart"),  
    BARRIER("barrier"),  
    ITEM_CRACK("iconcrack_"),  
    BLOCK_CRACK("blockcrack_"),  
    BLOCK_DUST("blockdust_"),  
    WATER_DROP("droplet"),  
    ITEM_TAKE("take"),  
    MOB_APPEARANCE("mobappearance");

    private final String _particleName;
    private final String _enumValue;
    private final boolean _hasColor;
    private static Class<?> _nmsPacketPlayOutParticle;
    private static Class<?> _nmsEnumParticle;
    private static int _particleRange;
    private static Class<?> _nmsPlayerConnection;
    private static Class<?> _nmsEntityPlayer;
    private static Class<?> _ioNettyChannel;
    private static Method _nmsNetworkGetVersion;
    private static Field _nmsFieldPlayerConnection;
    private static Field _nmsFieldNetworkManager;
    private static Field _nmsFieldNetworkManagerI;
    private static Field _nmsFieldNetworkManagerM;

    private ParticleEffects(String particleName, String enumValue, boolean hasColor) {
        _particleName = particleName;
        _enumValue = enumValue;
        _hasColor = hasColor;
    }

    private ParticleEffects(String particleName, String enumValue) {
        _particleName = particleName;
        _enumValue = enumValue;
        _hasColor = false;
    }

    private ParticleEffects(String particleName) {
        _particleName = particleName;
        _enumValue = null;
        _hasColor = false;
    }

    private ParticleEffects(String particleName, boolean hasColor) {
        _particleName = particleName;
        _enumValue = null;
        _hasColor = hasColor;
    }
  
    public String getName() {
        return _particleName;
    }

    public boolean hasColor() {
        return _hasColor;
    }

    public static void setRange(int range) {
        if(range < 0) {
            throw new IllegalArgumentException("Range must be positive!");
        }
        _particleRange = range;
    }

    public static int getRange() {
        return _particleRange;
    }

    private void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, int... extra) throws Exception {
        sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, false, extra);
    }

    private void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force, int... extra) throws Exception {
        if((!force) && (!isPlayerInRange(player, location))) {
          return;
        }
        //if(ReflectionUtilities.getVersion().contains("v1_8")) {
            try {
                if(_nmsEnumParticle == null)
                    _nmsEnumParticle = ReflectionUtilities.getNMSClass("EnumParticle");
                if(this == BLOCK_CRACK) {
                    int id = 0;
                    int data = 0;
                    if (extra.length > 0) {
                      id = extra[0];
                    }
                    if (extra.length > 1) {
                      data = extra[1];
                    }
                    extra = new int[] { id, id | data << 12 };
                }
                Object packet = _nmsPacketPlayOutParticle.getConstructor(new Class[] { _nmsEnumParticle, Boolean.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE, int[].class }).newInstance(new Object[] { getEnum(_nmsEnumParticle.getName() + "." + (_enumValue != null ? _enumValue : name().toUpperCase())), true, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, count, extra });
                Object handle = ReflectionUtilities.getHandle(player);
                Object connection = ReflectionUtilities.getField(handle.getClass(), "playerConnection").get(handle);
                ReflectionUtilities.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] { packet });
            } catch (Exception e) {
                throw e;
            }
        /*} else {
            try {
                if(_particleName == null)
                    _particleName = name().toLowerCase();
                
                String name = _particleName;
                if((this == BLOCK_CRACK) || (this == ITEM_CRACK) || (this == BLOCK_DUST)) {
                    int id = 0;
                    int data = 0;
                    if(extra.length > 0) 
                        id = extra[0];

                    if(extra.length > 1)
                        data = extra[1];
  
                    name = name + id + "_" + data;
                }
                Object packet = _nmsPacketPlayOutParticle.getConstructor(new Class[] { String.class, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Float.TYPE, Integer.TYPE }).newInstance(new Object[] { name, (float)location.getX(), (float)location.getY(), (float)location.getZ(), offsetX, offsetY, offsetZ, speed, count});
                Object handle = ReflectionUtilities.getHandle(player);
                Object connection = ReflectionUtilities.getField(handle.getClass(), "playerConnection").get(handle);
                ReflectionUtilities.getMethod(connection.getClass(), "sendPacket", new Class[0]).invoke(connection, new Object[] { packet });
            } catch (Exception e) {
                throw e;
            }
        }*/
    }

    public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force) throws Exception {
        sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, force, new int[0]);
    }

    public void sendToPlayer(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, false);
    }

    public void sendToPlayers(Collection<? extends Player> players, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        for(Player p : players)
            sendToPlayer(p, location, offsetX, offsetY, offsetZ, speed, count);
    }
  
    public void sendToPlayers(Collection<? extends Player> players, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force) throws Exception {
        for(Player p : players)
            sendToPlayer(p, location, offsetX, offsetY, offsetZ, speed, count, force);
    }
  
    public void sendToPlayers(Player[] players, Location location, float offsetX, float offsetY, float offsetZ, float speed, int count) throws Exception {
        for (Player p : players)
            sendToPlayer(p, location, offsetX, offsetY, offsetZ, speed, count);
    }
  
    public void sendColor(Player p, Location location, org.bukkit.Color color) throws Exception {
        if(!_hasColor)
            return;
        sendToPlayer(p, location, getColor(color.getRed()), getColor(color.getGreen()), getColor(color.getBlue()), 1.0F, 0);
    }
  
    public void sendColor(Player p, Location location, org.bukkit.Color color, boolean force) throws Exception {
        if(!_hasColor)
            return;
        sendToPlayer(p, location, getColor(color.getRed()), getColor(color.getGreen()), getColor(color.getBlue()), 1.0F, 0, force);
    }
  
    @Deprecated
    public void sendColor(Player p, Location location, java.awt.Color color) throws Exception {
        if(!_hasColor)
            return;
        sendToPlayer(p, location, getColor(color.getRed()), getColor(color.getGreen()), getColor(color.getBlue()), 1.0F, 0);
    }
  
    public void sendColor(Player p, Location location, java.awt.Color color, boolean force) throws Exception {
        if(!_hasColor)
            return;
        sendToPlayer(p, location, getColor(color.getRed()), getColor(color.getGreen()), getColor(color.getBlue()), 1.0F, 0, force);
    }
  
    @Deprecated
    public void sendColor(Collection<? extends Player> players, Location location, java.awt.Color color) throws Exception {
        if(!_hasColor)
            return;
        for(Player p : players)
            sendColor(p, location, color);
    }

    public void sendColor(Collection<? extends Player> players, Location location, java.awt.Color color, boolean force) throws Exception {
        if(!_hasColor)
            return;
        for(Player p : players)
            sendColor(p, location, color, force);
    }

    public void sendColor(Collection<? extends Player> players, Location location, org.bukkit.Color color) throws Exception {
        if(!_hasColor)
             return;
        for(Player p : players)
            sendColor(p, location, color);
    }

    @Deprecated
    public void sendColor(Collection<? extends Player> players, Location location, org.bukkit.Color color, boolean force) throws Exception {
        if(!_hasColor)
            return;
        for(Player p : players)
            sendColor(p, location, color, force);
    }

    @Deprecated
    public void sendColor(Player[] players, Location location, org.bukkit.Color color) throws Exception {
        if(!_hasColor)
            return;
        for(Player p : players)
            sendColor(p, location, color);
    }

    @Deprecated
    public void sendColor(Player[] players, Location location, java.awt.Color color) throws Exception {
        if(!_hasColor)
            return;
        for(Player p : players)
            sendColor(p, location, color);
    }

    protected float getColor(float value) {
        if(value <= 0.0F)
            value = -1.0F;
        return value / 255.0F;
    }
    
    public static void sendPacket(Object packet, Player player) {
        try {
            Object nms_player = _nmsNetworkGetVersion.invoke(player, new Object[0]);
            Object nms_connection = _nmsFieldPlayerConnection.get(nms_player);
            _nmsNetworkGetVersion.invoke(nms_connection, packet);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
  
  @Deprecated
  public void sendBlockCrack(Player player, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_CRACK) {
      throw new IllegalArgumentException("This method is only available for BLOCK_CRACK!");
    }
    sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, new int[] { id, data });
  }
  
  public void sendBlockCrack(Player player, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != BLOCK_CRACK) {
      throw new IllegalArgumentException("This method is only available for BLOCK_CRACK!");
    }
    sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, force, new int[] { id, data });
  }
  
  @Deprecated
  public void sendBlockCrack(Collection<? extends Player> players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_CRACK) {
      throw new IllegalArgumentException("This method is only available for BLOCK_CRACK!");
    }
    for (Player p : players) {
      sendBlockCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  public void sendBlockCrack(Collection<? extends Player> players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != BLOCK_CRACK) {
      throw new IllegalArgumentException("This method is only available for BLOCK_CRACK!");
    }
    for (Player p : players) {
      sendBlockCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count, force);
    }
  }
  
  @Deprecated
  public void sendBlockCrack(Player[] players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_CRACK) {
      throw new IllegalArgumentException("This method is only available for BLOCK_CRACK!");
    }
    for (Player p : players) {
      sendBlockCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  @Deprecated
  public void sendItemCrack(Player player, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != ITEM_CRACK) {
      throw new IllegalArgumentException("This method is only available for ITEM_CRACK!");
    }
    sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, new int[] { id, data });
  }
  
  public void sendItemCrack(Player player, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != ITEM_CRACK) {
      throw new IllegalArgumentException("This method is only available for ITEM_CRACK!");
    }
    sendToPlayer(player, location, offsetX, offsetY, offsetZ, speed, count, force, new int[] { id, data });
  }
  
  @Deprecated
  public void sendItemCrack(Collection<? extends Player> players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != ITEM_CRACK) {
      throw new IllegalArgumentException("This method is only available for ITEM_CRACK!");
    }
    for (Player p : players) {
      sendItemCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  public void sendItemCrack(Collection<? extends Player> players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != ITEM_CRACK) {
      throw new IllegalArgumentException("This method is only available for ITEM_CRACK!");
    }
    for (Player p : players) {
      sendItemCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count, force);
    }
  }
  
  @Deprecated
  public void sendItemCrack(Player[] players, Location location, int id, byte data, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != ITEM_CRACK) {
      throw new IllegalArgumentException("This method is only available for ITEM_CRACK!");
    }
    for (Player p : players) {
      sendItemCrack(p, location, id, data, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  @Deprecated
  public void sendBlockDust(Player p, Location location, int id, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_DUST) {
      throw new IllegalArgumentException("This method is only available for BLOCK_DUST!");
    }
    sendToPlayer(p, location, offsetX, offsetY, offsetZ, speed, count, new int[] { id });
  }
  
  public void sendBlockDust(Player p, Location location, int id, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != BLOCK_DUST) {
      throw new IllegalArgumentException("This method is only available for BLOCK_DUST!");
    }
    sendToPlayer(p, location, offsetX, offsetY, offsetZ, speed, count, force, new int[] { id });
  }
  
  @Deprecated
  public void sendBlockDust(Collection<? extends Player> players, Location location, int id, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_DUST) {
      throw new IllegalArgumentException("This method is only available for BLOCK_DUST!");
    }
    for (Player p : players) {
      sendBlockDust(p, location, id, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  public void sendBlockDust(Collection<? extends Player> players, Location location, int id, float offsetX, float offsetY, float offsetZ, float speed, int count, boolean force)
    throws Exception
  {
    if (this != BLOCK_DUST) {
      throw new IllegalArgumentException("This method is only available for BLOCK_DUST!");
    }
    for (Player p : players) {
      sendBlockDust(p, location, id, offsetX, offsetY, offsetZ, speed, count, force);
    }
  }
  
  @Deprecated
  public void sendBlockDust(Player[] players, Location location, int id, float offsetX, float offsetY, float offsetZ, float speed, int count)
    throws Exception
  {
    if (this != BLOCK_DUST) {
      throw new IllegalArgumentException("This method is only available for BLOCK_DUST!");
    }
    for (Player p : players) {
      sendBlockDust(p, location, id, offsetX, offsetY, offsetZ, speed, count);
    }
  }
  
  protected static int getVersion(Player p)
  {
    try
    {
      Object handle = ReflectionUtilities.getHandle(p);
      Object connection = _nmsFieldPlayerConnection.get(handle);
      Object network = _nmsFieldNetworkManager.get(connection);
      Object channel;
      if (ReflectionUtilities.getVersion().contains("1_7")) {
        channel = _nmsFieldNetworkManagerM.get(network);
      } else {
        channel = _nmsFieldNetworkManagerI.get(network);
      }
      Object version = ReflectionUtilities.getVersion().contains("1_7") ? _nmsNetworkGetVersion.invoke(network, new Object[] { channel }) : Integer.valueOf(47);
      return ((Integer)version).intValue();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return 0;
  }
  
  static
  {
    _nmsPacketPlayOutParticle = ReflectionUtilities.getNMSClass("PacketPlayOutWorldParticles");
    
    _particleRange = 25;
    
    String ver = ReflectionUtilities.getVersion();
    try
    {
      _nmsPlayerConnection = ReflectionUtilities.getNMSClass("PlayerConnection");
      _nmsEntityPlayer = ReflectionUtilities.getNMSClass("EntityPlayer");
      _ioNettyChannel = ver.contains("1_7") ? Class.forName("net.minecraft.util.io.netty.channel.Channel") : Class.forName("io.netty.channel.Channel");
      
      _nmsFieldPlayerConnection = ReflectionUtilities.getField(_nmsEntityPlayer, "playerConnection");
      _nmsFieldNetworkManager = ReflectionUtilities.getField(_nmsPlayerConnection, "networkManager");
      _nmsFieldNetworkManagerI = ReflectionUtilities.getField(_nmsFieldNetworkManager.getType(), "i");
      _nmsFieldNetworkManagerM = ReflectionUtilities.getField(_nmsFieldNetworkManager.getType(), "m");
      
      _nmsNetworkGetVersion = ReflectionUtilities.getMethod(_nmsFieldNetworkManager.getType(), "getVersion", new Class[] { _ioNettyChannel });
    }
    catch (Exception e)
    {
      System.err.println("[ParticleLIB] Error while loading: " + e.getMessage());
      e.printStackTrace(System.err);
      Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("ParticleLIB"));
    }
  }
  
  private static Enum<?> getEnum(String enumFullName) {
    String[] x = enumFullName.split("\\.(?=[^\\.]+$)");
    if (x.length == 2)
    {
      String enumClassName = x[0];
      String enumName = x[1];
      try {
        Class<Enum> cl = (Class<Enum>)Class.forName(enumClassName);
        return Enum.valueOf(cl, enumName);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    }
    return null;
  }
  
  public static boolean isPlayerInRange(Player p, Location center)
  {
    double distance = 0.0D;
    if (!p.getLocation().getWorld().equals(center.getWorld())) {
      return false;
    }
    if ((distance = center.distanceSquared(p.getLocation())) > Double.MAX_VALUE) {
      return false;
    }
    return distance < _particleRange * _particleRange;
  }
  
  public static class ReflectionUtilities
  {
    public static void setValue(Object instance, String fieldName, Object value)
      throws Exception
    {
      Field field = instance.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(instance, value);
    }
    
    public static Object getValue(Object instance, String fieldName)
      throws Exception
    {
      Field field = instance.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(instance);
    }
    
    public static String getVersion()
    {
      String name = Bukkit.getServer().getClass().getPackage().getName();
      String version = name.substring(name.lastIndexOf('.') + 1) + ".";
      return version;
    }
    
    public static Class<?> getNMSClass(String className)
    {
      String fullName = "net.minecraft.server." + getVersion() + className;
      Class<?> clazz = null;
      try
      {
        clazz = Class.forName(fullName);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return clazz;
    }
    
    public static Class<?> getOBCClass(String className)
    {
      String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
      Class<?> clazz = null;
      try
      {
        clazz = Class.forName(fullName);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return clazz;
    }
    
    public static Object getHandle(Object obj)
    {
      try
      {
        return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return null;
    }
    
    public static Field getField(Class<?> clazz, String name)
    {
      try
      {
        Field field = clazz.getDeclaredField(name);
        field.setAccessible(true);
        return field;
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      return null;
    }
    
    public static Method getMethod(Class<?> clazz, String name, Class<?>... args)
    {
      for (Method m : clazz.getMethods()) {
        if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes()))))
        {
          m.setAccessible(true);
          return m;
        }
      }
      return null;
    }
    
    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2)
    {
      boolean equal = true;
      if (l1.length != l2.length) {
        return false;
      }
      for (int i = 0; i < l1.length; i++) {
        if (l1[i] != l2[i])
        {
          equal = false;
          break;
        }
      }
      return equal;
    }
  }
}

