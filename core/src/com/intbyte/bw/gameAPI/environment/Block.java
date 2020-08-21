package com.intbyte.bw.gameAPI.environment;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.ID;
import java.util.HashMap;

import static com.intbyte.bw.gameAPI.graphic.Graphic.ENVIRONMENT;
import static com.intbyte.bw.gameAPI.graphic.Graphic.MODEL_LOADER;
import static com.intbyte.bw.gameAPI.graphic.Graphic.MODEL_BATCH;

public class Block{

	static CustomBlock[] blocks = new CustomBlock[1200];
	static CustomBlock[] landBlocks = new CustomBlock[1200];
	public static class CustomBlock{
		HashMap<Integer,ExtraData> blockData;
		private ModelInstance modelInstance;
		public void render(float x, float y, float z){
			modelInstance.transform.setToTranslation(x, y, z);
			MODEL_BATCH.render(modelInstance, ENVIRONMENT);
		}

		public void collision(){
			
		}


	}

	public static CustomBlock[] getBlocks(){
		return blocks;
	}

	public static CustomBlock[] getLandBlocks(){
		return landBlocks;
	}


	public static void defineLandBlock(String id, String texture){
		int integerId = ID.registredId("land_block:" + id);
		CustomBlock block = new CustomBlock();

		Model model = MODEL_LOADER.loadModel(Gdx.files.internal("objects/block/landblock.obj"));
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);

		block.modelInstance = instance;

		landBlocks[integerId] = block;
	}

	public static void defineBlock(String id, String texture){
		int integerId = ID.registredId("block:" + id);
		CustomBlock block = new CustomBlock();

		Model model = MODEL_LOADER.loadModel(Gdx.files.internal("objects/block/block.obj"));
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);

		block.modelInstance = instance;

		blocks[integerId] = block;
	}

	public static void defineBlock(String id, int integerId, String texture){
		ID.registredId("block:" + id, integerId);
		CustomBlock block = new CustomBlock();

		Model model = MODEL_LOADER.loadModel(Gdx.files.internal("objects/block/block.obj"));
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);

		block.modelInstance = instance;

		blocks[integerId] = block;
	}

	public static void defineBlock(String id, Model model, String texture){
		int integerId = ID.registredId("block:" + id);
		CustomBlock block = new CustomBlock();
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);

		block.modelInstance = instance;
		blocks[integerId] = block;
	}


}
