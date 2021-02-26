package com.intbyte.bw.jsondb

import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.JsonReader
import com.badlogic.gdx.utils.JsonValue
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.stream.Collectors

data class Linker(val config: Config,val builder: AbstractBuilder) {
    private val reader = JsonReader()
    private fun readFileDirectlyAsText(fileName: String): String = File(fileName).readText(Charsets.UTF_8)


    private fun getFilesList(str: String): kotlin.Array<String> {
        val array = mutableListOf<String>()
        val path = Paths.get(str)
        val fileDirMap = Files.list(path).collect(Collectors.partitioningBy { Files.isDirectory(it) })
        getFilesList(array,fileDirMap)
        return array.toTypedArray()
    }

    private fun getFilesList(array: MutableList<String>, fileDirMap: Map<Boolean, MutableList<Path>>) {
        fileDirMap[false]?.forEach { array.add(readFileDirectlyAsText(it.toAbsolutePath().toString())) }
        fileDirMap[true]?.forEach { it ->
            getFilesList(array, Files.list(it).collect(Collectors.partitioningBy { Files.isDirectory(it) }))
        }
    }
    fun link(){
        val array = getFilesList(config.src)
        val jsonElements = Array<JsonValue>()
        array.forEach { i ->
            val json = reader.parse(i)
            val group = json.get("group")
            if(group != null&&group.isArray)
                for(element in group)
                    jsonElements.add(element)
            else
                jsonElements.add(json)
        }
        builder.build(config.out,jsonElements)
    }
}