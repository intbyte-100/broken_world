package com.intbyte.bw.gameAPI.environment;

import com.intbyte.bw.gameAPI.utils.Resource;

public class Tools {
    
    public static void newTool(int type, String strId, final String texture, final int level, final int damage, final int strength, final float coolDown, final float takeEndurance){
        Item.addItemFactory(strId, new ItemFactory(strId,type) {
            @Override
            public Item createItem() {
                return new Item() {

                    {
                        icon = Resource.getSprite(texture);
                        itemData = new ItemData(strength,damage,level, coolDown, takeEndurance);
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
    public static void newPickaxe(String strId, String texture, int level, int damage, int strength, float coolDown, float takeEndurance){
        newTool(Item.PICKAXE, strId, texture,level,damage,strength,coolDown,takeEndurance);
    }



    public static void newAxe(String strId, String texture, int level, int damage, int strength,float coolDown, float takeEndurance){
        newTool(Item.PICKAXE, strId, texture,level,damage,strength,coolDown, takeEndurance);
    }
    public static void newBlock(final String strId, final String texture, final String blockID){
        Item.addItemFactory(strId, new ItemFactory(strId,Item.BLOCK) {
            @Override
            public Item createItem() {
                return new Item() {

                    {
                        icon = Resource.getSprite(texture);
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
