package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.math.Vector2;

public class Tile {
    Vector2 position = new Vector2();

    public void setPosition(Vector2 position) {
        this.position.set(position);
    }

    public void setPosition(float x, float z) {
        this.position.set(x, z);
    }
}
