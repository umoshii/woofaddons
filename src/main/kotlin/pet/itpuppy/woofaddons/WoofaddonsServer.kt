package pet.itpuppy.woofaddons

import net.fabricmc.api.DedicatedServerModInitializer
import pet.itpuppy.woofaddons.commands.*
import pet.itpuppy.woofaddons.events.ChatEvent

object WoofaddonsServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        val events = listOf(ChatEvent)
        events.forEach { it.register() }

        val commands = listOf(ShowCommand, BarkCommand, MeowCommand, BoopCommand, PleadCommand, LoreCommand)
        commands.forEach { it.register() }
    }
}