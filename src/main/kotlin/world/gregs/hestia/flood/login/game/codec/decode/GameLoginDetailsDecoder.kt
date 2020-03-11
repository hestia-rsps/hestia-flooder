package world.gregs.hestia.flood.login.game.codec.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOGIN_DETAILS
import world.gregs.hestia.flood.login.game.codec.decode.messages.GameLoginDetails

class GameLoginDetailsDecoder : MessageDecoder<GameLoginDetails>(Packet.Type.VAR_BYTE, LOGIN_DETAILS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): GameLoginDetails? {
        val rights = packet.readByte()
        packet.skip(5)
        val index = packet.readShort()
        packet.skip(5)
        val name = packet.readString()
        return GameLoginDetails(rights, index, name)
    }

}