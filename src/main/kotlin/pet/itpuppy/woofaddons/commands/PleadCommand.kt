package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

object PleadCommand : ServerCommand {
    private val color = TextColor.fromRgb(0xffe8a3)

    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("plead").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.player ?: return 0
        val broadcaster = ctx.source.server.playerList

        val usernameComponent = Comp.buildUsernameComponent(player)
        val message = Component.translatable(
            "%s %s pleads! \uD83E\uDD7A",

            Comp.literal("PLEAD", true),
            usernameComponent
        ).withColor(color)

        broadcaster.broadcastSystemMessage(message, false)
        return 1
    }
}