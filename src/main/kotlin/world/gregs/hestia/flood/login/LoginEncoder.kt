package world.gregs.hestia.flood.login

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import world.gregs.hestia.core.cache.crypto.Rsa
import world.gregs.hestia.core.network.codec.packet.PacketWriter
import world.gregs.hestia.flood.Flooder.Companion.RSA_MODULUS
import world.gregs.hestia.flood.Flooder.Companion.RSA_PUBLIC

interface LoginEncoder {

    fun encodePassword(isaacKeys: IntArray, password: String): ByteBuf {
        val buf = isaacBuffer(isaacKeys)
        val buffer = PacketWriter(buffer = buf)
        buffer.apply {
            skip(8)
            writeString(password)
            skip(16)
            val data = ByteArray(position())
            this.buffer.readBytes(data)
            val encryptedData = Rsa.crypt(data, RSA_MODULUS, RSA_PUBLIC)
            writeShort(encryptedData.size)
            writeBytes(encryptedData)
        }
        return buf
    }

    private fun isaacBuffer(isaacKeys: IntArray): ByteBuf {
        val buffer = Unpooled.buffer()
        buffer.apply {
            writeByte(10)
            isaacKeys.forEach {
                writeInt(it)
            }
        }
        return buffer
    }

}