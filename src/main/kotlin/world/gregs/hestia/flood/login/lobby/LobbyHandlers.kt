package world.gregs.hestia.flood.login.lobby

import world.gregs.hestia.core.network.codec.MessageDispatcher
import world.gregs.hestia.flood.Flooder
import world.gregs.hestia.flood.login.lobby.handle.LobbyDetailsHandler

class LobbyHandlers(codec: LobbyCodec, flooder: Flooder) : MessageDispatcher() {
    init {
        bind(LobbyDetailsHandler(codec, flooder))
    }
}