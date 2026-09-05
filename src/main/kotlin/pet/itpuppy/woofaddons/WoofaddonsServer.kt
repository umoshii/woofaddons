package pet.itpuppy.woofaddons

import net.fabricmc.api.DedicatedServerModInitializer
import pet.itpuppy.woofaddons.commands.implementation.*
import pet.itpuppy.woofaddons.events.ChatEvent

object WoofaddonsServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        val events = listOf(
            ChatEvent
        )
        events.forEach { it.register() }

        val commands = listOf(
            // show
            ShowCommand,

            // boop
            BoopCommand,

            // sounds
            BarkCommand,
            MeowCommand,
            PleadCommand,

            // lore
            LoreCommand,
            ResetLoreCommand
        )
        commands.forEach { it.register() }
    }
}