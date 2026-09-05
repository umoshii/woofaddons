package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

object ShowCommand : ServerCommand {
    private val shows = listOf("SHOW", "LOOK", "HERE")

    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("show").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player      = ctx.source.player ?: return 0
        val username    = player.displayName.copy()
        val heldItem    = player.activeItem

        val message = Component.translatable(
            "%s %s is holding %s",

            Comp.literal(shows.random(), true),
            username.withColor(player.teamColor),
            heldItem.displayName
        ).withColor(TextColor.GRAY)

        val broadcaster = ctx.source.server.playerList
        broadcaster.broadcastSystemMessage(message, false)
        return 1
    }
}