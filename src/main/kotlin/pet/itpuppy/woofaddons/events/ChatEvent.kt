package pet.itpuppy.woofaddons.events

import pet.itpuppy.woofaddons.utils.Comp
import pet.itpuppy.woofaddons.utils.Comp.withHoverText
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.network.chat.*
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import kotlin.math.floor
import kotlin.math.pow

object ChatEvent {
    fun register() {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(::customChatEvent)
    }

    fun customChatEvent(message: PlayerChatMessage, player: ServerPlayer, bound: ChatType.Bound): Boolean {
        val newMessage = chatComponentBuilder(player, message)

        val broadcaster = player.level().server.playerList
        broadcaster.broadcastSystemMessage(newMessage, false)
        return false
    }

    fun chatComponentBuilder(player: ServerPlayer, message: PlayerChatMessage): Component {
        val deaths   = player.stats.getValue(Stats.CUSTOM.get(Stats.DEATHS)).toString()
        val username = player.displayName.copy()
        val msg      = message.decoratedContent().copy()

        val deathCountComponent = Component.translatable("[%s]", Comp.literal(deaths, TextColor.YELLOW)).withColor(TextColor.DARK_GRAY)
        val hover = hoverComponentBuilder(player)

        val component = Component.translatable(
            "%s %s » %s",

            deathCountComponent,
            username.withColor(player.teamColor).withHoverText(hover),
            msg.withColor(TextColor.WHITE)
        ).withColor(TextColor.GRAY)

        return component
    }

    fun hoverComponentBuilder(player: ServerPlayer): Component {
        val username = player.displayName.copy()
        // ticks -> seconds -> hours
        val playtime = ((player.stats.getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) / 20.0) / 3600.0).floorTo(1)

        val component = Component.translatable(
            "%s\n%s\n\nPlaytime: %s",

            username.withColor(player.teamColor),
            Comp.literal(player.stringUUID),
            Comp.literal("${playtime}h", TextColor.GRAY)
        ).withColor(TextColor.DARK_GRAY)

        return component
    }

    fun Double.floorTo(decimals: Int): Double {
        val factor = 10.0.pow(decimals)
        return floor(this * factor) / factor
    }
}