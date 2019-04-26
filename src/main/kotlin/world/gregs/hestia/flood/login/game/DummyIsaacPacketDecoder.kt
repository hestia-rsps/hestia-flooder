package world.gregs.hestia.flood.login.game

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.cache.crypto.Cipher
import world.gregs.hestia.core.network.codec.decode.IsaacPacketDecoder
import world.gregs.hestia.flood.Flooder
import java.io.DataInputStream

class DummyIsaacPacketDecoder(cipher: Cipher) : IsaacPacketDecoder(cipher) {

    override fun getSize(ctx: ChannelHandlerContext, opcode: Int): Int? {
        return opcodes.firstOrNull { it.first == opcode }?.second
    }

    companion object {
        private var opcodes = ArrayList<Pair<Int, Int>>()

        init {
            val stream = DataInputStream(Flooder::class.java.getResourceAsStream("opcodes.dat"))
            while(stream.available() > 0) {
                opcodes.add(Pair(stream.read(), stream.readByte().toInt()))
            }
        }
    }

}