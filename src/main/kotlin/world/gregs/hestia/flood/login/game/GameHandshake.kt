package world.gregs.hestia.flood.login.game

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshake

@ChannelHandler.Sharable
class GameHandshake(dispatcher: HandshakeDispatcher) : SimpleMessageHandshake(dispatcher)