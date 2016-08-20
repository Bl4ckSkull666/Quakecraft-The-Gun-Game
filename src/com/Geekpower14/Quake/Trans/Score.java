/*
 * Decompiled with CFR 0_114.
 */
package com.Geekpower14.Quake.Trans;

public class Score {

    public static enum Type {
        Win("Wins"),
        Kill("Kills"),
        Death("Deaths"),
        Shot("Shots"),
        TeamWin("TeamWins");
        
        String _type;

        private Type(String s) {
            _type = s;
        }

        @Override
        public String toString() {
            return _type;
        }
    }

}

