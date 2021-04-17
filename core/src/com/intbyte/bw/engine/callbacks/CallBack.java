package com.intbyte.bw.engine.callbacks;

import com.badlogic.gdx.math.Vector3;
import com.intbyte.bw.engine.input.GameInputProcessor;
import com.intbyte.bw.engine.GameThread;


public class CallBack {


    public static void addCallBack(Initialization callback) {
        Initialization.callBacks.add(callback);
    }

    public static void addCallBack(Render callback) {
        Render.callBacks.add(callback);
    }

    public static void addCallBack(Drag callback) {
        Drag.callBacks.add(callback);
    }

    public static void addCallBack(BlockHit callback) {
        BlockHit.callBacks.add(callback);
    }

    public static void addCallBack(TouchOnBlock callback) {
        TouchOnBlock.callBacks.add(callback);
    }

    public static void addCallBack(Touch callback){
        Touch.callBacks.add(callback);
    }

    public static void executeInitializationCallBacks() {
        Initialization.iterator = Initialization.callBacks.iterator();
        if (com.intbyte.bw.engine.Initialization.isReadyCallBack())
            while (Initialization.iterator.hasNext()) {
                Initialization.iterator.next().main();
                Initialization.iterator.remove();
            }
    }

    public static void executeRenderCallBacks() {
        if (GameThread.isReadyCallBack())
            for (Render i : Render.callBacks)
                i.main();
    }

    public static void executeDraggedCallBacks(Vector3 position) {
        if (GameInputProcessor.isReadyCallBack())
            for (Drag i : Drag.callBacks)
                i.main(position);
    }

    public static void executeTouchedCallBacks(Vector3 position) {
        if (GameInputProcessor.isReadyCallBack())
            for (Touch i : Touch.callBacks)
                i.main(position);
    }

    public static void executeTouchOnBlockCallBack(float x, float z) {
        if (GameInputProcessor.isReadyCallBack())
            for (TouchOnBlock i : TouchOnBlock.callBacks)
                i.main(x, z);

    }

    public static void executeBlockHitCallBack(int x, int z, int itemID) {
        if (GameInputProcessor.isReadyCallBack())
            for (BlockHit i : BlockHit.callBacks)
                i.main(x, z, itemID);
    }
}
