package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor
import pet.itpuppy.woofaddons.utils.Comp

object CoordsCommand : ServerCommand {
    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("coords").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.player ?: return 0
        val broadcaster = ctx.source.server.playerList
        val currentDimension = player.level().dimension().identifier().toShortString()

        val positionComponent = Component.translatable(
            "[%s %s %s]",

            player.blockX,
            player.blockY,
            player.blockZ
        ).withColor(TextColor.YELLOW)

        val dimensionComponent = Component.translatable(
            "[%s]",

            parseIdentifierKey(currentDimension)
        ).withColor(TextColor.YELLOW)

        val message = Component.translatable(
            "%s %s is currently at %s in %s",

            Comp.literal("COORD", true),
            Comp.buildUsernameComponent(player),
            positionComponent,
            dimensionComponent
        ).withColor(TextColor.GRAY)

        broadcaster.broadcastSystemMessage(message, false)
        return 1
    }

    private fun parseIdentifierKey(key: String): String {
        return key.split("_").joinToString(" ") { word -> word.replaceFirstChar { it.uppercase() } }
    }
}