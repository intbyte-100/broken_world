package com.intbyte.bw.gameAPI.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;

public final class Graphic{
	static public final ModelBatch MODEL_BATCH;
	static public final Stage STAGE;
	static public final Environment ENVIRONMENT;
	static public final ModelLoader MODEL_LOADER;
	public static final Batch BATCH;
	public static final int

	SCREEN_WIDTH;
	public static final int SCREEN_HEIGHT;
	public static final float BLOCK_SIZE;
	static{
		STAGE = new Stage();
		STAGE.cancelTouchFocus();
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		BLOCK_SIZE = 10;
		MODEL_LOADER = new ObjLoader();
		BATCH = STAGE.getBatch();
		MODEL_BATCH = new ModelBatch();
        ENVIRONMENT = new Environment();
        ENVIRONMENT.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        ENVIRONMENT.add(new DirectionalLight().set(Color.WHITE, -0.5f, -0.8f, -0.2f));
	}

	public static int greatestCommonFactor(int width, int height) {
		return (height == 0) ? width : greatestCommonFactor(height, width % height);
	}

	public static int getHeightRatio(){
		return SCREEN_HEIGHT / greatestCommonFactor(SCREEN_WIDTH,SCREEN_HEIGHT);
	}

	public static int getWidthRatio(){
		return SCREEN_WIDTH / greatestCommonFactor(SCREEN_WIDTH,SCREEN_HEIGHT);
	}
}

