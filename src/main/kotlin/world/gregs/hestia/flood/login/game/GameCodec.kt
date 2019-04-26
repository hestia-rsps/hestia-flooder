package world.gregs.hestia.flood.login.game

import world.gregs.hestia.core.network.codec.MessageHandshakeCodec
import world.gregs.hestia.core.network.protocol.decoders.PingDecoder
import world.gregs.hestia.flood.login.game.codec.decode.GameHandshakeSuccessDecoder
import world.gregs.hestia.flood.login.game.codec.decode.GameLoginDetailsDecoder
import world.gregs.hestia.flood.login.game.codec.decode.MapRegionDecoder
import world.gregs.hestia.flood.login.game.codec.encode.GameHandshakeEncoder
import world.gregs.hestia.flood.login.game.codec.encode.GameLoginEncoder
import world.gregs.hestia.flood.login.game.codec.encode.WalkMapEncoder

class GameCodec : MessageHandshakeCodec() {
    init {
        bind(GameHandshakeSuccessDecoder(), true)
        bind(GameLoginDetailsDecoder())
        bind(MapRegionDecoder())
        bind(PingDecoder())

        bind(GameHandshakeEncoder())
        bind(GameLoginEncoder())
        bind(WalkMapEncoder())
    }
}