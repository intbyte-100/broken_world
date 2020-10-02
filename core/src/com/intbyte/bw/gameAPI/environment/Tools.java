package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.Resource;

public class Tools {
    public static ItemFactory newPickaxe(String strId, final String texture, final int level, final int damage, final int strength){
        return new ItemFactory(strId,Item.PICKAXE) {
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
        };
    }
}
