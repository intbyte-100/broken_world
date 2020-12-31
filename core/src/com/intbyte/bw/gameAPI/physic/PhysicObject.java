package com.intbyte.bw.gameAPI.physic;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;

public interface PhysicObject {
    Body getBody();
    Vector3 getPosition();
    void setPosition(Vector3 position);
    int getBodyID();
}
