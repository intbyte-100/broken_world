package com.intbyte.bw.engine.envinroment


import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import com.intbyte.bw.engine.render.GlobalEnvironment
import com.intbyte.bw.engine.utils.Debug
import kotlin.math.floor


object Time {

    var totalMinutes = 0f
        set(value) {
            field = if (value > 1440) 0f else value
        }

    var minutes: Int
        get() {
            return (totalMinutes - floor(totalMinutes / 60) * 60).toInt()
        }
        set(value) {
            totalMinutes -= minutes + value
        }
    var hours: Int
        get() {
            return (totalMinutes / 60).toInt()
        }
        set(value) {
            totalMinutes = totalMinutes - hours * 60 + value
        }

    fun setTime(hours: Int, minutes: Int) {
        totalMinutes = hours * 24f + minutes
    }

    private var oneMinute = 0f

    @JvmStatic
    fun update() {
        Debug.valueInfo("time", "$hours:$minutes")
        Debug.valueInfo("total minutes", totalMinutes.toInt())

        totalMinutes += Gdx.graphics.deltaTime*30

        if(totalMinutes in 187f..1037f){
            GlobalEnvironment.intensity = MathUtils.lerp(0f, 1f, (totalMinutes-187)/850)
        } else if (totalMinutes in 1037f..1293f){
            GlobalEnvironment.intensity = MathUtils.lerp(1f, 0f, (totalMinutes-1038)/255)
        }

    }


}