package world.gregs.hestia.flood.login.game.codec.decode

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.protocol.ClientOpcodes.REGION
import world.gregs.hestia.flood.login.game.codec.decode.messages.MapRegion

class MapRegionDecoder : MessageDecoder<MapRegion>(Packet.Type.VAR_SHORT, REGION) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): MapRegion? {
        packet.startBitAccess()
        return MapRegion(packet.readBits(30))
    }

}