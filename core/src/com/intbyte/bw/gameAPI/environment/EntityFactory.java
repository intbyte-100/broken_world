package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.ID;

public abstract class EntityFactory {
    private int id;
    public EntityFactory(String strId, int id){
        this.id = ID.registeredId("entity:"+strId, id);
    }

    public EntityFactory(String strId){
        this.id = ID.registeredId("entity:"+strId);
    }

    public final int getId() {
        return id;
    }

    protected abstract Entity createEntity();

    public final Entity create(){
        Entity entity = createEntity();
        entity.setId(id);
        return entity;
    }
}
