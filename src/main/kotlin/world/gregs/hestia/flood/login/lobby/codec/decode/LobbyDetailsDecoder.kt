package world.gregs.hestia.flood.login.lobby.codec.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.LOBBY_DETAILS
import world.gregs.hestia.flood.login.lobby.codec.decode.messages.LobbyDetails

class LobbyDetailsDecoder : MessageDecoder<LobbyDetails>(Packet.Type.VAR_BYTE, LOBBY_DETAILS) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): LobbyDetails? {
        return LobbyDetails()
    }

}