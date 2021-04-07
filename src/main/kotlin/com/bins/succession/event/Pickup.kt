package com.bins.succession.event

import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.Succession
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent

class Pickup : Listener {
    @EventHandler
    fun onPickup(e: PlayerAttemptPickupItemEvent) {
        if (e.item.itemStack.itemMeta == null) return
        if (!e.item.itemStack.itemMeta.hasCustomModelData()) return
        if (e.item.itemStack.itemMeta.customModelData == 0) return
        val custom: Int = e.item.itemStack.itemMeta.customModelData
        val name =
            if (custom == 1) "HardWood" else if (custom == 2) "Metal" else if (custom == 3) "Fuel" else if (custom == 4) "MW" else if (custom == 5) "EW" else "NaS"
        val names = arrayOf("목재", "폐품", "연료", "금속판")
        var b = false
        for (str in names) {
            b = e.item.itemStack.itemMeta.displayName.contains(str)
            if (b) break
        }
        if (b) {
            Sabotage.Items[e.player.uniqueId]!![name] =
                (Sabotage.Items[e.player.uniqueId]!![name]!! + e.item.itemStack.amount)
            e.isCancelled = true
            e.flyAtPlayer = true
            Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable { e.item.remove() }, 1)
        }
    }
}