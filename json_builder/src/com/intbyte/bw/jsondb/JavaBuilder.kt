package com.intbyte.bw.jsondb

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.JsonValue
import java.io.File

class JavaBuilder : AbstractBuilder {

    private val map = mutableMapOf<String, Array<JsonValue>>()
    private val head = """
        package com.intbyte.bw.gameAPI.environment.json_wrapper;
        
        import com.intbyte.bw.gameAPI.environment.*;
        import com.intbyte.bw.gameAPI.physic.PhysicBlockObject;
        import com.badlogic.gdx.math.Rectangle;
        import com.intbyte.bw.gameAPI.utils.Resource;
        
        //TODO: do not delete, this file was automatically generated by json data generator from json files
        public class GeneratedJsonData{
            public static void init(){
                BlockWrapper blockWrapper = new BlockWrapper();
                Block.CustomBlock block;
                PhysicBlockObject object = new PhysicBlockObject();
                object.setShape(new Rectangle(0,0,20,20));
                object.setOffset(10, 10);
                Resource.putBlockObject("10x10",object);
    """.trimIndent()

    private val end = """

            }
        }
    """.trimIndent()

    override fun build(out: String, jsonValues: Array<JsonValue>) {
        sort(jsonValues)
        map.keys.forEach {
            when (it) {
                "block" -> println(it)
            }
        }

        File(out).writeText("$head${generateBlockData()}$end")
    }

    private fun sort(array: Array<JsonValue>) {
        array.forEach {
            val str = it.get("type")?.asString()?.split(":")?.get(0)
            if (map[str] == null && str != null) map[str] = Array()
            map[str]?.add(it)
        }
    }

    private fun generateBlockData(): String {
        var code = ""

        (map["block"] ?: return "")?.forEach {
            code += """
                
        blockWrapper.reset();
        blockWrapper.setId("${it["type"].asString().split(":")[1]}");
        
        """.trimEnd()

            code+="\n\t\tblockWrapper.setModel(\"${it["model"]?.asString()}\");"
            code+="\n\t\tblockWrapper.setTexture(\"${it["texture"]?.asString()}\");"
            code+="\n\t\tblockWrapper.setHealth(${it["health"]?.asString()?:"100"});"
            code+="\n\t\tblockWrapper.setBody(object);"
            val iconScale = it["iconScale"]?.asString()
            val scale = it["scale"]?.asString()
            if(iconScale!=null) code+="\n\t\tblockWrapper.setIconScale(${iconScale}f);"
            else if(scale!=null) code+="\n\t\tblockWrapper.setIconScale(${scale}f);"

            val iconRenderCoord = it["iconRenderCoord"]?.asStringArray()
            val renderCoord = it["renderCoord"]?.asStringArray()

            if(iconRenderCoord!=null) code+="\n\t\tblockWrapper.getIconRender().set(${iconRenderCoord[0]}f,${iconRenderCoord[1]}f,${iconRenderCoord[2]}f);"
            else if(renderCoord!=null) code+="\n\t\tblockWrapper.getIconRender().set(${renderCoord[0]}f,${renderCoord[1]}f,${renderCoord[2]}f);"

            code+="\n\t\tblockWrapper.setType(Block.${(it["blockType"]?.asString()?:"STONE").toUpperCase()});"
            code+="\n\t\tblockWrapper.setLevel(${it["level"]?.asString()?:"1"});"

            code+="\n\t\tblock = Block.defineBlock(blockWrapper);\n\t\tblock.updateIcon();".trimEnd()



            if(scale!=null) code+="\n\t\tblock.setScale(${scale}f);"
            if(renderCoord!=null) code+="\n\t\tblock.setPosition(${renderCoord[0]}f,${renderCoord[1]}f,${renderCoord[2]}f);"

        }
        return code
    }

    private fun generatePhysicBodyData(): String{
        var code = ""

        return code
    }
}


