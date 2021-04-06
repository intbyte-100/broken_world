package com.intbyte.bw.core.game;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.environment.WorldConfig;
import com.intbyte.bw.gameAPI.graphic.GlobalEnvironment;
import com.intbyte.bw.gameAPI.ui.GUI;
import com.intbyte.bw.gameAPI.ui.container.TakenItemsRender;
import com.intbyte.bw.gameAPI.physic.Physic;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;


public class GameThread implements Screen {


    public static float xDraw, zDraw;
    public static int visible;
    private static boolean isReadyCallBack;
    private static EntityManager entityManager;
    private final FrustumCullingRender render;
    private final Engine engine;


    public GameThread() {
        World.createVoidWorld(12, 12, new WorldConfig(true,true));


        render = new ChuncksRender();
        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, STAGE, new GameInputProcessor(render.camera3d)));


        entityManager = new EntityManager();
        engine = new Engine();
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static boolean isReadyCallBack() {
        return isReadyCallBack;
    }
    float power = 0;
    @Override
    public void render(final float delta) {
        GlobalEnvironment.setIntensity(power);
        GlobalEnvironment.update();
        new Thread() {
            @Override
            public void run() {

                Physic.update();
                World.update();

                power+=delta/40;
                if(power>1)power=0.0f;
            }
        }.start();


                for (int i = 0; i < entityManager.getActive().size; i++)
                    entityManager.getActive().get(i).renderTick();

        isReadyCallBack = true;




        render.render();
        for (Entity i : entityManager.getActive()) {
            i.render();
        }
        engine.update(delta);

        BATCH.begin();
        CallBack.executeRenderCallBacks();
        BATCH.end();
        getModelBatch().end();
        STAGE.act();
        STAGE.draw();
        BATCH.begin();
        Render.callBacks.get(0).main();
        BATCH.end();
        isReadyCallBack = false;
        visible = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            GUI.setLayer(GUI.isOpen("main")?"inventory":"main",null);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.log("APPLICATION","application finished");
            System.exit(0);
        }
    }


    @Override
    public void resume() {

    }

    @Override
    public void resize(int p1, int p2) {
        render.camera3d.viewportHeight = p2;
        render.camera3d.viewportWidth = p1;
        render.resize();
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




