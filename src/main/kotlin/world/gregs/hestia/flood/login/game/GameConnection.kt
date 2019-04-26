package world.gregs.hestia.flood.login.game

import io.netty.channel.ChannelHandler
import org.slf4j.LoggerFactory
import world.gregs.hestia.flood.Client
import world.gregs.hestia.flood.Flooder
import world.gregs.hestia.flood.login.ConnectionClientListener
import world.gregs.hestia.flood.login.game.codec.encode.messages.Handshake

@ChannelHandler.Sharable
class GameConnection : ConnectionClientListener() {

    private val logger = LoggerFactory.getLogger(GameConnection::class.java)!!

    override fun connect(client: Client) {
        client.game?.writeAndFlush(Handshake())
    }

    override fun disconnect(client: Client) {
        logger.warn("Disconnected ${client.name}")
        Flooder.clients.remove(client)
        //Stop if server off
        if(Flooder.clients.isEmpty()) {
            System.exit(0)
        }
    }

}