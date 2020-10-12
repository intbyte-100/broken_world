package com.intbyte.bw.gameAPI.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.Render;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.graphic.Graphic.*;

public class Resource {
    private static final HashMap<String, FileHandle> files = new HashMap<>();
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static final HashMap<String, Model> models = new HashMap<>();
    private static final ObjLoader loader = new ObjLoader();
    private static final PerspectiveCamera camera = new PerspectiveCamera(67,128,128);
    private static final FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);

    public static FileHandle getFile(String path) {
        FileHandle fileHandle = files.get(path);
        if (fileHandle == null) {
            fileHandle = Gdx.files.internal(path);
            files.put(path, fileHandle);
            Gdx.app.log("RESOURCE LOADER","file "+fileHandle+" is loaded");
        }
        return fileHandle;
    }

    public static Texture getTexture(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            texture = new Texture(getFile("textures/" + path));
            textures.put(path, texture);
            Gdx.app.log("RESOURCE LOADER","texture "+texture+" is loaded");
        }
        return texture;
    }

    public static Model getObjModel(String path) {
        Model model = models.get(path);
        if (model == null) {
            model = loader.loadModel(getFile("objects/" + path));
            models.put(path, model);
            Gdx.app.log("RESOURCE LOADER","model "+path+" is loaded");
        }
        return model;
    }

    public static ModelInstance createModalInstance(String modelPath) {
        return new ModelInstance(getObjModel(modelPath));
    }

    public static void dispose() {
        for (Model model : models.values()) {
            Gdx.app.log("DISPOSE",model+" is disposed");
            model.dispose();
        }
        for (Texture texture :
                textures.values()) {
            Gdx.app.log("DISPOSE","texture "+texture+" is disposed");
            texture.dispose();
        }
    }

    public static Texture getIconFromModel(ModelInstance modelInstance){
        frameBuffer.begin(); //Capture rendering to frame buffer.
        camera.position.set(10,10,10);
        modelInstance.transform.setToTranslation(0,0,0);
        camera.lookAt(0,0,0);
        Gdx.gl.glClearColor(1f,1f,1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        camera.update();
        MODEL_BATCH.begin(camera);
        MODEL_BATCH.render(modelInstance,ENVIRONMENT);
        MODEL_BATCH.end();
        frameBuffer.end();

        return new Sprite(frameBuffer.getColorBufferTexture()).getTexture();
    }

    public static void addTexture(Texture texture, String path){
        textures.put(path,texture);
    }
}
