package com.intbyte.bw.gameAPI.physic;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.intbyte.bw.gameAPI.utils.ID;


public class PhysicBlockObject implements PhysicObject {
    Vector3 position = new Vector3();
    Shape2D shape2D;
    Vector2 offset = new Vector2();
    int bodyID;


    public PhysicBlockObject(String bodyID){
        this.bodyID = ID.get("2dBody:"+bodyID);
    }

    public PhysicBlockObject(){
        this("default");
    }

    public boolean containsXZ(float x, float z) {
        return shape2D.contains(x - position.x + offset.x, z - position.z + offset.y);
    }

    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    public void setOffset(float x, float y) {
        this.offset.set(x, y);
    }

    public void setShape(Shape2D shape2D) {
        this.shape2D = shape2D;
    }

    public Body getBody(){
        return Physic.allocateBody(bodyID);
    }

    public int getBodyID(){
        return bodyID;
    }
}
