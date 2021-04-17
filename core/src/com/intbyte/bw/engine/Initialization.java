package com.intbyte.bw.engine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.intbyte.bw.GameBoot;
import com.intbyte.bw.engine.GameThread;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.input.InteractionOfItems;
import com.intbyte.bw.game.Game;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.physic.Physic;

import static com.intbyte.bw.engine.graphic.Graphic.BATCH;

public class Initialization implements Screen {

    private static boolean isReadyCallBack;
    private GameBoot boot;
    private Texture texture;
    private boolean isRendered;

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

        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {

                Gdx.app.log("INITIALIZATION", "starting initialization");
                Physic.init();
                Player.getPlayer();
                boot.setScreen(new GameThread());
                isReadyCallBack = true;
                InteractionOfItems.init();
                new Game().main();
                CallBack.executeInitializationCallBacks();
                isReadyCallBack = false;
                texture.dispose();
                Gdx.app.log("INITIALIZATION", "initialization finished");
            }
        });


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
