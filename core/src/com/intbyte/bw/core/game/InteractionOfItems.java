package com.intbyte.bw.core.game;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.TouchOnBlock;
import com.intbyte.bw.gameAPI.environment.*;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;

import java.util.HashMap;

import static com.intbyte.bw.gameAPI.environment.Item.getItemFactories;


public class InteractionOfItems {
    public static final HashMap<Integer, Integer> settableItemsHashMap = new HashMap<>();
    private static final StringBuilder builder = new StringBuilder();
    private static Player player = Player.getPlayer();
    private static Integer id;
    private static Container container;

    public static void init() {
        CallBack.addCallBack(new TouchOnBlock() {
            Item item;


            private void hit(int x, int z) {
                if (container.getCountItems() != 0)
                    item = container.getLastElement();
                container.getItems().get(container.getCountItems() - 1).getItemData().decrementStrength();
                if (container.getItems().get(container.getItems().size - 1).getItemData().getStrength() <= 0) {
                    item = container.delete();
                }


                Block.CustomBlock customBlock = Block.getBlocks()[World.getBlock(x, z)];
                BlockExtraData data = World.getBlockData(x, z);

                if (data == BlockExtraData.NOT_DATA) {
                    World.setBlockData(x, z, customBlock.newData());
                    data = World.getBlockData(x, z);
                }

                if (getItemFactories()[id].getType() == customBlock.TYPE) {
                    data.setHealth(data.getHealth() - item.getItemData().getDamage());
                } else {
                    data.setHealth(data.getHealth() - item.getItemData().getDamage() / 10);
                }

                if (data.getHealth() <= 0) {
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
                    return;
                }

                builder.append("player hit to block with id ").
                        append(id).
                        append(", used item with id ").
                        append(Player.getPlayer().getCarriedItem().getId()).
                        append(" item strength = ");

                if (container.getItems().size == 0)
                    return;
                else
                    builder.append(container.getItems().get(container.getCountItems() - 1).getItemData().getStrength());

                builder.append("; x = ").
                        append(x).
                        append("; z = ").
                        append(z);
                Gdx.app.log("PLAYER", builder.toString());
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

            @Override
            public void main(int x, int z) {
                builder.setLength(0);
                container = player.getCarriedItem();
                id = settableItemsHashMap.get(container.getId());
                boolean set = true;
                if (id == null) {
                    id = container.getId();
                    set = false;
                }


                if (World.getBlock(x, z) > 0) {
                    if (player.coolDown == 0 && Item.getItemFactories()[id].getType() != Item.BLOCK) {
                        hit(x, z);
                    }
                } else if (set)
                    set(x, z);
            }
        });
    }
}
