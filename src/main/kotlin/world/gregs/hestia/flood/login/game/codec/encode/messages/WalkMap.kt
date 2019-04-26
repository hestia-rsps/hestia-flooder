package world.gregs.hestia.flood.login.game.codec.encode.messages

import world.gregs.hestia.core.network.codec.message.Message

data class WalkMap(val x: Int, val y: Int) : Message