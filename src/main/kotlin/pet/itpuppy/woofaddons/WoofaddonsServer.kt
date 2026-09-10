package pet.itpuppy.woofaddons

import net.fabricmc.api.DedicatedServerModInitializer
import pet.itpuppy.woofaddons.commands.ServerCommand
import pet.itpuppy.woofaddons.events.ServerEvent

object WoofaddonsServer : DedicatedServerModInitializer {
    override fun onInitializeServer() {
        ServerEvent::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .forEach { it.register() }

        ServerCommand::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .forEach { it.register() }
    }
}