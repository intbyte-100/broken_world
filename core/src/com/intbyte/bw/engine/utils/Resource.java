package com.intbyte.bw.engine.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.UBJsonReader;
import com.intbyte.bw.engine.physic.PhysicBlockObject;

import java.util.HashMap;


public class Resource {
    private static final HashMap<String, FileHandle> files = new HashMap<>();
    private static final HashMap<String,Sprite> sprites = new HashMap<>();
    private static final HashMap<String, Model> models = new HashMap<>();
    private static final HashMap<String, PhysicBlockObject> physicBlockObjects = new HashMap<>();
    private static final ObjLoader loader = new ObjLoader();
    private static final G3dModelLoader g3dLoader = new G3dModelLoader(new UBJsonReader());
    private static final PerspectiveCamera camera = new PerspectiveCamera(67,128,128);
    private static final Environment environment = new Environment();
    private static final ModelBatch modelBatch = new ModelBatch();

    static {
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.6f,0.6f,0.6f, -1f, -1f, 1f));
    }

    public static FileHandle getFile(String path) {
        FileHandle fileHandle = files.get(path);
        if (fileHandle == null) {
            fileHandle = Gdx.files.internal(path);
            files.put(path, fileHandle);
            Gdx.app.log("RESOURCE LOADER","file "+fileHandle+" is loaded");
        }
        return fileHandle;
    }

    public static Sprite getSprite(String path) {
        Sprite sprite = sprites.get(path);
        if (sprite == null) {
            sprite = new Sprite(new Texture(getFile("textures/" + path)));
            sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            sprites.put(path, sprite);
            Gdx.app.log("RESOURCE LOADER","sprite "+sprite.getTexture()+" is loaded");
        }
        return sprite;
    }

    public static Model getModel(String path) {
        Model model = models.get(path);
        if (model == null) {

            model = (path.split("\\.")[1].equals("obj") ? loader : g3dLoader).loadModel(getFile("objects/" + path));
            models.put(path, model);
            Gdx.app.log("RESOURCE LOADER","model "+path+" is loaded");
        }
        return model;
    }

    public static ModelInstance createModelInstance(String modelPath) {
        return new ModelInstance(getModel(modelPath));
    }


    public static PhysicBlockObject getBlockObject(String path){
        return physicBlockObjects.get(path);
    }

    public static void putBlockObject(String path, PhysicBlockObject object){
        physicBlockObjects.put(path,object);
    }

    public static void dispose() {
        for (Model model : models.values()) {
            Gdx.app.log("DISPOSE",model+" is disposed");
            model.dispose();
        }
        for (Sprite sprite :
                sprites.values()) {
            Gdx.app.log("DISPOSE","sprite "+sprite.getTexture()+" is disposed");
            sprite.getTexture().dispose();
        }
    }

    public static Sprite getIconFromModel(ModelInstance modelInstance){
        return getIconFromModel(modelInstance,0,0,0,1);
    }

    public static Sprite getIconFromModel(ModelInstance modelInstance, float x, float y, float z, float scale){
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight(), true);
        frameBuffer.begin(); //Capture rendering to frame buffer.
        camera.position.set(10-x,10-y,10-z);
        modelInstance.transform.setToTranslation(x,y,z);
        modelInstance.transform.setToScaling(scale,scale,scale);
        camera.lookAt(0,0,0);
        Gdx.gl.glClearColor(1f,1f,1f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        camera.update();

        modelBatch.begin(camera);

        modelBatch.render(modelInstance,environment);
        modelBatch.end();

        frameBuffer.end();
        Sprite sprite = new Sprite(frameBuffer.getColorBufferTexture());
        sprite.flip(false,true);
        sprite.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return sprite;
    }
    public static void addSprite(Sprite sprite, String path){
        sprites.put(path,sprite);
    }
}
