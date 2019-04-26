package world.gregs.hestia.flood.login.game.codec.encode

import world.gregs.hestia.core.cache.crypto.Xtea
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes
import world.gregs.hestia.flood.login.LoginEncoder
import world.gregs.hestia.flood.login.game.codec.encode.messages.GameLogin

class GameLoginEncoder : MessageEncoder<GameLogin>(), LoginEncoder {

    override fun encode(builder: PacketBuilder, message: GameLogin) {
        val (username, password, isaacKeys) = message
        builder.apply {
            writeOpcode(ClientOpcodes.GAME_LOGIN, Packet.Type.VAR_SHORT)
            writeInt(667)
            writeByte(0)
            writeBytes(encodePassword(isaacKeys, password))
            val encryptionStart = position()
            writeString(username)
            skip(36)
            writeByte(35)
            writeByte(25)
            skip(34)
            writeByte(5)
            skip(190)
            //Xtea encryption
            Xtea.encipher(buffer, encryptionStart, position(), isaacKeys)
        }
    }

}