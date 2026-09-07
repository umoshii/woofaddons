package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

object BarkCommand : ServerCommand {
    private val barks = listOf("BARK", "WOOF", "AWRF", "AWOO", "ARFF")
    private val color = TextColor.fromRgb(0xffc387)

    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("bark").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.player ?: return 0
        val broadcaster = ctx.source.server.playerList

        val message = Component.translatable(
            "%s %s barked!",

            Comp.literal(barks.random(), true),
            Comp.buildUsernameComponent(player)
        ).withColor(color)

        broadcaster.broadcastSystemMessage(message, false)
        return 1
    }
}