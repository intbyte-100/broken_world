package com.intbyte.bw.engine.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.GameThread;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.physic.Physic;
import com.intbyte.bw.engine.physic.PhysicData;
import com.intbyte.bw.engine.utils.ID;

public abstract class Entity {

    protected static final Player player = Player.getPlayer();;
    private static final EntityFactory[] factories = new EntityFactory[1200];
    public int id;
    protected double x, z;
    protected float health, width, height, rotate, endurance;
    protected int maxHealth = 100, maxEndurance = 100;
    protected Body body;
    protected int bodyID;
    protected Vector2 position = new Vector2();
    protected Array<Container> inventory = new Array<>();
    protected double carrying;
    protected Container carriedItem = new Container(64);
    protected double itemsWeight;
    protected Vector3 speed = new Vector3(1,0,1);
    protected Vector3 maxSpeed = new Vector3(1,0,1);


    public static void addFactory(EntityFactory entityFactory){
        factories[entityFactory.getId()] = entityFactory;
    }


    public static Entity spawn(Entity entity) {
        GameThread.getEntityManager().add(entity);
        return entity;
    }

    public static Entity spawn(int id,float x, float z){
        Entity entity = spawn(factories[id].create());
        entity.setPosition(x,z-0.5f);
        Gdx.app.log("ENTITY","spawned entity with id "+id+"; x = "+entity.getX()+"; z = "+entity.getZ());
        return entity;
    }

    public static Entity spawn(String id, float x, float z){
        return spawn(ID.get("entity:"+id),x,z);
    }
    public void calculateModelPositionAndRotation(float x, float y, float z) {
        getEntityModel().transform.setToTranslation((float) (getPixelX() - player.getPixelX() + GameThread.xDraw) + x, y, (float) (getPixelZ() - player.getPixelZ() + GameThread.zDraw) + z);
        getEntityModel().transform.rotateRad(Vector3.Y, rotate);
    }

    @Override
    public String toString() {
        return getClass().toString() + " " + hashCode();
    }

    public void renderTick() {
        increaseEndurance((100/6*Gdx.graphics.getRawDeltaTime()));
        position = body.getTransform().getPosition();
        rotate = -body.getTransform().getRotation();
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

    public double getPixelX() {
        return position.x*10;
    }


    public double getPixelZ() {
        return position.y*10;
    }

    public float getX() {
        return position.x;
    }

    public float getZ() {
        return position.y;
    }


    public void setPosition(Vector2 position) {
        body.setTransform(position,rotate);
    }

    public void setPosition(float x, float z){
        body.setTransform(x,z,rotate);
    }

    public Container getCarriedItem() {
        return carriedItem;
    }


    protected void spawn(){
        PhysicData data = (PhysicData) getBody().getUserData();
        data.setObject(this);
        data.setType(data.ENTITY);
    }

    abstract public void render();


    public void delete(){
        Physic.getBodyFactory(bodyID).addBody(body);
        GameThread.getEntityManager().delete(this);
    }
    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public float getEndurance() {
        return endurance;
    }

    public void increaseEndurance(float endurance){
        this.endurance+=endurance;
        if (this.endurance > maxEndurance) this.endurance = maxEndurance;
        else if (this.endurance < 0) this.endurance = 0;
    }

    public int getMaxEndurance() {
        return maxEndurance;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
        body.setTransform(body.getPosition(),-rotate);
    }

    public float getRotate() {
        return rotate;
    }

    public Body getBody() {
        return body;
    }

    public boolean takeDrop(Container container){
        return false;
    }

    public void setCarrying(double carrying) {
        this.carrying = carrying;
    }

    public void tick(){

    }

    public double getSummaryWeight(){
        double weight = 0;
        for(Container i: inventory)
            if(i.getItems().notEmpty())
                weight+=i.getCountItems()*i.getItems().get(0).getWeight();
        return weight;
    }
    public double getCarrying() {
        return carrying;
    }

    public double getItemsWeight() {
        return itemsWeight;
    }

    public void setItemsWeight(double itemsWeight) {
        this.itemsWeight = itemsWeight;
    }

    public Vector3 getSpeed() {
        return speed;
    }

    public Vector3 getMaxSpeed() {
        return maxSpeed;
    }
}
