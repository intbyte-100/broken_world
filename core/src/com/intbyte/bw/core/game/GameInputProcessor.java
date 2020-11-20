package com.intbyte.bw.core.game;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.intbyte.bw.gameAPI.callbacks.CallBack;

import static com.intbyte.bw.core.game.Player.player;


public class GameInputProcessor implements InputProcessor {
    private static boolean isReadyCallBack;
    private final PerspectiveCamera camera;
    private Vector3 position;
    private float x, z;

    public GameInputProcessor(PerspectiveCamera camera) {
        this.camera = camera;
        position = new Vector3();
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }

    public static Vector3 getFastBlock(PerspectiveCamera camera, Vector3 position, int screenX, int screenY) {
        Ray ray = camera.getPickRay(screenX, screenY);
        return position.set(ray.direction).
                scl(-ray.origin.y / ray.direction.y).
                add(ray.origin).
                add(0, 0, 0.1f);
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
        x = (float) (player.getX() / 10 - Math.floor(player.getX() / 10));
        z = (float) (player.getZ() / 10 - Math.floor(player.getZ() / 10));

        isReadyCallBack = true;
        CallBack.executeTouchedCallBacks(position);
        CallBack.executeTouchOnBlockCallBack(Math.round(position.x - x), (int) (position.z - z));
        isReadyCallBack = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Ray ray = camera.getPickRay(screenX, screenY);
        final float distance = -ray.origin.y / ray.direction.y;
        position.set(ray.direction).scl(distance).add(ray.origin).add((float) player.getX(), 0, (float) player.getZ()).scl(0.1f).add(0, 0, 1);


        isReadyCallBack = true;
        CallBack.executeDraggedCallBacks(position);
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
