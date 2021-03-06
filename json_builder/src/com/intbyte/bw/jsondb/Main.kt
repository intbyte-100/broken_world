package com.intbyte.bw.jsondb


fun main(args: Array<String>) {
    val config = Config("${args[0]}/json","${args[0]}/src/com/intbyte/bw/game/GeneratedJsonData.java")
    val builder = JavaBuilder()
    val linker = Linker(config,builder)
    linker.link()
}
