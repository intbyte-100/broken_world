package com.intbyte.bw.core.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.environment.World;

import static com.intbyte.bw.core.game.Player.player;


public class GameInputProcessor implements InputProcessor {
    private final PerspectiveCamera camera;
    private Vector3 position;
    private float x, z;

    private static boolean isReadyCallBack;

    public GameInputProcessor(PerspectiveCamera camera) {
        this.camera = camera;
        position = new Vector3();
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

        position.set(ray.direction).scl(distance).add(ray.origin).add((float) player.getX(), 0, (float) player.getZ()).scl(0.1f).add(0, 0, 1);


        isReadyCallBack = true;
        CallBack.executeTouchCallBacks(position);
        CallBack.executeTouchOnBlockCallBack(Math.round(position.x-GameThread.xDraw), (int) (position.z-GameThread.zDraw));
        isReadyCallBack = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Ray ray = camera.getPickRay(screenX, screenY);
        final float distance = -ray.origin.y / ray.direction.y;
        position.set(ray.direction).scl(distance).add(ray.origin).add((float) player.getX(), -10, (float) player.getZ()).scl(0.1f).add(0, 0, 1);


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
