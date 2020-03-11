package world.gregs.hestia.flood.login.game.codec.encode

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOGIN_HANDSHAKE
import world.gregs.hestia.flood.login.game.codec.encode.messages.Handshake

class GameHandshakeEncoder : MessageEncoder<Handshake>() {

    override fun encode(builder: PacketBuilder, message: Handshake) {
        builder.apply {
            writeByte(LOGIN_HANDSHAKE)
        }
    }

}