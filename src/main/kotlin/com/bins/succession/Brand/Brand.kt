package com.bins.succession.Brand

import com.bins.succession.Brand.Brand
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.messaging.PluginMessageListener
import java.nio.charset.StandardCharsets

class Brand : Listener, PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        val realMessage = a(message).substring(1)
        //        Bukkit.broadcastMessage(channel+" | "+player.getName()+" | "+realMessage);
    }

    companion object {
        fun a(message: ByteArray?): String {
            return String(message!!, StandardCharsets.UTF_8)
        }
    }
}