package com.intbyte.bw.gameAPI.physic;

public class PhysicData {
    public static final int BLOCK = 0, ENTITY = 1, DROP = 2;
    protected int type;
    protected Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public Object getObject() {
        return object;
    }
}
