package com.intbyte.bw.engine.physic;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.intbyte.bw.engine.utils.ID;

public class PhysicEntityObject implements PhysicObject {
    int bodyID;

    public PhysicEntityObject(){
        this("defaultEntity");
    }
    public PhysicEntityObject(String bodyId){
        this.bodyID = ID.get("2dBody:"+bodyId);
    }

    @Override
    public Vector3 getPosition() {
        return null;
    }

    @Override
    public void setPosition(Vector3 vector3) {

    }

    public Body getBody(){
        return Physic.allocateBody(bodyID);
    }

    public int getBodyID(){
        return bodyID;
    }
}
