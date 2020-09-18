package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;

import static com.intbyte.bw.gameAPI.graphic.Graphic.BLOCK_SIZE;

public abstract class Entity {

    protected static  Vector3 modelPosition = new Vector3();
    protected static final Player player = Player.getPlayer();
    protected double x, z;
    protected float health, width, height;
    protected int maxHealth = 100;
    protected int id;
    protected Container carriedItem;


    public static void spawn(Entity entity){
        GameThread.getEntityManager().add(entity);
    }
    public Entity() {
        carriedItem = new Container(64);
    }


    @Override
    public String toString() {
        return getClass().toString() + " " + hashCode();
    }

    public void tick() {

    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float value) {
        if (value >= 0)
            if (value <= maxHealth)
                health = value;
            else
                health = maxHealth;
        else health = 0;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getXOnBlock() {
        return x / BLOCK_SIZE;
    }

    public void setXOnBlock(double xOnBlock) {
        this.x = xOnBlock * BLOCK_SIZE;
    }

    public double getZOnBlock() {
        return z / BLOCK_SIZE;
    }

    public void setZOnBlock(double yOnBlock) {
        this.z = yOnBlock * BLOCK_SIZE;
    }

    public void translate(double x, double z) {
        this.x += x;
        this.z += z;
    }

    public void translateBlock(double x, double z) {
        this.x += x * BLOCK_SIZE;
        this.z += z * BLOCK_SIZE;
    }

    public void setTranslateToBlock(double x, double z) {
        this.x = x * BLOCK_SIZE;
        this.z = z * BLOCK_SIZE;
    }

    public Container getCarriedItem() {
        return carriedItem;
    }


    abstract public void render();


}
