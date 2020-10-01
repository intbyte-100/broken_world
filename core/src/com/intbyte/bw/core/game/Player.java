package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.EntityFactory;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.utils.ID;


public class Player extends Entity {
    static Player player;
    private Container[] inventory;
    float coolDown;
    protected Player() {
        inventory = new Container[36];
        for (int i = 0; i < 36; i++) {
            inventory[i] = new Container(64);
        }
        Gdx.app.log("PLAYER","player is initialized");
    }

    @Override
    public void tick() {
        if(0 < coolDown){
            coolDown-=10*Gdx.graphics.getRawDeltaTime();
        }else
            coolDown = 0;
    }

    public static Player getPlayer() {
        if (player == null)
            player = (Player) new PlayerFactory().create();
        return player;
    }

    @Override
    public void render() {

    }

    private static class PlayerFactory extends EntityFactory{

        public PlayerFactory() {
            super("player", 0);
        }

        @Override
        protected Entity createEntity() {
            return new Player();
        }
    }
}

