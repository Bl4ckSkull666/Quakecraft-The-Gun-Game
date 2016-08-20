/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 */
package com.Geekpower14.Quake.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.bukkit.Bukkit;

public class Reflection {
    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String version = String.valueOf(name.substring(name.lastIndexOf(46) + 1)) + ".";
        return version;
    }

    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + Reflection.getVersion() + className;
        Class clazz = null;
        try {
            clazz = Class.forName(fullName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Enum<?> getNMSEnum(String className) {
        String enumFullName = "net.minecraft.server." + Reflection.getVersion() + className;
        String[] x = enumFullName.split("\\.(?=[^\\.]+$)");
        if (x.length == 2) {
            String enumClassName = x[0];
            String enumName = x[1];
            try {
                Class cl = Class.forName(enumClassName);
                return Enum.valueOf(cl, enumName);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Class<?> getOBCClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + Reflection.getVersion() + className;
        Class clazz = null;
        try {
            clazz = Class.forName(fullName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public static Object getHandle(Object obj) {
        try {
            return Reflection.getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getMethod(Class<?> clazz, String name, Class<?> ... args) {
        Method[] arrmethod = clazz.getMethods();
        int n = arrmethod.length;
        int n2 = 0;
        while (n2 < n) {
            Method m = arrmethod[n2];
            if (m.getName().equals(name) && (args.length == 0 || Reflection.ClassListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
            ++n2;
        }
        return null;
    }

    public static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        int i = 0;
        while (i < l1.length) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
            ++i;
        }
        return equal;
    }
}

