package world.gregs.hestia.flood.behaviours

import world.gregs.hestia.flood.Behaviour
import world.gregs.hestia.flood.Client

/**
 * Walks around slowly
 */
class Grumpy : Behaviour {

    override fun begin(client: Client) {
        client.step(range.random(), range.random())
    }

    override fun tick(client: Client) {

    }

    companion object {
        val range = -22 .. 22
    }

}