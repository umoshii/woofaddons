package pet.itpuppy.woofaddons.utils

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextColor
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import kotlin.math.floor
import kotlin.math.pow

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

    // TODO: figure out why saving the displayName component as a val errors out
    fun buildUsernameComponent(player: ServerPlayer): Component {
        // ticks -> seconds -> hours
        val playtime = player.stats.getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) / 20.0 / 3600.0
        val deathcount = player.stats.getValue(Stats.CUSTOM.get(Stats.DEATHS))
        val expLvl = player.experienceLevel

        val hoverComponent = Component.translatable(
            "\uD83D\uDC64 %s\n%s\n\n⌚ Playtime: %s\n☠ Deaths: %s\n⭐ EXP Level: %s",

            player.displayName.copy().withColor(player.teamColor),
            literal(player.stringUUID),
            literal("${playtime.floorTo(1)}h", TextColor.GRAY),
            literal("$deathcount", TextColor.GRAY),
            literal("$expLvl", TextColor.GRAY)
        ).withColor(TextColor.DARK_GRAY)

        return player.displayName.copy().withColor(player.teamColor).withHoverText(hoverComponent)
    }

    // this is not logged by anything, we can excuse using normal color codes
    val PRIVATE_ICON_COMPONENT: Component = literal("§8[§7!§8]§r").withHoverText(literal("§7Only you can see this message.§r"))

    fun Double.floorTo(decimals: Int): Double {
        val multiplier = 10.0.pow(decimals)
        return floor(this * multiplier) / multiplier
    }
}