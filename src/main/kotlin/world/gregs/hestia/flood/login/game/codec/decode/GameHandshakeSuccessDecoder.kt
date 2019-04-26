package world.gregs.hestia.flood.login.game.codec.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.flood.login.game.codec.decode.messages.GameHandshakeSuccess

class GameHandshakeSuccessDecoder : MessageDecoder<GameHandshakeSuccess>(0, 0) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): GameHandshakeSuccess? {
        return GameHandshakeSuccess()
    }

}