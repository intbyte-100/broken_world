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
}
