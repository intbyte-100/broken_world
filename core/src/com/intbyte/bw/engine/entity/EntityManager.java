package com.intbyte.bw.engine.entity;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.entity.Player;

public class EntityManager extends EntitySystem {
    private final Array<Entity> active, nonActive;
    private final Player player;
    private final StringBuilder builder;
    private final Rectangle activeRect, nonActiveRect;

    public EntityManager() {
        builder = new StringBuilder();

        active = new Array<>();
        nonActive = new Array<>();

        player = Player.getPlayer();

        activeRect = new Rectangle();
        activeRect.height = 48 * 2;
        activeRect.width = 48 * 2;

        nonActiveRect = new Rectangle();
        nonActiveRect.height = 80 * 2;
        nonActiveRect.width = 80 * 2;


        active.add(player);
        new EntityGarbageCollector().start();
    }

    public Array<Entity> getActive() {
        return active;
    }
    public void delete(Entity entity){
        if(!active.removeValue(entity,true))
            nonActive.removeValue(entity,true);
    }



    public void add(Entity entity){
        nonActive.add(entity);
    }

    class EntityGarbageCollector extends Thread {
        @Override
        public void run() {
            activeRect.setCenter((float) player.getX(), (float) player.getZ());
            nonActiveRect.setCenter((float) player.getX(), (float) player.getZ());
            for (int j = 0; j < active.size; j++) {
                Entity i = active.get(j);
                i.tick();
                if (!activeRect.contains((float) i.getX(), (float) i.getZ())) {
                    nonActive.add(i);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is not active").toString());
                    active.removeIndex(j);
                }
            }
            for (int j = 0; j < nonActive.size; j++) {
                Entity i = nonActive.get(j);
                if (activeRect.contains((float) i.getX(), (float) i.getZ())) {
                    active.add(i);
                    nonActive.removeIndex(j);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is active").toString());
                } else if (!nonActiveRect.contains((float) i.getX(), (float) i.getZ())) {
                    nonActive.removeIndex(j);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is deleted").toString());
                }
            }
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new EntityGarbageCollector().start();
        }
    }
}


