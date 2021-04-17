package com.intbyte.bw.engine.item;

public class ItemData {

    protected int strength;
    protected int level;
    protected int maxStrength;
    protected int damage;
    protected float coolDown, takeEndurance;
    public ItemData(int maxStrength, int damage, int level, float coolDown, float takeEndurance) {
        this.damage = damage;
        this.maxStrength = maxStrength;
        this.strength = maxStrength;
        this.level = level;
        this.takeEndurance = takeEndurance;
        this.coolDown = coolDown;
    }

    public int getLevel() {
        return level;
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

    public float getCoolDown() {
        return coolDown;
    }

    public float getTakeEndurance() {
        return takeEndurance;
    }
}
