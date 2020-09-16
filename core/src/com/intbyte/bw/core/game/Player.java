package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.utils.ID;


public class Player extends Entity {


    static Player player;
    private Container[] inventory;

    protected Player() {
        super();
        carriedItem = new Container(64);
        id = ID.registeredId("entity:player", 0);
        inventory = new Container[36];
        for (int i = 0; i < 36; i++) {
            inventory[i] = new Container(64);
        }
        Gdx.app.log("PLAYER","player is initialized");
    }

    public static Player getPlayer() {
        if (player == null)
            player = new Player();
        return player;
    }

    @Override
    public void render() {

    }
}
