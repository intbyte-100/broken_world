package com.intbyte.bw.gameAPI.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.intbyte.bw.core.game.GameThread;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;
import com.intbyte.bw.gameAPI.utils.ShaderContainer;

public final class Graphic {
    private static final ModelBatch modelBatch;
    private static final ModelBatch shadowModelBatch = new ModelBatch(new DepthShaderProvider());
    private static boolean shadowMod;
    static public final Stage STAGE;
    static public final Environment ENVIRONMENT;
    static public final ModelLoader MODEL_LOADER;
    private static final DirectionalShadowLight shadowLight;
    public static final Batch BATCH;
    static int screenWidth, screenHeight;
    public static final float BLOCK_SIZE;
    private static Environment environment = new Environment();

    static {
        ShaderContainer shaderContainer = new ShaderContainer("default");
        modelBatch = new ModelBatch(new DefaultShaderProvider(shaderContainer.getVert(), shaderContainer.getFrag()));
        STAGE = new Stage();
        STAGE.cancelTouchFocus();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        BLOCK_SIZE = 10;
        MODEL_LOADER = new ObjLoader();
        BATCH = STAGE.getBatch();

        ENVIRONMENT = new Environment();
        float color = 0.99f;
        float ambient = 0;

        ambient+=(color-0.5f)/(0.5f/4)/10;
        if (ambient == 0) ambient+=0.1f;
        System.out.println(ambient);
        ENVIRONMENT.set(new ColorAttribute(ColorAttribute.AmbientLight, ambient, ambient, ambient, 1f));

        ENVIRONMENT.add(new DirectionalLight().set(0.f,0,0.2f, 0, -20f, 0));
        ENVIRONMENT.add(new DirectionalLight().set(0.1f,0.1f,0.1f, 0, -20f, 0));
        ENVIRONMENT.add((shadowLight = new DirectionalShadowLight(2000, 2000, 150f, 100f, 1f, 200f)).set(color,color,color, -1f, -1f, 1f));
        ENVIRONMENT.shadowMap = shadowLight;
        final PointLight pointLight = new PointLight().set(Color.WHITE, 1f, -1f, 1f, 300f);
        //ENVIRONMENT.add(pointLight);

        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.2f,0.2f,0.2f,1));
        environment.shadowMap = ENVIRONMENT.shadowMap;
        float v = 1/(-(color-1)*10);
        final PointLight light = new PointLight().set(Color.CORAL, 1f, 20f, 1f, 10*100f);
        environment.add(new DirectionalShadowLight(2000, 2000, 150f, 100f, 1f, 200f).set(v,v,v, -1f, -1f, 1f));
        //environment.add(light);
        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                light.setPosition(1+ GameThread.xDraw,20,1+GameThread.zDraw);
                pointLight.setPosition(1f+ GameThread.xDraw, 10f, 1f+GameThread.zDraw);
            }
        });

    }

    public static int greatestCommonFactor(int width, int height) {
        return (height == 0) ? width : greatestCommonFactor(height, width % height);
    }

    public static int getHeightRatio() {
        return getScreenHeight() / greatestCommonFactor(getScreenWidth(), getScreenHeight());
    }

    public static int getWidthRatio() {
        return getScreenWidth() / greatestCommonFactor(getScreenWidth(), getScreenHeight());
    }

    public static int getScreenWidth() {
        return screenWidth;
    }


    public static int getScreenHeight() {
        return screenHeight;
    }

    public static void resize() {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
    }

    public static ModelBatch getModelBatch() {
        return shadowMod ? shadowModelBatch : modelBatch;
    }

    public static void setShadowMod(boolean shadowMod) {
        Graphic.shadowMod = shadowMod;
    }

    public static DirectionalShadowLight getShadowLight() {
        return GlobalEnvironment.INSTANCE.getShadowLight();
        //return shadowLight;
    }

    public static Environment getEnvironment(boolean isLandBlock){
        return GlobalEnvironment.getEnvironment(isLandBlock);
        //return isLandBlock ? environment : ENVIRONMENT;
    }
}

