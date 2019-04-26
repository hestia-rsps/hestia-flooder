package world.gregs.hestia.flood.login.lobby.codec.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.flood.login.lobby.codec.decode.messages.LobbyHandshake

class LobbyHandshakeDecoder : MessageDecoder<LobbyHandshake>(0, 10) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LobbyHandshake? {
        return LobbyHandshake()
    }

}