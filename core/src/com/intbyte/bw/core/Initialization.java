package com.intbyte.bw.core;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.game.Game;
import com.intbyte.bw.gameAPI.callbacks.CallBack;

import java.util.Iterator;

import com.intbyte.bw.GameBoot;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public class Initialization implements Screen {

    private GameBoot boot;
    private Texture texture;
    private boolean isRendered;
    private static boolean isReadyCallBack;

    public Initialization(final GameBoot boot) {
        this.boot = boot;
        texture = new Texture(Gdx.files.internal("android.jpg"));
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }

    @Override
    public void render(float p1) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        BATCH.begin();
        BATCH.draw(texture, Gdx.graphics.getWidth() / 4 + (Gdx.graphics.getHeight() / 4), Gdx.graphics.getWidth() / 4 - (Gdx.graphics.getHeight() / 4), Gdx.graphics.getHeight() / 2, Gdx.graphics.getHeight() / 2);
        BATCH.end();


        if (isRendered) {
            Player.getPlayer();
            isReadyCallBack = true;
            new Game().main();
            boot.setScreen(new GameThread(boot));
            CallBack.executeInitializationCallBacks();
            isReadyCallBack = false;
            texture.dispose();
        }
        isRendered = true;

    }

    @Override
    public void resume() {

    }

    @Override
    public void resize(int p1, int p2) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
