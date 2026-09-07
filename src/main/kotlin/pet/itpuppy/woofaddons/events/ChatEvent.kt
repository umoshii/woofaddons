package pet.itpuppy.woofaddons.events

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.PlayerChatMessage
import net.minecraft.network.chat.TextColor
import net.minecraft.server.level.ServerPlayer
import net.minecraft.stats.Stats
import pet.itpuppy.woofaddons.utils.Comp

object ChatEvent {
    fun register() {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(::customChatEvent)
    }

    fun customChatEvent(message: PlayerChatMessage, player: ServerPlayer, bound: ChatType.Bound): Boolean {
        val newMessage  = messageBuilder(player, message)
        val broadcaster = player.level().server.playerList

        broadcaster.broadcastSystemMessage(newMessage, false)
        return false
    }

    fun messageBuilder(player: ServerPlayer, message: PlayerChatMessage): Component {
        val playerDeaths = player.stats.getValue(Stats.CUSTOM.get(Stats.DEATHS)).toString()

        val messageComponent  = message.decoratedContent().copy().withColor(TextColor.WHITE)
        val deathsComponent   = Component.translatable("[%s]", Comp.literal(playerDeaths, TextColor.YELLOW)).withColor(TextColor.DARK_GRAY)

        val message = Component.translatable(
            "%s %s » %s",

            deathsComponent,
            Comp.buildUsernameComponent(player),
            messageComponent
        ).withColor(TextColor.GRAY)

        return message
    }
}