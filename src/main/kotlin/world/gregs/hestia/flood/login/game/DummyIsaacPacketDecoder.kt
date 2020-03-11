package world.gregs.hestia.flood.login.game

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.cache.crypto.Cipher
import world.gregs.hestia.core.network.codec.decode.IsaacPacketDecoder
import world.gregs.hestia.flood.login.game.Opcodes.opcodes

class DummyIsaacPacketDecoder(cipher: Cipher) : IsaacPacketDecoder(cipher) {

    override fun getSize(ctx: ChannelHandlerContext, opcode: Int): Int? {
        return opcodes.firstOrNull { it.first == opcode }?.second
    }
}