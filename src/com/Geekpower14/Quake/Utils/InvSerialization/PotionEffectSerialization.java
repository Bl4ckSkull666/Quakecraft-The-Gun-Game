package com.Geekpower14.Quake.Utils.InvSerialization;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectSerialization {
    protected PotionEffectSerialization() {
    }

    public static String serializeEffects(Collection<PotionEffect> effects) {
        String serialized = "";
        for (PotionEffect e : effects) {
            serialized = serialized + e.getType().getId() + ":" + e.getDuration() + ":" + e.getAmplifier() + ";";
        }
        return serialized;
    }

    public static Collection<PotionEffect> getPotionEffects(String serializedEffects) {
        ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
        if (serializedEffects.isEmpty()) {
            return effects;
        }
        String[] effs = serializedEffects.split(";");
        for (int i = 0; i < effs.length; ++i) {
            String[] effect = effs[i].split(":");
            if (effect.length < 3) {
                throw new IllegalArgumentException(serializedEffects + " - PotionEffect " + i + " (" + effs[i] + "): split must at least have a length of 3");
            }
            if (!Util.isNum(effect[0])) {
                throw new IllegalArgumentException(serializedEffects + " - PotionEffect " + i + " (" + effs[i] + "): id is not an integer");
            }
            if (!Util.isNum(effect[1])) {
                throw new IllegalArgumentException(serializedEffects + " - PotionEffect " + i + " (" + effs[i] + "): duration is not an integer");
            }
            if (!Util.isNum(effect[2])) {
                throw new IllegalArgumentException(serializedEffects + " - PotionEffect " + i + " (" + effs[i] + "): amplifier is not an integer");
            }
            int id = Integer.parseInt(effect[0]);
            int duration = Integer.parseInt(effect[1]);
            int amplifier = Integer.parseInt(effect[2]);
            PotionEffectType effectType = PotionEffectType.getById((int)id);
            if (effectType == null) {
                throw new IllegalArgumentException(serializedEffects + " - PotionEffect " + i + " (" + effs[i] + "): no PotionEffectType with id of " + id);
            }
            PotionEffect e = new PotionEffect(effectType, duration, amplifier);
            effects.add(e);
        }
        return effects;
    }

    public static void addPotionEffects(String code, LivingEntity entity) {
        entity.addPotionEffects(PotionEffectSerialization.getPotionEffects(code));
    }

    public static void setPotionEffects(String code, LivingEntity entity) {
        for (PotionEffect effect : entity.getActivePotionEffects()) {
            entity.removePotionEffect(effect.getType());
        }
        PotionEffectSerialization.addPotionEffects(code, entity);
    }
}

