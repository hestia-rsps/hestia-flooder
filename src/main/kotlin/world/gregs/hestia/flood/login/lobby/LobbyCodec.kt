package world.gregs.hestia.flood.login.lobby

import world.gregs.hestia.core.network.codec.MessageHandshakeCodec
import world.gregs.hestia.flood.login.lobby.codec.decode.LobbyDetailsDecoder
import world.gregs.hestia.flood.login.lobby.codec.decode.LobbyHandshakeDecoder
import world.gregs.hestia.flood.login.lobby.codec.encode.LobbyLoginEncoder

class LobbyCodec(crc: IntArray) : MessageHandshakeCodec() {
    init {
        bind(LobbyHandshakeDecoder(), true)
        bind(LobbyDetailsDecoder())

        bind(LobbyLoginEncoder(crc))
    }
}