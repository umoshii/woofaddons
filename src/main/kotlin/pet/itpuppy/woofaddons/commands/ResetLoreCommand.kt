package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor
import net.minecraft.world.item.component.ItemLore
import pet.itpuppy.woofaddons.utils.Comp

object ResetLoreCommand : ServerCommand {
    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("resetlore").executes(::onExecuteCommand)
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player   = ctx.source.player ?: return 0
        val heldItem = player.activeItem

        heldItem.set(DataComponents.LORE, ItemLore(listOf(Component.empty())))
        val message = Component.translatable(
            "%s %s Reset lore for item %s",

            Comp.PRIVATE_ICON_COMPONENT,
            Comp.literal("LORE", true),
            heldItem.displayName
        ).withColor(TextColor.GRAY)

        player.sendSystemMessage(message)
        return 1
    }
}