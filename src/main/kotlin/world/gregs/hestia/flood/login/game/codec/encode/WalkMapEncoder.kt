package world.gregs.hestia.flood.login.game.codec.encode

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Endian
import world.gregs.hestia.core.network.codec.packet.Modifier
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.WALK
import world.gregs.hestia.flood.login.game.codec.encode.messages.WalkMap

class WalkMapEncoder : MessageEncoder<WalkMap>() {

    override fun encode(builder: PacketBuilder, message: WalkMap) {
        val (x, y) = message
        builder.apply {
            writeOpcode(WALK)
            writeShort(x, Modifier.ADD, Endian.LITTLE)
            writeShort(y, Modifier.ADD, Endian.LITTLE)
            writeByte(0)
        }
    }

}