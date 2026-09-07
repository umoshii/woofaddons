package pet.itpuppy.woofaddons.commands

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack

sealed interface ServerCommand {
    fun register()
    fun onExecuteCommand(ctx: CommandContext<CommandSourceStack>): Int
}