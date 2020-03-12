package world.gregs.hestia.flood.behaviours

import world.gregs.hestia.flood.Behaviour
import world.gregs.hestia.flood.Client
import kotlin.random.Random

/**
 * Doesn't do anything
 */
class Sleepy : Behaviour {

    override fun begin(client: Client) {
        client.step(range.random(), range.random())
    }

    override fun tick(client: Client) {
        if(Random.nextDouble() < 0.25) {
            client.step(range.random(), range.random())
        }
    }


    companion object {
        val range = -22 .. 22
    }

}