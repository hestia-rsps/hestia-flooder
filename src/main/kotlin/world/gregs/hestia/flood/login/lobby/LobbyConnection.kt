package world.gregs.hestia.flood.login.lobby

import io.netty.channel.ChannelHandler
import world.gregs.hestia.flood.Client
import world.gregs.hestia.flood.login.ConnectionClientListener
import world.gregs.hestia.flood.login.lobby.codec.encode.messages.LobbyLogin

@ChannelHandler.Sharable
class LobbyConnection : ConnectionClientListener() {

    override fun connect(client: Client) {
        //Send lobby login request
        client.send(LobbyLogin(client), true)
    }

    override fun disconnect(client: Client) {
    }
}