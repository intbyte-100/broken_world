package com.intbyte.bw.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class PointLightTest extends ApplicationAdapter {
    ModelBatch modelBatch;
    Environment environment;
    PerspectiveCamera camera;
    CameraInputController camController;
    PointLight pointLight;
    Model model;
    ModelInstance instance;

    @Override
    public void create () {
        modelBatch = new ModelBatch();
        camera = new PerspectiveCamera();
        camera.position.set(5f, 5f, 5f);
        camera.lookAt(0f, 0f, 0f);
        camController = new CameraInputController(camera);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.0f));
        environment.add(pointLight = new PointLight().set(0.8f, 0.8f, 0.8f, 2f, 0f, 0f, 10f));

        ModelBuilder mb = new ModelBuilder();
        model = mb.createSphere(1f, 1f, 1f, 20, 10, new Material(ColorAttribute.createDiffuse(Color.GREEN)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camController.update();
        pointLight.position.rotate(Vector3.Z, Gdx.graphics.getDeltaTime() * 90f);
        modelBatch.begin(camera);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose () {
        model.dispose();
        modelBatch.dispose();
    }
}
