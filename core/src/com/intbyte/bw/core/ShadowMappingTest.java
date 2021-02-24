package com.intbyte.bw.core;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;


public class ShadowMappingTest extends Game {
    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
    Model model;
    ModelInstance instance;
    Environment environment;
    DirectionalShadowLight shadowLight;
    ModelBatch shadowBatch;
    ModelInstance modelInstance;

    @Override
    public void create () {
        Model model = new ObjLoader().loadModel(Gdx.files.internal("objects/block/tree.obj"));
        modelInstance = new ModelInstance(model);
        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, .4f, .4f, .4f, 1f));
        environment.add((shadowLight = new DirectionalShadowLight(1024, 1024, 10f, 10f, 1f, 100f)).set(0.8f, 0.8f, 0.8f, -1f, -01f,
                -.2f));
        environment.shadowMap = shadowLight;

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0f, 7f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 50f;
        cam.update();

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder mpb = modelBuilder.part("parts", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal | Usage.ColorUnpacked,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)));
        mpb.setColor(1f, 1f, 1f, 1f);
        mpb.box(0, 1.5f, 0, 10, 1, 10);
        mpb.setColor(1f, 0f, 1f, 1f);
        mpb.sphere(2f, 2f, 2f, 100, 100);

        //mpb.ellipse(2,1,100,0,-5,0,0,0,0);
        model = modelBuilder.end();
        instance = new ModelInstance(model);

        shadowBatch = new ModelBatch(new DepthShaderProvider());

        Gdx.input.setInputProcessor(camController = new CameraInputController(cam));
        instance.transform.translate(-1,-1,-1);
    }

    @Override
    public void render () {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        shadowLight.begin(Vector3.Zero, cam.direction);
        shadowBatch.begin(shadowLight.getCamera());
        shadowBatch.render(instance,environment);
        modelInstance.transform.rotate(Vector3.Y, (float) Math.toDegrees(5)*Gdx.graphics.getDeltaTime());
        shadowBatch.render(modelInstance,environment);
        shadowBatch.end();
        shadowLight.end();

        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.render(modelInstance,environment);
        modelBatch.end();
    }

    @Override
    public void dispose () {
        modelBatch.dispose();
        model.dispose();
    }

    public boolean needsGL20 () {
        return true;
    }

    public void resume () {
    }

    public void resize (int width, int height) {
    }

    public void pause () {
    }
}
