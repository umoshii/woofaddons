package pet.itpuppy.woofaddons.utils

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextColor
import net.minecraft.server.level.ServerPlayer

object Comp {
    fun literal(text: String, bold: Boolean = false): MutableComponent {
        return Component.literal(text).withStyle { s -> s.withBold(bold) }
    }

    fun literal(text: String, color: TextColor, bold: Boolean = false): MutableComponent {
        return Component.literal(text).withColor(color).withStyle { s -> s.withBold(bold) }
    }

    fun MutableComponent.withHoverText(hoverComponent: Component): MutableComponent {
        return this.withStyle { s ->
            s.withHoverEvent(
                HoverEvent.ShowText(hoverComponent)
            )
        }
    }

    fun buildUsernameComponent(player: ServerPlayer): Component {
        return player.displayName.copy().withColor(player.teamColor)
    }

    // this is not logged by anything, we can excuse using normal color codes
    val PRIVATE_ICON_COMPONENT: Component = literal("§8[§7!§8]§r").withHoverText(literal("§7Only you can see this message.§r"))
}