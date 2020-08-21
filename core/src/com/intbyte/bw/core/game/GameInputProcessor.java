package com.intbyte.bw.core.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.environment.World;


public class GameInputProcessor implements InputProcessor {
    private final PerspectiveCamera camera;
    private Vector3 position;

    private static boolean isReadyCallBack;

    public GameInputProcessor(PerspectiveCamera camera) {
        this.camera = camera;
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Ray ray = camera.getPickRay(screenX, screenY);
        final float distance = -ray.origin.y / ray.direction.y;

        position = new Vector3();
        position.set(ray.direction).scl(distance).add(ray.origin).add((float) Player.player.getX(), 0, (float) Player.player.getZ()).scl(0.1f).add(0, 0, 1);


        isReadyCallBack = true;
        CallBack.executeTouchCallBacks(position);
        CallBack.executeTouchOnBlockCallBack(Math.round(position.x), (int) position.z);
        isReadyCallBack = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Ray ray = camera.getPickRay(screenX, screenY);
        final float distance = -ray.origin.y / ray.direction.y;
        position = new Vector3();
        position.set(ray.direction).scl(distance).add(ray.origin).add((float) Player.player.getX(), -10, (float) Player.player.getZ()).scl(0.1f).add(0, 0, 1);


        isReadyCallBack = true;
        CallBack.executeTouchCallBacks(position);
        isReadyCallBack = false;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
