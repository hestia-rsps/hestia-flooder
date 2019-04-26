package world.gregs.hestia.flood.login.lobby.handle

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleIsaacMessageEncoder
import world.gregs.hestia.flood.Client.Companion.KEY
import world.gregs.hestia.flood.Flooder
import world.gregs.hestia.flood.login.lobby.LobbyCodec
import world.gregs.hestia.flood.login.lobby.codec.decode.messages.LobbyDetails

class LobbyDetailsHandler(private val codec: LobbyCodec, private val flooder: Flooder) : MessageHandler<LobbyDetails> {

    override fun handle(ctx: ChannelHandlerContext, message: LobbyDetails) {
        val client = ctx.channel().attr(KEY).get()

        ctx.pipeline().apply {
            //Ignore future messages
            remove("packet")
            remove("decoder")
            remove("handler")
            //Update encoder with isaac encryption
            replace("encoder", "encoder", SimpleIsaacMessageEncoder(codec, client.encrypt!!))
        }

        //Attempt login to game
        flooder.login(client)
    }

}