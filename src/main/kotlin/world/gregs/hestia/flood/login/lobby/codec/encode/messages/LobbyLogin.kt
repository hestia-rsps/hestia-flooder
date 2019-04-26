package world.gregs.hestia.flood.login.lobby.codec.encode.messages

import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.flood.Client

data class LobbyLogin(val username: String, val password: String, val isaacKeys: IntArray) : Message {

    constructor(client: Client) : this(client.name, client.password, client.generateIsaacKeys())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LobbyLogin

        if (username != other.username) return false
        if (password != other.password) return false
        if (!isaacKeys.contentEquals(other.isaacKeys)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + isaacKeys.contentHashCode()
        return result
    }
}