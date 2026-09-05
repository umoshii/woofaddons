package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

object MeowCommand : ServerCommand {
    private val meows = listOf("MEOW", "MRRP", "MROW", "NYAN", "PRRR")
    private val color = TextColor.fromRgb(0xdca1bb)

    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("mrrp").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player      = ctx.source.player ?: return 0
        val username    = player.displayName.copy()

        val message = Component.translatable(
            "%s %s meowed!",

            Comp.literal(meows.random(), true),
            username.withColor(player.teamColor)
        ).withColor(color)

        val broadcaster = ctx.source.server.playerList
        broadcaster.broadcastSystemMessage(message, false)
        return 1
    }
}