package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.math.*;

import java.util.Arrays;

public class BlockPhysicEntity implements PhysicEntity{
    Vector3 position = new Vector3();
    Shape2D shape2D;
    Vector2 center = new Vector2();
    @Override
    public boolean isCollision(PhysicEntity entity) {
        return false;
    }

    @Override
    public boolean contains(float x, float y, float z) {
        return false;
    }

    public boolean containsXZ(float x, float z){
        return shape2D.contains(x, z);
    }
    @Override
    public Vector3 getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3 position) {
        this.position.set(position);
    }

    @Override
    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }



    public void setShape(Shape2D shape2D) {
        this.shape2D = shape2D;
    }

}
