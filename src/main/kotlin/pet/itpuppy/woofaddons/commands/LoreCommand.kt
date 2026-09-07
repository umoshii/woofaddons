package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import pet.itpuppy.woofaddons.utils.Comp
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.Commands.argument
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.TextColor
import net.minecraft.world.item.component.ItemLore

object LoreCommand : ServerCommand {
    private val color: TextColor = TextColor.fromRgb(0x8a8a8a)

    override fun register() {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(
                Commands.literal("lore").then(argument("lore", StringArgumentType.greedyString())
                    .executes(::onExecuteCommand))
            )
        }
    }

    override fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int {
        val player = ctx.source.player ?: return 0

        val heldItem = player.activeItem
        val lore = StringArgumentType.getString(ctx, "lore")

        heldItem.set(DataComponents.LORE, ItemLore(
            lore.split("\\n").map { Comp.literal(it, color) }
        ))

        val message = Component.translatable(
            "%s %s Applied lore to item %s",

            Comp.PRIVATE_ICON_COMPONENT,
            Comp.literal("LORE", true),
            heldItem.displayName
        ).withColor(TextColor.GRAY)

        player.sendSystemMessage(message)
        return 1
    }
}