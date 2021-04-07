package com.bins.succession.event

import com.bins.succession.Succession
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerGameModeChangeEvent

class GamemodeChange : Listener {
    @EventHandler
    fun onChange(e: PlayerGameModeChangeEvent) {
        if (!Succession.b) {
            e.isCancelled = true
        }
    }
}