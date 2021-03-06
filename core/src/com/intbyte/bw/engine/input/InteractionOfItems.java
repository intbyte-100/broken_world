package com.intbyte.bw.engine.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.engine.GameThread;
import com.intbyte.bw.engine.entity.Player;
import com.intbyte.bw.engine.block.BlockExtraData;
import com.intbyte.bw.engine.block.CustomBlock;
import com.intbyte.bw.engine.callbacks.Render;
import com.intbyte.bw.engine.callbacks.Touch;
import com.intbyte.bw.engine.callbacks.TouchOnBlock;
import com.intbyte.bw.engine.callbacks.Drag;
import com.intbyte.bw.engine.callbacks.CallBack;
import com.intbyte.bw.engine.entity.Entity;
import com.intbyte.bw.engine.item.Container;
import com.intbyte.bw.engine.block.Block;
import com.intbyte.bw.engine.item.Item;
import com.intbyte.bw.engine.world.Tile;
import com.intbyte.bw.engine.world.World;

import java.util.HashMap;

import static com.intbyte.bw.engine.item.Item.getItemFactories;


public class InteractionOfItems {
    private static final Player player = Player.getPlayer();
    private static InteractionOfItems instance;
    private static boolean isDragged;

    private final HashMap<Integer, Integer> settableItemsHashMap = new HashMap<>();
    private final StringBuilder builder = new StringBuilder();
    private Integer id;
    private Container container;
    private Item item;
    private static final Rectangle rectangle = new Rectangle(0, 0, 10, 10);
    private static boolean interaction;
    private static ModelInstance modelInstance;

    public static InteractionOfItems getInstance() {
        if (instance == null) instance = new InteractionOfItems();
        return instance;
    }

    public static boolean isInteraction() {
        return interaction;
    }

    public static void setInteraction(boolean interaction) {
        InteractionOfItems.interaction = interaction;
    }

    public static void init() {
        final Vector3 vector3 = new Vector3();
        final Vector3 destination = new Vector3();
        final Vector3 velocity = new Vector3();
        final Vector3 origin = new Vector3();
        final Vector3 oldPosition = new Vector3();
        final InteractionOfItems interaction = getInstance();

        CallBack.addCallBack(new Touch() {
            @Override
            public void main(Vector3 position) {
                if (!InteractionOfItems.interaction) return;
                float x = position.x * 10 - GameThread.xDraw, z = position.z * 10 - GameThread.zDraw;
                isDragged = !rectangle.contains(x, z - 10);
                if (!isDragged) return;
                rectangle.setCenter(x, z - 10);
                destination.set(x, 0, z - 10);
                velocity.x = (destination.x - vector3.x);
                velocity.z = (destination.z - vector3.z);
                origin.set(vector3);

            }
        });

        CallBack.addCallBack(new Drag() {
            @Override
            public void main(Vector3 position) {
                if (!InteractionOfItems.interaction) return;
                float x = position.x * 10 - GameThread.xDraw, z = position.z * 10 - GameThread.zDraw;
                rectangle.setCenter(x, z - 10);
                destination.set(x, 0, z - 10);
                velocity.x = (destination.x - vector3.x) * 0.2f;
                velocity.z = (destination.z - vector3.z) * 0.2f;
                oldPosition.set(vector3);
                origin.set(vector3);
            }
        });

        CallBack.addCallBack(new Render() {
            @Override
            public void main() {
                if (isDragged && InteractionOfItems.interaction) {
                    Integer id = interaction.settableItemsHashMap.get(interaction.container.getId());
                    if (id == null) {
                        isDragged = false;
                        rectangle.setCenter(-1000, -1000);
                        return;
                    }

                    if (!(Math.abs(vector3.x - destination.x) <= Math.abs(velocity.x) &&
                            Math.abs(vector3.z - destination.z) <= Math.abs(velocity.z))) {
                        vector3.add(velocity);
                        if (!(Math.abs(vector3.x - destination.x) - Math.abs(oldPosition.x - destination.x) < Math.abs(velocity.x) &&
                                Math.abs(vector3.z - destination.z) - Math.abs(oldPosition.z - destination.z) < Math.abs(velocity.z)))
                            vector3.set(destination);
                    } else
                        vector3.set(destination);
                    if (modelInstance == null) return;
                    Color.GREEN.a = 0.5f;
                    modelInstance.materials.peek().set(ColorAttribute.createDiffuse(Color.GREEN), new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
                    Block.getBlocks()[id].render(modelInstance, (float) (vector3.x - player.getPixelX()) + GameThread.xDraw, 5, (float) (vector3.z - player.getPixelZ()) + GameThread.zDraw);
                    return;
                }
                isDragged = false;
                rectangle.setPosition(-1000, -1000);
            }
        });
        CallBack.addCallBack(new TouchOnBlock() {

            private int oldId;

            @Override
            public void main(float x, float z) {
                if (!InteractionOfItems.interaction) return;
                interaction.builder.setLength(0);
                interaction.container = player.getCarriedItem();
                interaction.id = interaction.settableItemsHashMap.get(interaction.container.getId());
                boolean set = true;
                if (interaction.id == null) {
                    interaction.id = interaction.container.getId();
                    set = false;
                } else {
                    if (oldId != instance.id) {
                        modelInstance = new ModelInstance(Block.getBlock(interaction.id).getModelInstance().model);
                    }
                    oldId = interaction.id;
                }
                if (isDragged && !World.isCollision(x, z - 1)) {
                    return;
                }
                if (World.isCollision(x, z - 1) && Item.getItemFactories()[interaction.container.getId()] != null && player.getCoolDown() == 0 && Item.getItemFactories()[interaction.container.getId()].getType() != Item.BLOCK) {
                    interaction.hit(x, z);
                    return;
                }
                if (!(World.isCollision(x, z - 1)) && set) {
                    isDragged = false;
                    interaction.set(x, z);
                }
                rectangle.setCenter(Integer.MIN_VALUE, Integer.MIN_VALUE);
            }
        });
    }

    public void addSettableItem(int itemId, int blockId) {
        settableItemsHashMap.put(itemId, blockId);
    }

    synchronized private void hit(float x, float z) {
        if (player.getCoolDown() > 0 || player.getEndurance() < 2) return;

        if (container.getCountItems() != 0)
            item = container.getLastElement();
        container.getItems().get(container.getCountItems() - 1).getItemData().decrementStrength();
        if (container.getItems().get(container.getItems().size - 1).getItemData().getStrength() <= 0) {
            item = container.delete();
        }

        Tile tile = World.getIntersectedTile(x, z - 1);

        assert tile != null;
        CustomBlock customBlock = tile.getBlock();

        BlockExtraData data = tile.getData();
        player.setCoolDown(player.getCoolDown() + item.getItemData().getCoolDown());
        int blockLevel = tile.getBlock().getLevel();
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
                append(player.getCoolDown()).
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
                Entity.spawn(customBlock.getDropID(), x, z - 0.5f);
                builder.append("; drop = ").append(customBlock.getDropID());
            }
            tile.setBlockID(0);
            Gdx.app.log("PLAYER", builder.toString());
        }

    }

    private void set(float x, float z) {
        isDragged = false;
        if (World.isCollision(x, z - 1)) return;
        builder.append("player set block with id ").
                append(id).
                append(", used item with id ").
                append(Player.getPlayer().getCarriedItem().getId()).
                append("; x = ").
                append(x).
                append("; z = ").
                append(z);
        Gdx.app.log("PLAYER", "player set block with id " + id + ", used item with id " + Player.getPlayer().getCarriedItem().getId() + "; x = " + x + "; z = " + z);
        World.setBlock((rectangle.x + 5) / 10, (rectangle.y + 5) / 10, id);
        container.delete();
    }
}
