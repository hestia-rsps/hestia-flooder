package world.gregs.hestia.flood.login.game

import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.MessageHandshakeDispatcher
import world.gregs.hestia.flood.login.game.handle.GameHandshakeHandler
import world.gregs.hestia.flood.login.game.handle.GameLoginDetailsHandler
import world.gregs.hestia.flood.login.game.handle.MapRegionHandler

class GameHandlers(codec: Codec) : MessageHandshakeDispatcher() {
    init {
        bind(GameHandshakeHandler(), true)
        bind(GameLoginDetailsHandler(codec))
        bind(MapRegionHandler())
    }
}