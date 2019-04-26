package world.gregs.hestia.flood.login.game.handle

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.flood.Client
import world.gregs.hestia.flood.login.game.codec.decode.messages.MapRegion

class MapRegionHandler : MessageHandler<MapRegion> {

    override fun handle(ctx: ChannelHandlerContext, message: MapRegion) {
        val client = ctx.channel().attr(Client.KEY).get()
        if(client.position == -1) {
            client.position = message.position
            client.start()
        }
    }

}