package com.intbyte.bw.gameAPI.physic;

import com.badlogic.gdx.math.Vector3;

public interface PhysicEntity {
    boolean isCollision(PhysicEntity entity);
    boolean contains(float x, float y, float z);
    Vector3 getPosition();
    void setPosition(Vector3 position);
    void setPosition(float x, float y, float z);
}
