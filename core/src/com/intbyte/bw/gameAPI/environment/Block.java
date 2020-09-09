package com.intbyte.bw.gameAPI.environment;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.intbyte.bw.gameAPI.utils.ExtraData;
import com.intbyte.bw.gameAPI.utils.ID;
import com.intbyte.bw.gameAPI.utils.Resource;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.graphic.Graphic.ENVIRONMENT;
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

	private static ModelInstance getModelInstance(String pathToModel,String pathToTexture){
		Model model = Resource.getObjModel(pathToModel);
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, Resource.getTexture(pathToTexture));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);
		return instance;
	}
	public static void defineLandBlock(String id, String texture){
		int integerId = ID.registeredId("land_block:" + id);
		CustomBlock block = new CustomBlock();


		block.modelInstance = getModelInstance("block/landblock.obj",texture);

		landBlocks[integerId] = block;
		Gdx.app.log("BLOCK","defined block "+id);
	}

	public static void defineBlock(String id, String texture){
		int integerId = ID.registeredId("block:" + id);
		CustomBlock block = new CustomBlock();

		block.modelInstance = getModelInstance("block/block.obj",texture);

		blocks[integerId] = block;
		Gdx.app.log("BLOCK","defined block "+id);
	}

	public static void defineBlock(String id, int integerId, String texture){
		ID.registeredId("block:" + id, integerId);
		CustomBlock block = new CustomBlock();

		block.modelInstance = getModelInstance("block/block.obj",texture);

		blocks[integerId] = block;
		Gdx.app.log("BLOCK","defined block "+id);
	}

	public static void defineBlock(String id, Model model, String texture){
		int integerId = ID.registeredId("block:" + id);
		CustomBlock block = new CustomBlock();
		TextureAttribute textureAttribute1 = new TextureAttribute(TextureAttribute.Diffuse, new Texture(Gdx.files.internal("textures/" + texture)));
		Material material = model.materials.get(0);
		material.set(textureAttribute1);
		ModelInstance instance = new ModelInstance(model);
		instance.transform.setToTranslation(0, 0, 0);

		block.modelInstance = instance;
		blocks[integerId] = block;
		Gdx.app.log("BLOCK","defined block "+id);
	}


}
