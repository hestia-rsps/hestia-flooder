package world.gregs.hestia.flood.login.game

import world.gregs.hestia.flood.Flooder
import java.io.DataInputStream

object Opcodes {
    var opcodes = ArrayList<Pair<Int, Int>>()

    init {
        println(Flooder::class.java.getResourceAsStream("opcodes.dat"))
        val stream = DataInputStream(Flooder::class.java.getResourceAsStream("opcodes.dat"))
        while(stream.available() > 0) {
            opcodes.add(Pair(stream.read(), stream.readByte().toInt()))
        }
    }
}