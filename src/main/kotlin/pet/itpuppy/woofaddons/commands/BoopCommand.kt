package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor

object BoopCommand : ServerCommand {
    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("boop")
                    .then(
                        argument("player", EntityArgument.player())
                            .executes(::onExecuteCommand)
                    )
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.player ?: return 0
        val target = EntityArgument.getPlayer(ctx, "player")

        val playerMessage = Component.translatable(
            "%s %s You booped %s!",

            Comp.PRIVATE_ICON_COMPONENT,
            Comp.literal("BOOP", true),
            Comp.buildUsernameComponent(target)
        ).withColor(TextColor.LIGHT_PURPLE)

        val targetMessage = Component.translatable(
            "%s %s %s booped you!",

            Comp.PRIVATE_ICON_COMPONENT,
            Comp.literal("BOOP", true),
            Comp.buildUsernameComponent(player)
        ).withColor(TextColor.LIGHT_PURPLE)

        player.sendSystemMessage(playerMessage)
        target.sendSystemMessage(targetMessage)

        return 1
    }
}
