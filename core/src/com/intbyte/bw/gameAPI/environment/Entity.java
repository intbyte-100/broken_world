package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.utils.ID;

import static com.intbyte.bw.gameAPI.graphic.Graphic.BLOCK_SIZE;

public abstract class Entity {

    protected static final Player player = Player.getPlayer();;
    private static final EntityFactory[] factories = new EntityFactory[12000];
    public int id;
    protected double x, z;
    protected float health, width, height, rotate;
    protected int maxHealth = 100;

    protected Container carriedItem = carriedItem = new Container(64);


    public static void addFactory(EntityFactory entityFactory){
        factories[entityFactory.getId()] = entityFactory;
    }


    public static Entity spawn(Entity entity) {
        GameThread.getEntityManager().add(entity);
        return entity;
    }

    public static Entity spawn(int id,float x, float z){
        Entity entity = spawn(factories[id].create());
        entity.setTranslateToBlock(x,z-0.5);
        Gdx.app.log("ENTITY","spawned entity with id "+id+"; x = "+entity.getX()+"; z = "+entity.getZ());
        return entity;
    }

    public static Entity spawn(String id, float x, float z){
        return spawn(ID.get("entity:"+id),x,z);
    }
    public void calculateModelPositionAndRotation(float x, float y, float z) {
        getEntityModel().transform.setToTranslation((float) (getX() - player.getX() + GameThread.xDraw) + x, y, (float) (getZ() - player.getZ() + GameThread.zDraw) + z);
        getEntityModel().transform.rotateRad(Vector3.Y, rotate);
    }

    @Override
    public String toString() {
        return getClass().toString() + " " + hashCode();
    }

    public void tick() {

    }


    public ModelInstance getEntityModel() {
        throw new RuntimeException("this method is not implements");
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


    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
}
