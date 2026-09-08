package pet.itpuppy.woofaddons

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pet.itpuppy.woofaddons.commands.ServerCommand
import pet.itpuppy.woofaddons.events.ChatEvent

object Woofaddons : ModInitializer {
	const val MOD_ID: String = "woofaddons"
	val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		LOGGER.info("woof!")

        return // Return when testing in singleplayer
        ChatEvent.register()

        ServerCommand::class.sealedSubclasses
            .mapNotNull { it.objectInstance }
            .forEach { it.register() }
	}

	fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
