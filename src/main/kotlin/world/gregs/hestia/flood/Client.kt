package world.gregs.hestia.flood

import io.netty.channel.Channel
import io.netty.util.AttributeKey
import world.gregs.hestia.core.cache.crypto.Isaac
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.flood.behaviours.*
import world.gregs.hestia.flood.login.game.codec.encode.messages.WalkMap

data class Client(val name: String, val password: String, var lobby: Channel?) {

    constructor(number: Int, lobby: Channel) : this("Client$number", "Password$number", lobby)

    var index = -1
    lateinit var encrypt: Isaac
    lateinit var decrypt: Isaac
    var game: Channel? = null
    var position: Int = -1

    val plane
        get() = position shr 28
    val x
        get() = position shr 14 and 0x3fff
    val y
        get() = position and 0x3fff

    private var behaviour = behaviours.last()//behaviours.random() TODO TEMP

    fun generateIsaacKeys(): IntArray {
        //Generate
        val isaacKeys = intArrayOf(
            (Math.random() * 9.9999999E7).toInt(),
            (Math.random() * 9.9999999E7).toInt(),
            (Math.random() * 9.9999999E7).toInt(),
            (Math.random() * 9.9999999E7).toInt()
        )

        //Set encrypt
        encrypt = Isaac(isaacKeys)
        //Set decrypt
        decrypt = Isaac(isaacKeys.map { it + 50 }.toIntArray())
        //Return
        return isaacKeys
    }

    fun login(index: Int) {
        this.index = index
        lobby?.close()
        lobby = null
    }

    fun start() {
        behaviour.begin(this)
    }

    fun step(deltaX: Int, deltaY: Int) {
        walk(x + deltaX, y + deltaY)
    }

    fun walk(x: Int, y: Int) {
        send(WalkMap(x, y))
        position = y + (x shl 14) + (plane shl 28)
    }

    fun send(message: Message, lobby: Boolean = false) {
        val channel = (if (lobby) this.lobby else game)
            ?: throw IllegalStateException("Not connected to ${if (lobby) "lobby" else "game"}-server, can't send message: $message")
        channel.writeAndFlush(message)
    }

    companion object {
        val KEY: AttributeKey<Client> = AttributeKey.valueOf<Client>("client.name")
        private val behaviours = arrayListOf(Happy(), Sleepy(), Sneezy(), Dopey(), Grumpy())
    }
}