package com.intbyte.bw.engine;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

import com.intbyte.bw.engine.entity.EntityManager;
import com.intbyte.bw.engine.input.GameInputProcessor;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.callbacks.Render;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.world.World;
import com.intbyte.bw.engine.world.WorldConfig;
import com.intbyte.bw.engine.render.GlobalEnvironment;
import com.intbyte.bw.engine.ui.GUI;
import com.intbyte.bw.engine.ui.container.TakenItemsRender;
import com.intbyte.bw.engine.physic.Physic;

import static com.intbyte.bw.engine.graphic.Graphic.*;


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
        Gdx.input.setInputProcessor(new InputMultiplexer(TakenItemsRender.listener, STAGE, new GameInputProcessor(render.getCamera())));


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
        Physic.update();
        new Thread() {
            @Override
            public void run() {


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
        render.getCamera().viewportHeight = p2;
        render.getCamera().viewportWidth = p1;
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




