package com.intbyte.bw.gameAPI.environment.json_wrapper

import com.badlogic.gdx.math.Vector3
import com.intbyte.bw.gameAPI.environment.Block
import com.intbyte.bw.gameAPI.physic.PhysicBlockObject

class BlockWrapper : Wrapper{
    var id = ""
    var dropID = ""
    var dropCount = 1
    var texture = ""
    var model = ""
    var scale = 1f
    var iconScale = 1f
    var render = Vector3(0f,0f,0f)
    var iconRender = Vector3(0f,0f,0f)
    var type = 0
    var health = 0
    var level = 0
    var body: PhysicBlockObject? = null
    override fun reset(){
        render.set(0f,0f,0f)
        iconRender.set(0f,0f,0f)
        scale = 1f
        iconScale = 1f
    }
}