package world.gregs.hestia.flood

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants.LOCALHOST
import world.gregs.hestia.core.network.client.Connection
import world.gregs.hestia.core.network.codec.decode.SimplePacketDecoder
import world.gregs.hestia.core.network.codec.decode.SimplePacketHandshakeDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageEncoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandler
import world.gregs.hestia.core.network.pipe.Pipeline
import world.gregs.hestia.flood.Client.Companion.KEY
import world.gregs.hestia.flood.login.SilentMessageHandshakeDecoder
import world.gregs.hestia.flood.login.game.GameCodec
import world.gregs.hestia.flood.login.game.GameConnection
import world.gregs.hestia.flood.login.game.GameHandlers
import world.gregs.hestia.flood.login.game.GameHandshake
import world.gregs.hestia.flood.login.lobby.LobbyCodec
import world.gregs.hestia.flood.login.lobby.LobbyConnection
import world.gregs.hestia.flood.login.lobby.LobbyHandlers
import java.io.DataInputStream
import java.math.BigInteger
import java.util.concurrent.atomic.AtomicInteger

class Flooder {

    //Setup lobby and game networking
    private val lobby = setupLobby(this)
    private val game = setupGame()

    fun create(count: Int) {
        runBlocking {
            repeat(count) {
                lobby.connect(LOBBY_ADDRESS, LOBBY_PORT)
                delay(50L)
            }
        }
    }

    /**
     * Attempts to login to the game-server from the lobby
     * @param client The client attempting to connect
     */
    fun login(client: Client) {
        //Connect to the game-server
        val channel = game.connect(GAME_ADDRESS, GAME_PORT).channel()
        client.game = channel
        channel.attr(KEY).set(client)
    }

    companion object {
        val clients = ArrayList<Client>()
        lateinit var RSA_MODULUS: BigInteger
        lateinit var RSA_PUBLIC: BigInteger
        private val count = AtomicInteger(0)

        private const val LOBBY_ADDRESS = LOCALHOST
        private const val GAME_ADDRESS = LOCALHOST

        private const val LOBBY_PORT = 50015
        private const val GAME_PORT = 50001

        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            RSA_MODULUS = BigInteger(Settings.getString("rsaModulus"), 16)
            RSA_PUBLIC = BigInteger(Settings.getString("rsaPublic"), 16)
            Flooder().create(2000)//The number of clients to make
        }

        /**
         * Sets up the social-server network connection
         * @return The connection bootstrap
         */
        private fun setupLobby(flooder: Flooder): Connection {
            val crc = loadCheckSums()
            val codec = LobbyCodec(crc)
            val handlers = LobbyHandlers(codec, flooder)

            //Setup individual pipeline
            val pipeline = Pipeline {
                //If a new channel create a client
                val client = Client(count.getAndIncrement(), it.channel())
                it.channel().attr(KEY).setIfAbsent(client)
                //Packet decoding
                it.addLast("packet", SimplePacketDecoder(codec))
            }

            //Setup shared pipeline
            pipeline.apply {
                //Decode
                add(SimpleMessageDecoder(codec), "decoder")
                //Handle
                add(SimpleMessageHandler(handlers), "handler")
                //Encode
                add(SimpleMessageEncoder(codec), "encoder")
                //Connection
                add(LobbyConnection())
            }

            return Connection(pipeline)
        }

        /**
         * Sets up the game-server network connection
         * @return The connection bootstrap
         */
        private fun setupGame(): Connection {
            val codec = GameCodec()
            val handshake = GameHandshake(GameHandlers(codec))

            //Setup individual pipeline
            val pipeline = Pipeline {
                it.addLast("packet", SimplePacketHandshakeDecoder(codec, handshake))
            }

            //Setup shared pipeline
            pipeline.apply {
                //Decode
                add(SilentMessageHandshakeDecoder(codec, handshake), "decoder")
                //Handle
                add(handshake, "handler")
                //Encode
                add(SimpleMessageEncoder(codec), "encoder")
                //Connection
                add(GameConnection())
            }

            return Connection(pipeline)
        }

        /**
         * Loads cache index checksum values from checksum.dat file
         * @return complete list of checksum values
         */
        private fun loadCheckSums(): IntArray {
            val stream = DataInputStream(Flooder::class.java.getResourceAsStream("checksum.dat"))
            val count = stream.readByte().toInt()
            val sums = arrayOfNulls<Int>(count)
            repeat(count) {
                sums[it] = stream.readInt()
            }
            return sums.filterNotNull().toIntArray()
        }
    }
}