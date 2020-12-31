package com.intbyte.bw.gameAPI.physic;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class BodyFactory {
    private final Array<Body> bodies2d = new Array<>();

    protected abstract Body getBody(World world);

    final Body allocate2dBody(World world) {
        if (bodies2d.isEmpty())
            return getBody(world);
        else return bodies2d.peek();
    }

    public final void addBody(Body body) {
        bodies2d.add(body);
    }
}
