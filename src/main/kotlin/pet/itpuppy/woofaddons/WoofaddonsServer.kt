package pet.itpuppy.woofaddons

import net.fabricmc.api.DedicatedServerModInitializer
import pet.itpuppy.woofaddons.commands.BarkCommand
import pet.itpuppy.woofaddons.commands.BoopCommand
import pet.itpuppy.woofaddons.commands.LoreCommand
import pet.itpuppy.woofaddons.commands.MeowCommand
import pet.itpuppy.woofaddons.commands.PleadCommand
import pet.itpuppy.woofaddons.commands.ResetLoreCommand
import pet.itpuppy.woofaddons.commands.ShowCommand
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