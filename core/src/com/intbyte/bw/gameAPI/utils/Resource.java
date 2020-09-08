package com.intbyte.bw.gameAPI.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;

import java.util.HashMap;

public class Resource {
    private static final HashMap<String, FileHandle> files = new HashMap<>();
    private static final HashMap<String, Texture> textures = new HashMap<>();
    private static final HashMap<String, Model> models = new HashMap<>();
    private static final ObjLoader loader = new ObjLoader();


    public static FileHandle getFile(String path) {
        FileHandle fileHandle = files.get(path);
        if (fileHandle == null) {
            fileHandle = Gdx.files.internal(path);
            files.put(path, fileHandle);
        }
        return fileHandle;
    }

    public static Texture getTexture(String path) {
        Texture texture = textures.get(path);
        if (texture == null) {
            texture = new Texture(Gdx.files.internal("textures/" + path));
            textures.put(path, texture);
        }
        return texture;
    }

    public static Model getObjModel(String path) {
        Model model = models.get(path);
        if (model == null) {
            model = loader.loadModel(Gdx.files.internal("objects/" + path));
            models.put(path, model);
        }
        return model;
    }

    public static ModelInstance createModalInstance(String modelPath) {
        return new ModelInstance(getObjModel(modelPath));
    }

    public static void dispose() {
        for (Model model : models.values()) {
            model.dispose();
        }
        for (Texture texture :
                textures.values()) {
            texture.dispose();
        }

        for (FileHandle fileHandle :
                files.values()) {
            fileHandle.delete();
        }
    }
}
