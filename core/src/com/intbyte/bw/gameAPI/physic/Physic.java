package com.intbyte.bw.gameAPI.physic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.intbyte.bw.gameAPI.environment.Drop;
import com.intbyte.bw.gameAPI.environment.Entity;
import com.intbyte.bw.gameAPI.utils.ID;

public class Physic {
    static private final World world = new World(new Vector2(0, 0), true);
    private static final BodyFactory[] bodyFactories = new BodyFactory[1200];

    public static void init(){
        defineStaticRectangleBody("default", 0.5f, 0.5f);
        defineDynamicRectangleBody("defaultEntity", 0.5f, 0.5f);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                PhysicData data = (PhysicData) contact.getFixtureB().getBody().getUserData();
                PhysicData physicData = (PhysicData) contact.getFixtureA().getBody().getUserData();
                Drop drop = (Drop) (data.getType() == PhysicData.DROP ? data.getObject() : (physicData.getType()==PhysicData.DROP ? physicData.getObject() : null));
                Entity entity = (Entity) (data.getType() == PhysicData.ENTITY ? data.getObject() : (physicData.getType()==PhysicData.ENTITY ? physicData.getObject() : null));
                if(drop!=null&&entity!=null){
                    if(entity.takeDrop(drop.getDrop()))
                        drop.delete();
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    public static void update() {
        world.step(Gdx.graphics.getDeltaTime(), 4, 4);
    }

    public static void defineRectangleBody(String id, final BodyDef.BodyType type, final float x, final float z) {
        defineBodyFactory(id, new BodyFactory() {
            @Override
            protected Body getBody(World world) {
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = type;

                Body body = world.createBody(bodyDef);

                FixtureDef fixtureDef = new FixtureDef();
                body.setFixedRotation(false);
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(x, z);

                fixtureDef.shape = shape;
                body.createFixture(fixtureDef);
                body.setLinearDamping(0.5f);
                body.setFixedRotation(false);
                return body;
            }
        });
    }

    public static void defineStaticRectangleBody(String id, final float x, final float z){
        defineRectangleBody(id, BodyDef.BodyType.StaticBody,x,z);
    }

    public static void defineDynamicRectangleBody(String id, final float x, final float z){
        defineRectangleBody(id, BodyDef.BodyType.DynamicBody,x,z);
    }

    public static void defineBodyFactory(String id, BodyFactory factory) {
        bodyFactories[ID.registeredId("2dBody:" + id)] = factory;
    }


    public static BodyFactory getBodyFactory(int id) {
        return bodyFactories[id];
    }

    public static Body allocateBody(int id) {
        return bodyFactories[id].allocate2dBody(world);
    }

    public static World getWorld() {
        return world;
    }
}
