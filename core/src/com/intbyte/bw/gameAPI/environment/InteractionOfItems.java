package com.intbyte.bw.gameAPI.environment;

import com.badlogic.gdx.Gdx;
import com.intbyte.bw.core.game.Player;
import com.intbyte.bw.gameAPI.callbacks.CallBack;
import com.intbyte.bw.gameAPI.callbacks.TouchOnBlock;
import com.intbyte.bw.gameAPI.graphic.ui.container.Container;

import static com.intbyte.bw.gameAPI.environment.Item.getItems;
import static com.intbyte.bw.gameAPI.environment.Item.settableItemsHashMap;

class InteractionOfItems {
    private static final StringBuilder builder = new StringBuilder();
    static void init(){
        CallBack.addCallBack(new TouchOnBlock() {
            @Override
            public void main(int x, int z) {
                builder.setLength(0);

                Container container = Player.getPlayer().getCarriedItem();
                Integer id = settableItemsHashMap.get(container.getId());
                boolean set = true;
                if (id == null){
                    id = container.getId();
                    set = false;
                }
                if (id == 0) return;

                if (World.getBlock(x, z) > 0) {

                    container.getItems().get(container.getCountItems() - 1).decrementStrength();
                    if (container.getItems().get(container.getItems().size - 1).getStrength() <= 0) container.delete();

                    Block.CustomBlock customBlock = Block.getBlocks()[World.getBlock(x, z)];
                    BlockExtraData data = World.getBlockData(x, z);

                    if (data == BlockExtraData.NOT_DATA) {
                        World.setBlockData(x, z, customBlock.newData());
                        data = World.getBlockData(x, z);
                    }

                    if (getItems()[id].getType() == customBlock.TYPE) {
                        data.setHealth(data.getHealth() - getItems()[id].getDamage());
                    } else {
                        data.setHealth(data.getHealth() - getItems()[id].getDamage() / 10);
                    }

                    if (data.getHealth() <= 0) {
                        builder.append("player destroyed block; x = ").
                                append(x).
                                append("; z = ").
                                append(z);
                        if(customBlock.getDropID() != 0){
                            Entity drop = Entity.spawn(customBlock.getDropID(),x,z);
                            drop.translate(Math.random()*8-4,Math.random()*8-4);
                            builder.append("; drop = "+customBlock.getDropID());
                        }
                        World.setBlock(x, z, 0);
                        Gdx.app.log("PLAYER", builder.toString());
                    }

                    builder.append("player hit to block with id ").
                            append(id).
                            append(", used item with id ").
                            append(Player.getPlayer().getCarriedItem().getId()).
                            append("item strength = ");

                    if(container.getItems().size == 0)
                        return;
                    else
                        builder.append(container.getItems().get(container.getCountItems() - 1).getStrength());

                    builder.append("; x = ").
                            append(x).
                            append("; z = ").
                            append(z);
                    Gdx.app.log("PLAYER", builder.toString());


                    return;
                }

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
        });
    }
}
