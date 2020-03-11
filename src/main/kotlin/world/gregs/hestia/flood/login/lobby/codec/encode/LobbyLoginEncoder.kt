package world.gregs.hestia.flood.login.lobby.codec.encode

import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.packet.Packet
import world.gregs.hestia.core.network.packet.PacketBuilder
import world.gregs.hestia.core.network.protocol.ClientOpcodes
import world.gregs.hestia.core.services.Xtea
import world.gregs.hestia.flood.login.LoginEncoder
import world.gregs.hestia.flood.login.lobby.codec.encode.messages.LobbyLogin

class LobbyLoginEncoder(private val crc: IntArray) : MessageEncoder<LobbyLogin>(),
    LoginEncoder {

    override fun encode(builder: PacketBuilder, message: LobbyLogin) {
        val (username, password, isaacKeys) = message
        builder.apply {
            writeOpcode(ClientOpcodes.LOBBY_LOGIN, Packet.Type.VAR_SHORT)
            //Packet
            writeInt(NetworkConstants.CLIENT_MAJOR_VERSION)
            writeBytes(encodePassword(isaacKeys, password))
            val encryptionStart = position()
            writeString(username)
            //Misc details
            skip(31)
            //Cache checksum values
            crc.forEach {
                writeInt(it)
            }
            //Xtea encryption
            Xtea.encipher(buffer, encryptionStart, position(), isaacKeys)
        }
    }

}