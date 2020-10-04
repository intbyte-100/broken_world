package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.Resource;

public class Tools {
    public static void newPickaxe(String strId, final String texture, final int level, final int damage, final int strength){
        Item.addItemFactory(strId, new ItemFactory(strId,Item.PICKAXE) {
            @Override
            public Item createItem() {
                return new Item() {

                    {
                        icon = Resource.getTexture(texture);
                        itemData = new ItemData(strength,damage,level);
                    }
                    @Override
                    public byte[] getBytes() {
                        return new byte[0];
                    }

                    @Override
                    public void readBytes(byte[] bytes) {

                    }
                };
            }
        });
    }

    public static void newBlock(final String strId, final String texture, final String blockID){
        Item.addItemFactory(strId, new ItemFactory(strId,Item.BLOCK) {
            @Override
            public Item createItem() {
                return new Item() {

                    {
                        icon = Resource.getTexture(texture);
                    }
                    @Override
                    public byte[] getBytes() {
                        return new byte[0];
                    }

                    @Override
                    public void readBytes(byte[] bytes) {

                    }
                };
            }
        });
        Item.setSettableItem(strId,blockID);
    }
}
