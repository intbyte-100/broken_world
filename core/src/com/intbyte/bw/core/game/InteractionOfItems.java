package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.gameAPI.callbacks.*;
import com.intbyte.bw.gameAPI.environment.Block;
import com.intbyte.bw.gameAPI.environment.BlockExtraData;
import com.intbyte.bw.gameAPI.environment.Item;
import com.intbyte.bw.gameAPI.environment.World;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;
import com.intbyte.bw.gameAPI.environment.Entity;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.environment.Item.getItemFactories;


public class InteractionOfItems {
    private static InteractionOfItems instance;
    private static boolean isDragged;
    static private Material material;
    static private TextureAttribute textureAttribute;
    private final HashMap<Integer, Integer> settableItemsHashMap = new HashMap<>();
    private final StringBuilder builder = new StringBuilder();
    private static final Player player = Player.getPlayer();
    private Integer id;
    private Container container;
    private Item item;

    public static InteractionOfItems getInstance() {
        if (instance == null) instance = new InteractionOfItems();
        return instance;
    }

    public static void init() {
        final Vector3 vector3 = new Vector3();
        final Vector3 destination = new Vector3();
        final Vector3 velocity = new Vector3();
        final Vector3 origin = new Vector3();
        final Rectangle rectangle = new Rectangle(0,0,10,10);
        final InteractionOfItems interaction = getInstance();

        CallBack.addCallBack(new Touch() {
            @Override
            public void main(Vector3 position) {
                float x = position.x*10, z = position.z*10;
                isDragged = !rectangle.contains(x, z-10);
                rectangle.setCenter(x,z-10);
                destination.set(position.x*10,0,position.z*10-10);
                velocity.x = (destination.x-vector3.x)*Gdx.graphics.getDeltaTime()*6;
                velocity.z = (destination.z-vector3.z)*Gdx.graphics.getDeltaTime()*6;
                origin.set(vector3);

            }
        });

        CallBack.addCallBack(new Drag() {
            @Override
            public void main(Vector3 position) {
                float x = position.x*10, z = position.z*10;
                rectangle.setCenter(x,z-10);
                destination.set(position.x*10,0,position.z*10-10);
                velocity.x = (destination.x-vector3.x)*Gdx.graphics.getDeltaTime()*6;
                velocity.z = (destination.z-vector3.z)*Gdx.graphics.getDeltaTime()*6;
                origin.set(vector3);
            }
        });

        CallBack.addCallBack(new Render() {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    material.clear();
                    material.set(textureAttribute);
                }
            };

            @Override
            public void main() {
                if (isDragged) {
                    Integer id = interaction.settableItemsHashMap.get(interaction.container.getId());
                    if (id == null) {
                        isDragged = false;
                        rectangle.setCenter(-1000,-1000);
                        return;
                    }
                    if(!(Math.abs(vector3.x-destination.x)<Math.abs(velocity.x)&&Math.abs(vector3.z-destination.z)<Math.abs(velocity.z)))
                        vector3.add(velocity);
                    else
                        vector3.set(destination);
                    Block.CustomBlock block = Block.getBlocks()[id];
                    material = block.getModelInstance().materials.peek();
                    textureAttribute = (TextureAttribute) material.get(TextureAttribute.Diffuse);
                    material.clear();
                    Color.GREEN.a = 0.5f;
                    material.set(ColorAttribute.createDiffuse(Color.GREEN), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
                    block.render((float) (vector3.x - interaction.player.getX()), vector3.y, (float) (vector3.z - instance.player.getZ()));
                    material.set(textureAttribute);
                    Gdx.app.postRunnable(runnable);
                } else
                    vector3.set((float) player.getX(),0,(float) player.getZ());
            }
        });
        CallBack.addCallBack(new TouchOnBlock() {


            @Override
            public void main(int x, int z) {
                interaction.builder.setLength(0);
                interaction.container = interaction.player.getCarriedItem();
                interaction.id = interaction.settableItemsHashMap.get(interaction.container.getId());
                boolean set = true;
                if (interaction.id == null) {
                    interaction.id = interaction.container.getId();
                    set = false;
                }

                if (World.getBlock(x, z) > 0 && Item.getItemFactories()[interaction.id] != null && interaction.player.coolDown == 0 && Item.getItemFactories()[interaction.id].getType() != Item.BLOCK) {
                    interaction.hit(x, z);
                }


                if (isDragged) {
                    return;
                }
                if (!(World.getBlock(x, z) > 0) && set) {
                    isDragged = false;
                    interaction.set(x, z);
                }
                rectangle.setCenter(-1000,-1000);
            }
        });
    }

    public void addSettableItem(int itemId, int blockId) {
        settableItemsHashMap.put(itemId, blockId);
    }

    private void hit(int x, int z) {
        if (player.coolDown > 0 || player.getEndurance() < 2) return;

        if (container.getCountItems() != 0)
            item = container.getLastElement();
        container.getItems().get(container.getCountItems() - 1).getItemData().decrementStrength();
        if (container.getItems().get(container.getItems().size - 1).getItemData().getStrength() <= 0) {
            item = container.delete();
        }


        Block.CustomBlock customBlock = Block.getBlocks()[World.getBlock(x, z)];
        BlockExtraData data = World.getBlockData(x, z);
        player.coolDown += item.getItemData().getCoolDown();
        if (data == BlockExtraData.NOT_DATA) {
            World.setBlockData(x, z, customBlock.newData());
            data = World.getBlockData(x, z);
        }
        int blockLevel = Block.getBlocks()[World.getBlock(x, z)].getLevel();
        float damage = item.getItemData().getDamage();
        if (blockLevel != 0)
            damage = damage * ((float) item.getItemData().getLevel() / blockLevel);
        if (getItemFactories()[id].getType() != customBlock.TYPE) {
            damage /= 10;
        }
        if (player.getEndurance() - item.getItemData().getTakeEndurance() < 0) {
            damage /= item.getItemData().getTakeEndurance() / player.getEndurance();
        }
        player.increaseEndurance(-item.getItemData().getTakeEndurance());
        System.out.println(player.getEndurance());
        data.setHealth(data.getHealth() - Math.round(damage));
        builder.append("player hit to block with id ").
                append(id).
                append(", used item with id ").
                append(Player.getPlayer().getCarriedItem().getId()).
                append("; block health = ").
                append(data.getHealth()).
                append("; item damage = ").
                append(damage).
                append(";").append("player endurance = ").
                append(player.getEndurance()).
                append("; player coolDown = ").
                append(player.coolDown).
                append(" item strength = ");

        if (container.getItems().size != 0)
            builder.append(container.getItems().get(container.getCountItems() - 1).getItemData().getStrength());
        else
            builder.append("0");
        builder.append("; x = ").
                append(x).
                append("; z = ").
                append(z);
        Gdx.app.log("PLAYER", builder.toString());

        if (data.getHealth() <= 0) {
            builder.setLength(0);
            builder.append("player destroyed block; x = ").
                    append(x).
                    append("; z = ").
                    append(z);
            if (customBlock.getDropID() != 0) {
                Entity drop = Entity.spawn(customBlock.getDropID(), x, z);
                drop.translate(Math.random() * 8 - 4, Math.random() * 8 - 4);
                builder.append("; drop = ").append(customBlock.getDropID());
            }
            World.setBlock(x, z, 0);
            Gdx.app.log("PLAYER", builder.toString());
        }

    }

    private void set(int x, int z) {
        World.setBlock(x, z, id);
        builder.append("player set block with id ").
                append(id).
                append(", used item with id ").
                append(Player.getPlayer().getCarriedItem().getId()).
                append("; x = ").
                append(x).
                append("; z = ").
                append(z);
        Gdx.app.log("PLAYER", "player set block with id " + id + ", used item with id " + Player.getPlayer().getCarriedItem().getId() + "; x = " + x + "; z = " + z);
        container.delete();
    }
}
