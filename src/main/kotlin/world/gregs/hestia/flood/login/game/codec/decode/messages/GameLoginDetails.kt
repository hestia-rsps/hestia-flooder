package world.gregs.hestia.flood.login.game.codec.decode.messages

import world.gregs.hestia.core.network.codec.message.Message

data class GameLoginDetails(val rights: Int, val index: Int, val name: String) : Message