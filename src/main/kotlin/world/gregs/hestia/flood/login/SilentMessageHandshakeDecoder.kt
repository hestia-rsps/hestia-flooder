package world.gregs.hestia.flood.login

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.HandshakeCodec
import world.gregs.hestia.core.network.codec.message.Message
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.message.MessageHandshake
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshakeDecoder
import world.gregs.hestia.core.network.codec.packet.Packet

class SilentMessageHandshakeDecoder(codec: HandshakeCodec, handshake: MessageHandshake) : SimpleMessageHandshakeDecoder(codec, handshake) {

    @Suppress("UNCHECKED_CAST")
    override fun decode(ctx: ChannelHandlerContext, msg: Packet, out: MutableList<Any>) {
        val handshake = handshake.shook(ctx)
        val decoder = codec.get(msg.opcode, handshake) as? MessageDecoder<Message> ?:return
        decoder.write(ctx, msg, out)
    }

}