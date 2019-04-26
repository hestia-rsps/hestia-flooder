package world.gregs.hestia.flood.login.game.handle

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.flood.Client.Companion.KEY
import world.gregs.hestia.flood.login.game.GameHandshake
import world.gregs.hestia.flood.login.game.codec.decode.messages.GameHandshakeSuccess
import world.gregs.hestia.flood.login.game.codec.encode.messages.GameLogin

class GameHandshakeHandler : MessageHandler<GameHandshakeSuccess> {

    override fun handle(ctx: ChannelHandlerContext, message: GameHandshakeSuccess) {
        ctx.pipeline().get(GameHandshake::class.java).shake(ctx)
        val client = ctx.channel().attr(KEY).get()
        //Send game login request
        client.send(GameLogin(client))
    }

}