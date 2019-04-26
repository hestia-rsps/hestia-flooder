package world.gregs.hestia.flood.login.game.handle

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.Codec
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.codec.message.SimpleIsaacMessageEncoder
import world.gregs.hestia.flood.Client.Companion.KEY
import world.gregs.hestia.flood.login.game.DummyIsaacPacketDecoder
import world.gregs.hestia.flood.login.game.codec.decode.messages.GameLoginDetails

class GameLoginDetailsHandler(private val codec: Codec) : MessageHandler<GameLoginDetails> {

    private val logger = LoggerFactory.getLogger(GameLoginDetailsHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: GameLoginDetails) {
        val (rights, index, name) = message
        val client = ctx.channel().attr(KEY).get()

        ctx.pipeline().apply {
            //Update packet with isaac dummy
            replace("packet", "packet", DummyIsaacPacketDecoder(client.decrypt))
            //Update encoder with isaac encryption
            replace("encoder", "encoder", SimpleIsaacMessageEncoder(codec, client.encrypt))
        }

        //Basic check that every thing is going okay
        if (name != client.name) {
//            throw IllegalStateException("Name mismatch $name ${client.name}")
        }

        //Notify client they are logged in
        logger.info("'$name' connected index: $index rights: $rights")
        client.login(index)
    }

}