package com.intbyte.bw.engine.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.math.Vector3
import com.intbyte.bw.engine.entity.Player
import com.intbyte.bw.engine.graphic.Graphic
import com.intbyte.bw.engine.input.GameInputProcessor
import com.intbyte.bw.engine.utils.gdx.render
import com.intbyte.bw.engine.world.World
import kotlin.math.roundToInt

open class FastRender(val batch: ModelBatch) : Render {
    val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private val player = Player.getPlayer()
    private val screenEdgePoint = Vector3()
    private var x = 0
    private var z = 0
    private var x1 =0
    private var z1 = 0


    init {
        camera.far = 30f
        camera.near = 0.1f
    }
    override fun render() {
        cameraUpdate(player.x,4.96f,player.z-1.8f,player.x-0.0008f,1f,player.z-0.618f)
        batch.render(camera) {
            for (xDraw in x..x1+1 step 2){
                for (zDraw in z1+1 downTo z-2 step 2){
                    if (!boundsInFrustum(xDraw.toFloat(),zDraw.toFloat(),0.5f,0.5f)) return
                    World.getChunck(xDraw.toFloat(), zDraw.toFloat()).render(0f,0f,0f)
                }
            }
        }
    }





    override fun resize(width: Int, height: Int) {
        cameraUpdate(0f,4.96f,-1.8f,-0.0008f,1f,-0.618f)
        x = (GameInputProcessor.getFastBlock(camera,screenEdgePoint, Graphic.getScreenWidth(), 0).x).toInt()
        z = GameInputProcessor.getFastBlock(camera, screenEdgePoint, 0, Graphic.getScreenHeight()).z.toInt()
        x1 = GameInputProcessor.getFastBlock(camera, screenEdgePoint, 0, 0).x.roundToInt() + 1
        z1 = GameInputProcessor.getFastBlock(camera, screenEdgePoint, 0, 0).z.toInt() + 1
        cameraUpdate(player.x,4.96f,player.z-1.8f,player.x-0.0008f,1f,-0.618f)
    }

    private fun cameraUpdate(x: Float, y: Float, z: Float, lookX: Float, lookY: Float, lookZ: Float){
        camera.position.set(x,y,z)
        camera.lookAt(lookX,lookY,lookZ)
        camera.update()
    }

    private fun boundsInFrustum(x: Float, z: Float, halfWidth: Float, halfDepth: Float): Boolean = camera.frustum.boundsInFrustum(x,0f,z,halfWidth,0f,halfDepth)
}