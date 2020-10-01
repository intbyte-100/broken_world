package com.intbyte.bw.gameAPI.environment;

public class ItemData {

    protected int strength;
    protected int level;
    protected int maxStrength;
    protected int damage;
    public ItemData(int maxStrength, int damage, int level) {
        this.damage = damage;
        this.maxStrength = maxStrength;
        strength = maxStrength;
        this.level = level;
    }

    public int getLevel() {
        return 0;
    }

    public int getMaxStrength() {
        return maxStrength;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        if (strength > -1 && strength < +getMaxStrength())
            this.strength = strength;
    }

    public void decrementStrength() {
        if (strength > 0) strength--;
    }

    public int getDamage() {
        return damage;
    }
}
