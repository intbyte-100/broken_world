package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.gameAPI.environment.Entity;

class EntityManager {
    protected final Thread entityThread;
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


        new EntityGarbageCollector().start();
        entityThread = new Thread() {
            @Override
            public void run() {
                for (Entity entity : active) {
                    entity.tick();
                }
            }
        };
    }

    public Array<Entity> getActive() {
        return active;
    }

    class EntityGarbageCollector extends Thread {
        @Override
        public void run() {
            activeRect.setCenter((float) player.getXOnBlock(), (float) player.getZOnBlock());
            nonActiveRect.setCenter((float) player.getXOnBlock(), (float) player.getZOnBlock());
            for (int j = 0; j < active.size; j++) {
                Entity i = active.get(j);
                if (!activeRect.contains((float) i.getXOnBlock(), (float) i.getZOnBlock())) {
                    nonActive.add(i);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is not active").toString());
                    active.removeIndex(j);
                }
            }
            for (int j = 0; j < nonActive.size; j++) {
                Entity i = nonActive.get(j);
                if (activeRect.contains((float) i.getXOnBlock(), (float) i.getZOnBlock())) {
                    active.add(i);
                    nonActive.removeIndex(j);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is active").toString());
                } else if (!nonActiveRect.contains((float) i.getXOnBlock(), (float) i.getZOnBlock())) {
                    nonActive.removeIndex(j);
                    builder.setLength(0);
                    Gdx.app.log("ENTITY MANAGER", builder.append(i.getClass().getName()).append(" ").append(i.hashCode()).append(" is deleted").toString());
                }
            }
            try {
                sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new EntityGarbageCollector().start();
        }
    }
}
