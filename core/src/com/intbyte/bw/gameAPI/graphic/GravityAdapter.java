package com.intbyte.bw.gameAPI.graphic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.HashSet;

import static com.intbyte.bw.gameAPI.graphic.Graphic.screenHeight;
import static com.intbyte.bw.gameAPI.graphic.Graphic.screenWidth;
import static com.intbyte.bw.gameAPI.graphic.GravityAttribute.*;


public class GravityAdapter {
    private Array<ActorAdapter> adapters = new Array<>();

    public void addActor(Actor actor) {
        adapters.add(new ActorAdapter(actor));
    }

    public void setGravity(Integer... gravity) {
        adapters.get(adapters.size - 1).gravity.addAll(Arrays.asList(gravity));
    }

    public void setHeight(float height) {
        adapters.get(adapters.size - 1).actor.setHeight(height);
    }

    public void setWidth(float height) {
        adapters.get(adapters.size - 1).actor.setWidth(height);
    }

    public void tiedTo(int side, Actor actor) {
        ActorAdapter adapter = adapters.get(adapters.size - 1);
        switch (side) {
            case LEFT:
                adapter.tiedToLeft = actor;
                break;
            case RIGHT:
                adapter.tiedToRight = actor;
                break;
            case TOP:
                adapter.tiedToTop = actor;
                break;
            case BOTTOM:
                adapter.tiedToBottom = actor;
                break;
        }
    }

    public void margin(float x, float y) {
        ActorAdapter adapter = adapters.get(adapters.size - 1);
        adapter.translate[0] += x;
        adapter.translate[1] += y;
    }

    private void setGravity(ActorAdapter adapter) {
        if (adapter.gravity.contains(CENTER)) {
            adapter.actor.setX(screenWidth / 2 - adapter.actor.getWidth() / 2);
            adapter.actor.setY(screenHeight / 2 - adapter.actor.getHeight() / 2);
        }
        if (adapter.gravity.contains(BOTTOM))
            adapter.actor.setY(0);
        if (adapter.gravity.contains(TOP))
            adapter.actor.setY(screenHeight - adapter.actor.getHeight());
        if (adapter.gravity.contains(LEFT))
            adapter.actor.setX(0);
        if (adapter.gravity.contains(RIGHT))
            adapter.actor.setX(screenWidth - adapter.actor.getWidth());
    }

    private void tiedToActor(ActorAdapter adapter) {
        if (adapter.tiedToLeft != null)
            adapter.actor.setX(adapter.tiedToLeft.getX() + adapter.tiedToLeft.getWidth());
        if (adapter.tiedToRight != null)
            adapter.actor.setX(adapter.tiedToRight.getX() - adapter.tiedToRight.getWidth());
        if (adapter.tiedToTop != null)
            adapter.actor.setY(adapter.tiedToTop.getY() + adapter.tiedToTop.getHeight());
        if (adapter.tiedToBottom != null)
            adapter.actor.setY(adapter.tiedToBottom.getY() - adapter.tiedToBottom.getHeight());
    }

    public void apply() {
        for (ActorAdapter i : adapters) {
            setGravity(i);
            tiedToActor(i);
            i.actor.moveBy(i.translate[0], i.translate[1]);
        }
    }

    private static class ActorAdapter {
        HashSet<Integer> gravity = new HashSet<>();
        Actor tiedToLeft, tiedToRight, tiedToTop, tiedToBottom;
        float[] translate = new float[2];
        Actor actor;

        ActorAdapter(Actor actor) {
            this.actor = actor;
        }

    }
}