package world.gregs.hestia.flood

interface Behaviour {
    fun begin(client: Client)

    fun tick(client: Client)
}