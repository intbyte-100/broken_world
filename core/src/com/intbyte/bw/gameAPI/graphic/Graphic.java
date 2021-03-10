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
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.scenes.scene2d.Stage;

public final class Graphic {
    private static final ModelBatch modelBatch = new ModelBatch();
    private static final ModelBatch shadowModelBatch = new ModelBatch(new DepthShaderProvider());
    private static boolean shadowMod;
    static public final Stage STAGE;
    static public final Environment ENVIRONMENT;
    static public final ModelLoader MODEL_LOADER;
    private static final DirectionalShadowLight shadowLight;
    public static final Batch BATCH;
    static int screenWidth, screenHeight;
    public static final float BLOCK_SIZE;

    static {
        STAGE = new Stage();
        STAGE.cancelTouchFocus();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        BLOCK_SIZE = 10;
        MODEL_LOADER = new ObjLoader();
        BATCH = STAGE.getBatch();

        ENVIRONMENT = new Environment();
        ENVIRONMENT.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        ENVIRONMENT.add((shadowLight = new DirectionalShadowLight(1536, 1024, 150f, 100f, 1f, 200f)).set(0.6f,0.6f,0.6f, -1f, -1f, 1f));
        ENVIRONMENT.shadowMap = shadowLight;
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
        return shadowLight;
    }
}

