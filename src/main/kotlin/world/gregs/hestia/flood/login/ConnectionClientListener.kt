package world.gregs.hestia.flood.login

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.ConnectionListener
import world.gregs.hestia.flood.Client
import world.gregs.hestia.flood.Client.Companion.KEY

abstract class ConnectionClientListener : ConnectionListener() {
    override fun connect(ctx: ChannelHandlerContext) {
        connect(getClient(ctx))
    }

    override fun disconnect(ctx: ChannelHandlerContext) {
        disconnect(getClient(ctx))
    }

    abstract fun connect(client: Client)

    abstract fun disconnect(client: Client)

    companion object {
        private fun getClient(ctx: ChannelHandlerContext): Client {
            return ctx.channel().attr(KEY).get() ?: throw IllegalStateException("Client is null")
        }
    }

}